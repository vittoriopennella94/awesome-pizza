package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.OrderProduct;
import it.adesso.awesomepizza.entity.OrderState;
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
    private OrderState orderState;
    private List<OrderProductDTO> orderProducts = new ArrayList<>();
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
}