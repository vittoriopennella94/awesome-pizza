package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderDTO;
import it.adesso.awesomepizza.dto.OrderDetailsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    public void findOrderByIdTest(){
        OrderDetailsDTO result = this.orderService.getOrderDetailsById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getProducts());
    }
}
