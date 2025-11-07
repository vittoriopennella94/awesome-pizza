package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void findOrderByIdTest(){
        OrderDTO orderDTO = this.orderService.getOrderById(1L);

        Assertions.assertNotNull(orderDTO);
        Assertions.assertNotNull(orderDTO.getOrderId());
        Assertions.assertNotNull(orderDTO.getOrderState());
    }
}
