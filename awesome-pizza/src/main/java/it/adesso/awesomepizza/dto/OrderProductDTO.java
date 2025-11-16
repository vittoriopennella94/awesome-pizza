package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.adesso.awesomepizza.entity.OrderProduct;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "Data transfer object for Order Product entity")
@Data
public class OrderProductDTO implements Serializable {
    @Schema(description = "Order ID")
    private Long orderId;

    @Schema(description = "Product ID")
    private Long productId;

    @Schema(description = "Product name")
    private String productName;

    @Schema(description = "Quantity")
    private Integer quantity;

    @Schema(description = "Note")
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