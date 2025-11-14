package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.*;
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

import java.util.ArrayList;
import java.util.List;

import static it.adesso.awesomepizza.utility.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderServiceTest {

    @MockitoSpyBean
    private OrderService orderService;

    @MockitoSpyBean
    private OrderRepository orderRepository;

    @MockitoSpyBean
    private OrderStateRepository orderStateRepository;

    @MockitoSpyBean
    private ProductRepository productRepository;

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
    public void saveOrderTest() {
    }

    @Test
    public void updateOrderStateTest(){
        UpdateOrderDTO updateOrderDTO =  new UpdateOrderDTO();
        updateOrderDTO.setOrderId(3L);
        updateOrderDTO.setStateId(3L);

        this.orderService.updateOrderState(updateOrderDTO);

        OrderDTO orderUpd = this.orderService.getOrderById(3L);

        assertNotNull(orderUpd);
        assertNotNull(orderUpd.getOrderId());
        assertEquals(orderUpd.getOrderState(), OrderStateEnum.IN_CONSEGNA.getName());
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

    private static InsertOrderDTO getInsertOrderDTO() {
        InsertOrderDTO insertOrderDTO = new InsertOrderDTO();
        insertOrderDTO.setCustomerName("CUSTOMER_NAME");
        insertOrderDTO.setCustomerSurname("CUSTOMER_SURNAME");
        insertOrderDTO.setCustomerAddress("CUSTOMER_ADDRESS");
        insertOrderDTO.setCustomerStreetNumber("CUSTOMER_STREET_NUMBER");
        insertOrderDTO.setCustomerAddInfo("CUSTOMER_ADDINFO");

        List<InsertOrderProductDTO> insertOrderProductDTOList = new ArrayList<>();
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        insertOrderProductDTO.setQuantity(3);
        insertOrderProductDTO.setNote("PRODUCT_NOTE");

        insertOrderDTO.setProducts(insertOrderProductDTOList);
        return insertOrderDTO;
    }
}
