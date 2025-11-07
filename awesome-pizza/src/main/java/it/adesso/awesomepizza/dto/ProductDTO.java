package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.Product;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO implements Serializable {
    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productEnable;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;

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
        productDTO.setCreateDatetime(product.getCreateDatetime());
        productDTO.setUpdateDatetime(product.getUpdateDatetime());
        
        return productDTO;
    }
}