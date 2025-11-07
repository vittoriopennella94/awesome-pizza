package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderStateDTO implements Serializable {
    private Long stateId;
    private String stateName;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
}