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
import it.adesso.awesomepizza.utility.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.adesso.awesomepizza.utility.Constants.UPDATE_STATE_BODY_ORDER_ID_NOT_FOUND_MSG;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;


    @Transactional(readOnly = true)
    public List<OrderProductDTO> getOrderProductDetailsById(Long orderId) {
        List<OrderProductDTO> result = new ArrayList<>();

        try {
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
        } catch (Exception e) {
            LOGGER.error("Error getting Order By OrderId: {}", orderId, e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {
        try {
            Order order = orderRepository.findOrderById(orderId);

            if (order == null) {
                throw new NotFoundException("Order not found: " + orderId);
            }

            return OrderDTO.fromEntity(order);

        } catch (NotFoundException e) {
            LOGGER.warn("Order not found: {}", orderId);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error getting Order By OrderId: {}", orderId, e);
            throw new ServiceException(e.getMessage());
        }
    }

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
