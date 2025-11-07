package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByOrderId(Long orderId);
}
