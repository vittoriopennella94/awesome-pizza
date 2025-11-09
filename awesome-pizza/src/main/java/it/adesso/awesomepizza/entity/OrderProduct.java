package it.adesso.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_order_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_id"), nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_id"), nullable = false)
    private Product product;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "NOTE")
    private String note;

    @CreationTimestamp
    @Column(name = "create_datetime", updatable = false)
    private LocalDateTime createDatetime;

    @UpdateTimestamp
    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;
}