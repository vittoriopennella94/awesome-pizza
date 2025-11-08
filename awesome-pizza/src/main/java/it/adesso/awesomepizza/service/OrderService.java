package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderDTO;
import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository  orderRepository;


    public OrderDTO getOrderById(Long orderId){
        OrderDTO result = null;

        try {
            Order order = orderRepository.findByOrderId(orderId);

            if(order != null){
                result = OrderDTO.fromEntity(order);
            }
        }catch(Exception e){
            LOGGER.error("Error getting Order By OrderId", e);
        }

        return result;
    }

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
