package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.OrderProduct;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderProductDTO implements Serializable {
    private ProductDTO product;
    private Integer quantity;
    private String note;


    public static OrderProductDTO fromEntity(OrderProduct orderProduct){
        if(orderProduct == null){
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProduct(orderProduct.getProduct() != null ? ProductDTO.fromEntity(orderProduct.getProduct()) : null);
        orderProductDTO.setQuantity(orderProduct.getQuantity());
        orderProductDTO.setNote(orderProduct.getNote());

        return orderProductDTO;
    }
}