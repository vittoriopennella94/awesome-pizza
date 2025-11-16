package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.adesso.awesomepizza.entity.Product;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Data transfer object for Product entity")
@Data
public class ProductDTO implements Serializable {
    @Schema(description = "Product ID")
    private Long productId;

    @Schema(description = "Product name")
    private String productName;

    @Schema(description = "Product description")
    private String productDescription;

    @Schema(description = "Product price")
    private BigDecimal productPrice;

    @Schema(description = "Product enable")
    private String productEnable;

    public static ProductDTO fromEntity(Product product) {
        if(product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setProductPrice(product.getProductPrice());
        productDTO.setProductEnable(product.getProductEnable());

        return productDTO;
    }
}