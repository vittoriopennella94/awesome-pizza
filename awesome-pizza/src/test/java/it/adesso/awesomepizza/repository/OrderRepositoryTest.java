package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findByOrderIdTest() {
        Optional<Order> order = this.orderRepository.findOrderById(1L);
        Assertions.assertNotNull(order);
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(1L, order.get().getOrderId());
        Assertions.assertNotNull(order.get().getOrderProducts());
        Assertions.assertNotEquals(0, order.get().getOrderProducts().size());
    }
}
