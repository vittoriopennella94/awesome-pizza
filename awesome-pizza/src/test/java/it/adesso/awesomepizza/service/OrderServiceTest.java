package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.*;
import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderProduct;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.exception.NotFoundException;
import it.adesso.awesomepizza.exception.ValidationException;
import it.adesso.awesomepizza.repository.OrderRepository;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import it.adesso.awesomepizza.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static it.adesso.awesomepizza.utility.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @MockitoSpyBean
    private OrderService orderService;

    @MockitoSpyBean
    private OrderRepository orderRepository;

    @MockitoSpyBean
    private ProductRepository productRepository;

    @MockitoSpyBean
    private OrderStateRepository orderStateRepository;


    @Test
    public void findAllOrdersTest(){
        List<OrderDTO> result = this.orderService.getOrders();

        assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findAllOrdersByStateTest(){
        List<OrderDTO> result = this.orderService.getOrdersByState(1L);

        assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        result.forEach(orderDTO -> {
            assertNotNull(orderDTO.getOrderState());
            assertEquals(orderDTO.getOrderState(), OrderStateEnum.IN_ATTESA.getName());
        });
    }

    @Test
    public void findAllOrdersByStateTestKo_StateId_Not_Found() throws ValidationException {

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            List<OrderDTO> result = this.orderService.getOrdersByState(10L);
        });

        assertEquals(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG, validationException.getMessage());

    }

    @Test
    public void getOrderProductDetailsByIdTest(){
        List<OrderProductDTO> result = this.orderService.getOrderProductDetailsById(1L);

        assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findOrderByIdTest(){
        OrderDTO result = this.orderService.getOrderById(1L);

        assertNotNull(result);
        assertNotNull(result.getOrderId());
    }

    @Test
    public void saveOrderTest(){
        Order order = getOrder();
        InsertOrderDTO insertOrderDTO = getInsertOrderDTO();

        when(this.orderRepository.save(order)).thenReturn(order);
        OrderDTO orderDTO = this.orderService.saveOrder(insertOrderDTO);

        Assertions.assertNotNull(orderDTO);
        Assertions.assertEquals("Name", orderDTO.getCustomerName());
    }

    @Test
    public void saveOrderTest_ValidationException_CustomerName_Null_Or_Empty() throws ValidationException{
        InsertOrderDTO insertOrderDTO = getInsertOrderDTO();
        insertOrderDTO.setCustomerName(null);

        ValidationException validationException =  Assertions.assertThrows(ValidationException.class, () -> {
            this.orderService.saveOrder(insertOrderDTO);
        });

        Assertions.assertEquals("CustomerName is required", validationException.getMessage());
    }

    @Test
    public void saveOrderTest_ValidationException_CustomerSurname_Null_Or_Empty() throws ValidationException{
        InsertOrderDTO insertOrderDTO = getInsertOrderDTO();
        insertOrderDTO.setCustomerSurname(null);

        ValidationException validationException =  Assertions.assertThrows(ValidationException.class, () -> {
            this.orderService.saveOrder(insertOrderDTO);
        });

        Assertions.assertEquals("CustomerSurname is required", validationException.getMessage());
    }

    @Test
    public void updateOrderStateTest(){
        Order order = getOrder();
        UpdateOrderDTO updateOrderDTO =  new UpdateOrderDTO();
        updateOrderDTO.setOrderId(10L);
        updateOrderDTO.setStateId(2L);

        when(this.orderRepository.save(order)).thenReturn(order);

        updateOrderDTO.setOrderId(order.getOrderId());

        this.orderService.updateOrderState(updateOrderDTO);

        OrderDTO orderDTO = this.orderService.getOrderById(order.getOrderId());

        assertNotNull(orderDTO);
        assertNotNull(orderDTO.getOrderId());
        assertEquals(orderDTO.getOrderState(), OrderStateEnum.IN_PREPARAZIONE.getName());
    }

    @Test
    public void updateOrderStateTestKo_StateId_Null() throws ValidationException {
        UpdateOrderDTO updateOrderDTO =  new UpdateOrderDTO();
        updateOrderDTO.setOrderId(3L);
        updateOrderDTO.setStateId(null);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            this.orderService.updateOrderState(updateOrderDTO);
        });

        assertEquals(UPDATE_STATE_BODY_STATE_ID_NULL_MSG, validationException.getMessage());
    }

    @Test
    public void updateOrderStateTestKo_OrderId_Null() throws ValidationException {
        UpdateOrderDTO updateOrderDTO =  new UpdateOrderDTO();
        updateOrderDTO.setOrderId(null);
        updateOrderDTO.setStateId(3L);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            this.orderService.updateOrderState(updateOrderDTO);
        });

        assertEquals(UPDATE_STATE_BODY_ORDER_ID_NULL_MSG, validationException.getMessage());
    }

    @Test
    public void updateOrderStateTestKo_StateId_NotFound() throws ValidationException {
        UpdateOrderDTO updateOrderDTO =  new UpdateOrderDTO();
        updateOrderDTO.setOrderId(3L);
        updateOrderDTO.setStateId(7L);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            this.orderService.updateOrderState(updateOrderDTO);
        });

        assertEquals(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG, validationException.getMessage());
    }

    @Test
    public void updateOrderStateTestKo_OrderId_NotFound() throws NotFoundException {
        UpdateOrderDTO updateOrderDTO =  new UpdateOrderDTO();
        updateOrderDTO.setOrderId(10L);
        updateOrderDTO.setStateId(3L);

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
            this.orderService.updateOrderState(updateOrderDTO);
        });

        assertEquals(UPDATE_STATE_BODY_ORDER_ID_NOT_FOUND_MSG + ": " + updateOrderDTO.getOrderId(), notFoundException.getMessage());
    }

    @Test
    public void findOrderById_Exception(){
        when(this.orderRepository.findOrderById(1L)).thenThrow(new RuntimeException());

        Assertions.assertThrows(Exception.class, () -> {
            this.orderService.getOrderById(1L);
        });
    }

    @Test
    public void findOrderDetailsById_Exception(){
        when(this.orderRepository.findOrderDetailsById(1L)).thenThrow(new RuntimeException());

        Assertions.assertThrows(Exception.class, () -> {
            this.orderService.getOrderProductDetailsById(1L);
        });
    }

    @Test
    public void getAllOrdersByState_Exception(){
        when(this.orderRepository.getAllOrdersByState(1L)).thenThrow(new RuntimeException());

        Assertions.assertThrows(Exception.class, () -> {
            this.orderService.getOrdersByState(1L);
        });
    }

    @Test
    public void getAllOrders_Exception(){
        when(this.orderRepository.getAllOrders()).thenThrow(new RuntimeException());

        Assertions.assertThrows(Exception.class, () -> {
            this.orderService.getOrders();
        });
    }

    private static InsertOrderDTO getInsertOrderDTO() {
        InsertOrderDTO insertOrderDTO = new InsertOrderDTO();
        insertOrderDTO.setCustomerName("Name");
        insertOrderDTO.setCustomerSurname("Surname");
        insertOrderDTO.setCustomerAddress("Address");
        insertOrderDTO.setCustomerStreetNumber("12/b");
        insertOrderDTO.setCustomerAddInfo("AddInfo");
        insertOrderDTO.setCustomerPhoneNumber("1234567890");

        List<InsertOrderProductDTO> insertOrderProductDTOList = new ArrayList<>();
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        insertOrderProductDTO.setQuantity(3);
        insertOrderProductDTO.setProductName("productName");

        insertOrderProductDTOList.add(insertOrderProductDTO);

        insertOrderDTO.setProducts(insertOrderProductDTOList);
        return insertOrderDTO;
    }

    public static Order getOrder(){
        Order order = new Order();
       // order.setOrderId(10L);
        order.setCustomerName("Name");
        order.setCustomerSurname("Surname");
        order.setCustomerAddress("Address");
        order.setCustomerStreetNumber("12/b");
        order.setCustomerAddInfo("addInfo");
        order.setCustomerPhoneNumber("1234567890");

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);

        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("productName");
        product.setProductEnable("Y");
        product.setProductDescription("productDescription");
        product.setProductPrice(new BigDecimal("12.00"));

        orderProduct.setProduct(product);

        OrderState orderState = new OrderState();
        orderState.setStateId(OrderStateEnum.IN_ATTESA.getId());
        orderState.setStateName(OrderStateEnum.IN_ATTESA.getName());

        order.setOrderState(orderState);
        return order;
    }

    @Test
    public void getOrderProductDetailsByIdValidationException(){
        Assertions.assertThrows(ValidationException.class, () -> {
            this.orderService.getOrderProductDetailsById(null);
        });
    }

    @Test
    public void getOrderByIdValidationException(){
        Assertions.assertThrows(ValidationException.class, () -> {
            this.orderService.getOrderById(null);
        });
    }
}
