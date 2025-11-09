package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderDTO;
import it.adesso.awesomepizza.dto.OrderDetailsDTO;
import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository  orderRepository;


    @Transactional(readOnly = true)
    public OrderDetailsDTO getOrderDetailsById(Long orderId){
        OrderDetailsDTO result = null;

        try {
            Optional<Order> order = orderRepository.findOrderById(orderId);

            if(order != null && order.isPresent()){
                result = OrderDetailsDTO.fromEntity(order.get());
            }
        }catch(Exception e){
            LOGGER.error("Error getting Order By OrderId", e);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrders(){
        List<OrderDTO> result = new ArrayList<>();

        try {
            List<Order> orderList = orderRepository.findAll();

            if(orderList != null && !orderList.isEmpty()){
                for(Order order : orderList){
                    OrderDTO orderDTO = OrderDTO.fromEntity(order);
                    result.add(orderDTO);
                }
            }

        }catch(Exception e){
            LOGGER.error("Error getting Orders", e);
        }

        return result;
    }
}
