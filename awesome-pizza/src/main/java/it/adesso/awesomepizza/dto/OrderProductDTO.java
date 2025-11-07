package it.adesso.awesomepizza.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderProductDTO implements Serializable {
    private Long orderProductId;
    private Long order;
    private Long product;
    private Integer quantity;
    private String note;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
}