package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.OrderProduct;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderProductDTO implements Serializable {
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private String note;


    public static OrderProductDTO fromEntity(OrderProduct orderProduct){
        if(orderProduct == null){
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setOrderId(orderProduct.getOrder() != null ? orderProduct.getOrder().getOrderId() : null);
        orderProductDTO.setProductId(orderProduct.getProduct() != null ? orderProduct.getProduct().getProductId() : null);
        orderProductDTO.setProductName(orderProduct.getProduct() != null ? orderProduct.getProduct().getProductName() : null);
        orderProductDTO.setQuantity(orderProduct.getQuantity());
        orderProductDTO.setNote(orderProduct.getNote());

        return orderProductDTO;
    }
}