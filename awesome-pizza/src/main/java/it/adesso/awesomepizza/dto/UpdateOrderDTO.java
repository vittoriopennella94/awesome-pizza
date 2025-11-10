package it.adesso.awesomepizza.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateOrderDTO implements Serializable {
    private Long orderId;
    private Long stateId;
}
