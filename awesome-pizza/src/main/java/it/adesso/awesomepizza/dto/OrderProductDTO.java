package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.OrderProduct;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderProductDTO implements Serializable {
    private Long orderProductId;
    private Long order;
    private Long product;
    private Integer quantity;
    private String note;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;


    public static OrderProductDTO fromEntity(OrderProduct orderProduct){
        if(orderProduct == null){
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setOrderProductId(orderProduct.getOrderProductId());
        orderProductDTO.setOrder(orderProduct.getOrder() != null ? orderProduct.getOrder().getOrderId() : null);
        orderProductDTO.setProduct(orderProduct.getProduct() != null ? orderProduct.getProduct().getProductId() : null);
        orderProductDTO.setQuantity(orderProduct.getQuantity());
        orderProductDTO.setNote(orderProduct.getNote());
        orderProductDTO.setCreateDatetime(orderProduct.getCreateDatetime());
        orderProductDTO.setUpdateDatetime(orderProduct.getUpdateDatetime());

        return orderProductDTO;
    }
}