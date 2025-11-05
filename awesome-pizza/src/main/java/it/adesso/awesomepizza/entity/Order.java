package it.adesso.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_surname", nullable = false)
    private String customerSurname;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_street_number", nullable = false)
    private String customerStreetNumber;

    @Column(name = "customer_add_info")
    private String customerAddInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_state", nullable = false, foreignKey = @ForeignKey(name = "fk_order_state_id"))
    private OrderState orderState;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "create_datetime", updatable = false)
    private LocalDateTime createDatetime;

    @UpdateTimestamp
    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;
}