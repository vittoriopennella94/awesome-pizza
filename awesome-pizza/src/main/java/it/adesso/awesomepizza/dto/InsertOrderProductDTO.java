package it.adesso.awesomepizza.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InsertOrderProductDTO implements Serializable {
    private Long productId;
    private String productName;
    private Integer quantity;
    private String note;
}
