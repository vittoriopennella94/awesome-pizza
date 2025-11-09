package it.adesso.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_order_state")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "state_name", nullable = false)
    private String stateName;

    @CreationTimestamp
    @Column(name = "create_datetime", updatable = false)
    private LocalDateTime createDatetime;

    @UpdateTimestamp
    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;
}