package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.exception.ServiceException;
import it.adesso.awesomepizza.repository.OrderRepository;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderStateService {
    private static final Logger logger = LoggerFactory.getLogger(OrderStateService.class);

    @Autowired
    private OrderStateRepository orderStateRepository;

    @Transactional(readOnly = true)
    public List<OrderStateDTO> getStates(){
        List<OrderStateDTO> result = new ArrayList<>();

        try {
            List<OrderState> orderStates = orderStateRepository.findAll();

            if(orderStates != null &&  !orderStates.isEmpty()){
                for(OrderState orderState : orderStates){
                    OrderStateDTO orderStateDTO = OrderStateDTO.fromEntity(orderState);
                    result.add(orderStateDTO);
                }
            }
        }catch (Exception e){
            logger.error("Error in OrderStateService.getStates", e);
            throw new ServiceException(e.getMessage());
        }

        return result;
    }
}
