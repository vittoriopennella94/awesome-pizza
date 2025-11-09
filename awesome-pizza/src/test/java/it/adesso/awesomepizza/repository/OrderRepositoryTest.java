package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findByOrderIdTest() {
        Order order = this.orderRepository.findOrderById(1L);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertNotNull(order.getOrderState());
    }

    @Test
    public void findOrderProductsDetailsById() {
        Order order = this.orderRepository.findOrderDetailsById(1L);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertNotNull(order.getOrderProducts());
        Assertions.assertFalse(order.getOrderProducts().isEmpty());
    }

    @Test
    public void findAllTest() {
        List<Order> orders = this.orderRepository.getAllOrders();
        Assertions.assertNotNull(orders);
        Assertions.assertFalse(orders.isEmpty());
    }
}
