package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.adesso.awesomepizza.entity.Order;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "Data transfer object for Order entity")
@Data
public class OrderDTO implements Serializable {
    @Schema(description = "Order ID")
    private Long orderId;

    @Schema(description = "Customer name")
    private String customerName;

    @Schema(description = "Customer surname")
    private String customerSurname;

    @Schema(description = "Customer address")
    private String customerAddress;

    @Schema(description = "Customer street number")
    private String customerStreetNumber;

    @Schema(description = "Customer additional info")
    private String customerAddInfo;

    @Schema(description = "Customer phone number")
    private String customerPhoneNumber;

    @Schema(description = "Order state name")
    private String orderState;

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
        orderDTO.setCustomerPhoneNumber(order.getCustomerPhoneNumber());
        orderDTO.setOrderState(order.getOrderState() != null ? order.getOrderState().getStateName() : "");

        return orderDTO;
    }
}