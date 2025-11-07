package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderStateRepositoryTest {
    @Autowired
    private OrderStateRepository orderStateRepository;


    @Test
    public void findAllTest() {
        List<OrderState> orderStateList = this.orderStateRepository.findAll();
        Assertions.assertNotNull(orderStateList);
        Assertions.assertEquals(5, orderStateList.size());
    }

    @Test
    public void checkStatesTest() {
        List<OrderState> orderStateList = this.orderStateRepository.findAll();
        Assertions.assertNotNull(orderStateList);
        Assertions.assertEquals(5, orderStateList.size());

        for(OrderState orderState : orderStateList) {
            Assertions.assertNotNull(OrderStateEnum.findById(orderState.getStateId()));
            Assertions.assertNotNull(OrderStateEnum.findByName(orderState.getStateName()));
        }
    }
}
