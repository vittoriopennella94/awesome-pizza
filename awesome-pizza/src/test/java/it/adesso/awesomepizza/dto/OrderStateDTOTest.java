package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderProduct;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderStateDTOTest {

    @Test
    public void fromEntityTest(){
        Order order = new Order();
        order.setOrderId(1L);
        order.setCustomerName("Name");
        order.setCustomerSurname("Surname");
        order.setCustomerAddress("Address");
        order.setCustomerStreetNumber("12/b");
        order.setCustomerAddInfo("addInfo");

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);

        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("productName");

        orderProduct.setProduct(product);

        OrderState orderState = new OrderState();
        orderState.setStateId(OrderStateEnum.IN_ATTESA.getId());
        orderState.setStateName(OrderStateEnum.IN_ATTESA.getName());

        order.setOrderState(orderState);

        OrderStateDTO orderStateDTO = OrderStateDTO.fromEntity(orderState);

        Assertions.assertNotNull(orderStateDTO);
        Assertions.assertEquals(orderStateDTO.getStateId(), orderState.getStateId());
        Assertions.assertEquals(orderStateDTO.getStateName(), orderState.getStateName());
    }

    @Test
    public void fromEntityTest_Null(){
        OrderStateDTO orderStateDTO = OrderStateDTO.fromEntity(null);

        Assertions.assertNull(orderStateDTO);
    }
}
