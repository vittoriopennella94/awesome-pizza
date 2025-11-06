package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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