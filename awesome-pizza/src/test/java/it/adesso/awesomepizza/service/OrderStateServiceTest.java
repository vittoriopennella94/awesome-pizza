package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderStateServiceTest {

    @MockitoSpyBean
    private OrderStateService orderStateService;

    @MockitoSpyBean
    private OrderStateRepository orderStateRepository;

    @Test
    public void findAllStates(){
        List<OrderStateDTO> states = this.orderStateService.getStates();

        Assertions.assertNotNull(states);
        Assertions.assertFalse(states.isEmpty());
    }

    @Test
    public void findAllStates_Exception(){
        when(this.orderStateRepository.findAll()).thenThrow(new RuntimeException());

        Assertions.assertThrows(Exception.class, () -> {
            this.orderStateService.getStates();
        });
    }


}
