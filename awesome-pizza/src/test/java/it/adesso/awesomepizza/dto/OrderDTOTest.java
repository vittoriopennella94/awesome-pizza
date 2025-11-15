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
public class OrderDTOTest {

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

        OrderDTO orderDTO = OrderDTO.fromEntity(order);

        Assertions.assertNotNull(orderDTO);
        Assertions.assertEquals(order.getOrderId(), orderDTO.getOrderId());
        Assertions.assertEquals(order.getCustomerName(), orderDTO.getCustomerName());
        Assertions.assertEquals(order.getCustomerSurname(), orderDTO.getCustomerSurname());
        Assertions.assertEquals(order.getCustomerAddress(), orderDTO.getCustomerAddress());
        Assertions.assertEquals(order.getCustomerStreetNumber(), orderDTO.getCustomerStreetNumber());
        Assertions.assertEquals(order.getCustomerAddInfo(), orderDTO.getCustomerAddInfo());
        Assertions.assertEquals(order.getOrderState().getStateName(), orderDTO.getOrderState());
    }

    @Test
    public void fromEntityTest_Null(){
        OrderDTO orderDTO = OrderDTO.fromEntity(null);

        Assertions.assertNull(orderDTO);
    }
}
