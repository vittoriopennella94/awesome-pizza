package it.adesso.awesomepizza.enums;

import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderStateEnumTest {

    @Autowired
    private OrderStateRepository orderStateRepository;

    @Test
    public void orderStateEnumTest() {
        OrderStateEnum[] orderStateEnums = OrderStateEnum.values();

        Assertions.assertNotNull(orderStateEnums);

        for (OrderStateEnum orderStateEnum : orderStateEnums) {
            OrderState orderState = this.orderStateRepository.findByStateId(orderStateEnum.getId());

            Assertions.assertNotNull(orderState);
            Assertions.assertNotNull(orderState.getStateId());
            Assertions.assertNotNull(orderState.getStateName());

            Assertions.assertEquals(orderStateEnum.getId(), orderState.getStateId());
            Assertions.assertEquals(orderStateEnum.getName(), orderState.getStateName());
        }
    }
}
