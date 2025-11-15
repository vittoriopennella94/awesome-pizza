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
public class ProductDTOTest {

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

        ProductDTO productDTO = ProductDTO.fromEntity(product);

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(product.getProductId(), productDTO.getProductId());
        Assertions.assertEquals(product.getProductName(), productDTO.getProductName());
    }

    @Test
    public void fromEntityTest_Null(){
        ProductDTO productDTO = ProductDTO.fromEntity(null);

        Assertions.assertNull(productDTO);
    }
}
