package it.adesso.awesomepizza.dto;

import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderProduct;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailsDTO implements Serializable {
    private List<OrderProductDTO> products;

    public static OrderDetailsDTO fromEntity(Order order) {
        if(order == null) {
            return null;
        }

        OrderDetailsDTO result = new OrderDetailsDTO();
        List<OrderProductDTO> products = new ArrayList<>();

        if(order.getOrderProducts() != null &&  !order.getOrderProducts().isEmpty()) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                products.add(OrderProductDTO.fromEntity(orderProduct));
            }
        }

        result.setProducts(products);

        return result;
    }
}