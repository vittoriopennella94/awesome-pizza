package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "Data products for creating an order")
@Data
public class InsertOrderProductDTO implements Serializable {
    @Schema(description = "Product ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

    @Schema(description = "Product name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String productName;

    @Schema(description = "Quantity", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @Schema(description = "Note", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String note;
}