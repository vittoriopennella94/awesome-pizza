package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Data for creating an order")
@Data
public class InsertOrderDTO implements Serializable {
    @Schema(description = "Customer name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerName;

    @Schema(description = "Customer surname", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerSurname;

    @Schema(description = "Customer address", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerAddress;

    @Schema(description = "Customer street number", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerStreetNumber;

    @Schema(description = "Customer additional info", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String customerAddInfo;

    @Schema(description = "Customer phone number", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerPhoneNumber;

    @Schema(description = "Order products", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<InsertOrderProductDTO> products;
}
