package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderState LEFT JOIN FETCH o.orderProducts op LEFT JOIN FETCH op.product WHERE o.orderId = :id")
    public Optional<Order> findOrderById(@Param("id") Long orderId);

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderState")
    public List<Order> getAllOrders();

}
