package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderDTO;
import it.adesso.awesomepizza.dto.OrderDetailsDTO;
import it.adesso.awesomepizza.dto.OrderProductDTO;
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
}
