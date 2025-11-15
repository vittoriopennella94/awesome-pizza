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
public class OrderProductDTOTest {

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

        OrderProductDTO orderProductDTO = OrderProductDTO.fromEntity(orderProduct);

        Assertions.assertNotNull(orderProductDTO);
        Assertions.assertEquals(orderProductDTO.getOrderId(), orderProduct.getOrder().getOrderId());
        Assertions.assertEquals(orderProductDTO.getProductId(), orderProduct.getProduct().getProductId());
    }

    @Test
    public void fromEntityTest_Null(){
        OrderProductDTO orderProductDTO = OrderProductDTO.fromEntity(null);

        Assertions.assertNull(orderProductDTO);
    }
}
