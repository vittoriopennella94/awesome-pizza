package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.*;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static it.adesso.awesomepizza.utility.Constants.UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void findAllOrdersTest(){
        List<OrderDTO> result = this.orderService.getOrders();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findAllOrdersByStateTest(){
        List<OrderDTO> result = this.orderService.getOrdersByState(1L);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        result.forEach(orderDTO -> {
            Assertions.assertNotNull(orderDTO.getOrderState());
            Assertions.assertEquals(orderDTO.getOrderState(), OrderStateEnum.IN_ATTESA.getName());
        });
    }

    @Test
    public void findAllOrdersByStateTestKo_StateId_Not_Found() throws ValidationException {

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            List<OrderDTO> result = this.orderService.getOrdersByState(10L);
        });

        Assertions.assertEquals(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG, validationException.getMessage());

    }

    @Test
    public void getOrderProductDetailsByIdTest(){
        List<OrderProductDTO> result = this.orderService.getOrderProductDetailsById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findOrderByIdTest(){
        OrderDTO result = this.orderService.getOrderById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getOrderId());
    }

    @Test
    public void saveOrderTest(){
        InsertOrderDTO insertOrderDTO = getInsertOrderDTO();

        OrderDTO orderDTO = this.orderService.saveOrder(insertOrderDTO);

        Assertions.assertNotNull(orderDTO);
    }

    @Test
    public void updateOrderStateTest(){
        OrderDTO result = this.orderService.getOrderById(3L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getOrderId());
        Assertions.assertEquals(result.getOrderState(), OrderStateEnum.IN_ATTESA.getName());

        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setOrderId(result.getOrderId());
        updateOrderDTO.setStateId(OrderStateEnum.IN_PREPARAZIONE.getId());

        this.orderService.updateOrderState(updateOrderDTO);

        result = this.orderService.getOrderById(3L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getOrderId());
        Assertions.assertEquals(result.getOrderState(), OrderStateEnum.IN_PREPARAZIONE.getName());
    }

    private static InsertOrderDTO getInsertOrderDTO() {
        InsertOrderDTO  insertOrderDTO = new InsertOrderDTO();
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
