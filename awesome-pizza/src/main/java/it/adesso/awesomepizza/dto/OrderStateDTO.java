package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.adesso.awesomepizza.entity.OrderState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Schema(description = "Data transfer object for Order State entity")
@Data
public class OrderStateDTO implements Serializable {
    @Schema(description = "State ID")
    private Long stateId;

    @Schema(description = "State name")
    private String stateName;

    public static OrderStateDTO fromEntity(OrderState orderState) {
        if(orderState == null) {
            return null;
        }

        OrderStateDTO orderStateDTO = new OrderStateDTO();
        orderStateDTO.setStateId(orderState.getStateId());
        orderStateDTO.setStateName(orderState.getStateName());

        return orderStateDTO;
    }
}