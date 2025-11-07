package it.adesso.awesomepizza.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum OrderStateEnum {
    IN_ATTESA(1L, "IN_ATTESA"),
    IN_PREPARAZIONE(2L, "IN_PREPARAZIONE"),
    IN_CONSEGNA(3L, "IN_CONSEGNA"),
    CONSEGNATO(4L, "CONSEGNATO"),
    ANNULLATO(5L, "ANNULLATO");


    private final Long id;
    private final String name;

    OrderStateEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public static  OrderStateEnum findById(Long id) {
        for (OrderStateEnum orderStateEnum : OrderStateEnum.values()) {
            if (orderStateEnum.getId().equals(id)) {
                return orderStateEnum;
            }
        }
        return null;
    }

    public static OrderStateEnum findByName(String name) {
        for (OrderStateEnum orderStateEnum : OrderStateEnum.values()) {
            if (orderStateEnum.getName().equals(name)) {
                return orderStateEnum;
            }
        }
        return null;
    }

}
