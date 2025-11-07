package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
}
