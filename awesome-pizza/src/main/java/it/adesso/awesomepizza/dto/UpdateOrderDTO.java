package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "Data for updating a state of an order")
@Data
public class UpdateOrderDTO implements Serializable {
    @Schema(description = "Order ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    @Schema(description = "State ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long stateId;
}
