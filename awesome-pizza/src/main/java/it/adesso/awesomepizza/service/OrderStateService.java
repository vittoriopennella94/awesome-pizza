package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for retrieve order states
 * @author vittorio
 * @see OrderState
 * @see OrderStateDTO
 * @see OrderStateRepository
 *
 */
@Service
public class OrderStateService {

    @Autowired
    private OrderStateRepository orderStateRepository;

    /**
     * Retrieves all order stats
     *
     * @return a list of all order states in the system
     */
    @Transactional(readOnly = true)
    public List<OrderStateDTO> getStates(){
        List<OrderStateDTO> result = new ArrayList<>();

        List<OrderState> orderStates = orderStateRepository.findAll();

        if(orderStates != null &&  !orderStates.isEmpty()){
            for(OrderState orderState : orderStates){
                OrderStateDTO orderStateDTO = OrderStateDTO.fromEntity(orderState);
                result.add(orderStateDTO);
            }
        }

        return result;
    }
}
