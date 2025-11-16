package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.*;
import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderProduct;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.exception.NotFoundException;
import it.adesso.awesomepizza.exception.ServiceException;
import it.adesso.awesomepizza.exception.ValidationException;
import it.adesso.awesomepizza.repository.OrderRepository;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import it.adesso.awesomepizza.utility.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static it.adesso.awesomepizza.utility.Constants.UPDATE_STATE_BODY_ORDER_ID_NOT_FOUND_MSG;


/**
 * Service for order business logic management
 * @author vittorio
 * @see Order
 * @see OrderState
 * @see OrderProduct
 * @see OrderDTO
 * @see OrderStateDTO
 * @see OrderProductDTO
 * @see OrderRepository
 *
 */
@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;


    /**
     * Retrieves a list of products for specific order by its unique identifier
     *
     * @param orderId the unique identifier of the order to retrieve
     * @return a list o product for order with the specified ID
     * @throws NotFoundException if no order exists with the given ID or products
     * @throws ValidationException if the ID is null
     * @throws ServiceException for generic exception
     */
    @Transactional(readOnly = true)
    public List<OrderProductDTO> getOrderProductDetailsById(Long orderId) {
        List<OrderProductDTO> result = new ArrayList<>();

        try {
            ValidationUtils.getOrderProductDetailsByIdValidation(orderId);

            Order order = orderRepository.findOrderDetailsById(orderId);

            if (order == null || order.getOrderProducts() == null || order.getOrderProducts().isEmpty()) {
                throw new NotFoundException("Order not found: " + orderId);
            }

            for (OrderProduct op : order.getOrderProducts()) {
                OrderProductDTO dto = OrderProductDTO.fromEntity(op);
                result.add(dto);
            }

            return result;

        } catch (NotFoundException e) {
            LOGGER.warn("Order not found: {}", orderId);
            throw e;
        } catch(ValidationException e) {
            LOGGER.warn("Validation exception: {}", orderId);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error getting Order By OrderId: {}", orderId, e);
            throw new ServiceException(e.getMessage());
        }
    }


    /**
     * Retrieves a specific order by its unique identifier
     *
     * @param orderId the unique identifier of the order to retrieve
     * @return the order with the specified ID
     * @throws NotFoundException if no order exists with the given ID
     * @throws ValidationException if the ID is null
     * @throws ServiceException for generic exception
     */
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {

        try {
            ValidationUtils.getOrderByIdValidation(orderId);

            Order order = orderRepository.findOrderById(orderId);

            if (order == null) {
                throw new NotFoundException("Order not found: " + orderId);
            }

            return OrderDTO.fromEntity(order);

        } catch (NotFoundException e) {
            LOGGER.warn("Order not found: {}", orderId);
            throw e;
        } catch(ValidationException e) {
            LOGGER.warn("Validation exception: {}", orderId);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error getting Order By OrderId: {}", orderId, e);
            throw new ServiceException(e.getMessage());
        }
    }


    /**
     * Retrieves all orders from the database
     *
     * @return a list of all orders in the system
     * @throws ServiceException for generic exception
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrders() {
        List<OrderDTO> result = new ArrayList<>();

        try {
            List<Order> orderList = orderRepository.getAllOrders();

            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    OrderDTO orderDTO = OrderDTO.fromEntity(order);
                    result.add(orderDTO);
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error getting Orders", e);
            throw new ServiceException(e.getMessage());
        }

        return result;
    }


    /**
     * Retrieves all orders by state ID from the database
     * @return a list of all products with the specified state ID in the system
     * @throws ServiceException for generic exception
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByState(Long stateId) {
        List<OrderDTO> result = new ArrayList<>();

        ValidationUtils.getAllOrdersByState(stateId);

        try {
            List<Order> orderList = orderRepository.getAllOrdersByState(stateId);

            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    OrderDTO orderDTO = OrderDTO.fromEntity(order);
                    result.add(orderDTO);
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error getting Orders", e);
            throw new ServiceException(e.getMessage());
        }

        return result;
    }


    /**
     * Saves a new order in the system.
     *
     * <p>This method performs the following operations:</p>
     * <ol>
     *   <li>Validates the input order data</li>
     *   <li>Creates a new Order entity with PENDING status</li>
     *   <li>Maps customer information from DTO to entity</li>
     *   <li>Creates OrderProduct entities for each product in the order</li>
     *   <li>Persists the order and its products to the database</li>
     *   <li>Converts the saved entity back to DTO</li>
     * </ol>
     *
     * <p>The entire operation is executed within a transaction to ensure
     * data consistency. If any error occurs, the transaction will be rolled back.</p>
     *
     * @param body the order data to save, including customer information and products
     * @return the saved order as DTO with generated ID and timestamps
     * @throws ValidationException if the order data fails validation rules
     * @throws NotFoundException if any referenced product is not found
     * @throws ServiceException if an unexpected error occurs during the save operation
     */
    @Transactional
    public OrderDTO saveOrder(InsertOrderDTO body) {
        OrderDTO result = new OrderDTO();

        try {
            ValidationUtils.insertOrderValidation(body);

            Order order = new Order();
            OrderState orderState = new OrderState();

            orderState.setStateId(OrderStateEnum.IN_ATTESA.getId());
            order.setOrderState(orderState);

            order.setCustomerName(body.getCustomerName());
            order.setCustomerSurname(body.getCustomerSurname());
            order.setCustomerAddress(body.getCustomerAddress());
            order.setCustomerStreetNumber(body.getCustomerStreetNumber());
            order.setCustomerAddInfo(body.getCustomerAddInfo());
            order.setCustomerPhoneNumber(body.getCustomerPhoneNumber());


            List<OrderProduct> orderProducts = new ArrayList<>();

            for (InsertOrderProductDTO p : body.getProducts()) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);

                Product product = new Product();
                product.setProductId(p.getProductId());
                orderProduct.setProduct(product);

                orderProduct.setQuantity(p.getQuantity());
                orderProduct.setNote(p.getNote());

                orderProducts.add(orderProduct);
            }

            order.setOrderProducts(orderProducts);

            Order savedOrder = this.orderRepository.save(order);

            result = OrderDTO.fromEntity(savedOrder);
        } catch (ValidationException e) {
            LOGGER.warn("Error body saving order: {}", body, e);
            throw e;
        } catch (NotFoundException e) {
            LOGGER.warn("Error not found saving order: {}", body, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error saving Order", e);
            throw new ServiceException(e.getMessage());
        }

        return result;
    }


    /**
     * Updates the state of an existing order.
     *
     * <p>This method performs the following operations:</p>
     * <ol>
     *   <li>Validates the input data</li>
     *   <li>Retrieves the order by ID</li>
     *   <li>Validates the state transition according to business rules</li>
     *   <li>Updates the order state</li>
     *   <li>Persists the changes to the database</li>
     *   <li>Returns the updated order as DTO</li>
     * </ol>
     *
     * <p>State transitions are validated to ensure they follow the allowed workflow
     * (e.g., IN_ATTESA -> IN_PREPARAZIONE -> IN_CONSEGNA -> CONSEGNATO). Invalid transitions will be rejected.</p>
     *
     * <p>The entire operation is executed within a transaction to ensure
     * data consistency. If any error occurs, the transaction will be rolled back.</p>
     *
     * @param body the update request containing order ID and new state ID
     * @return the updated order as DTO with the new state
     * @throws ValidationException if the request data is invalid or the state transition is not allowed
     * @throws NotFoundException if the order with the specified ID does not exist
     * @throws ServiceException if an unexpected error occurs during the update operation
     */
    @Transactional
    public OrderDTO updateOrderState(UpdateOrderDTO body) {
        OrderDTO result = new OrderDTO();

        try {
            ValidationUtils.updateStateBodyValidation(body);

            Order order = orderRepository.findOrderById(body.getOrderId());

            if (order == null) {
                throw new NotFoundException(UPDATE_STATE_BODY_ORDER_ID_NOT_FOUND_MSG + ": " + body.getOrderId());
            }

            ValidationUtils.updateOrderState_TransactionValidation(order.getOrderState().getStateId(), body.getStateId());

            OrderState orderState = new OrderState();
            orderState.setStateId(body.getStateId());
            order.setOrderState(orderState);

            order = this.orderRepository.save(order);

            result = OrderDTO.fromEntity(order);

        } catch (ValidationException e) {
            LOGGER.warn("Validation KO: {}", e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            LOGGER.warn("Not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error updating Order state", e);
            throw new ServiceException(e.getMessage());
        }

        return result;
    }
}
