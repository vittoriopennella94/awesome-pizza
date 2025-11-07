package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderDTO;
import it.adesso.awesomepizza.dto.OrderStateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderStateServiceTest {

    @Autowired
    private OrderStateService orderStateService;

    @Test
    public void findAllStates(){
        List<OrderStateDTO> states = this.orderStateService.getStates();

        Assertions.assertNotNull(states);
        Assertions.assertFalse(states.isEmpty());
    }
}
