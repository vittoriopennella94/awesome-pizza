package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void findByOrderIdTest() {
        Order order = this.orderRepository.findByOrderId(1L);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertNotNull(order.getOrderProducts());
        Assertions.assertNotEquals(0, order.getOrderProducts().size());
    }
}
