package it.adesso.awesomepizza.dto;

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
    private List<OrderProductDTO> orderProducts = new ArrayList<>();
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
}