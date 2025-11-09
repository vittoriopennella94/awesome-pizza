package it.adesso.awesomepizza.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InsertOrderDTO implements Serializable {
    private String customerName;
    private String customerSurname;
    private String customerAddress;
    private String customerStreetNumber;
    private String customerAddInfo;
    private List<InsertOrderProductDTO> products;
}
