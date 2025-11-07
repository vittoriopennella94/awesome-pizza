package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderProduct;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO implements Serializable {
    private Long orderId;
    private String customerName;
    private String customerSurname;
    private String customerAddress;
    private String customerStreetNumber;
    private String customerAddInfo;
    private Long orderState;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;

    public static OrderDTO fromEntity(Order order) {
        if(order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setCustomerName(order.getCustomerName());
        orderDTO.setCustomerSurname(order.getCustomerSurname());
        orderDTO.setCustomerAddress(order.getCustomerAddress());
        orderDTO.setCustomerStreetNumber(order.getCustomerStreetNumber());
        orderDTO.setCustomerAddInfo(order.getCustomerAddInfo());
        orderDTO.setOrderState(order.getOrderId());
        orderDTO.setCreateDatetime(order.getCreateDatetime());
        orderDTO.setUpdateDatetime(order.getUpdateDatetime());

        return orderDTO;
    }
}