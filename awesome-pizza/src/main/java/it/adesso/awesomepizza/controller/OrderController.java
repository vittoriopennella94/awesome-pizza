package it.adesso.awesomepizza.controller;

import it.adesso.awesomepizza.dto.ApiResponse;
import it.adesso.awesomepizza.dto.OrderDTO;
import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.service.OrderService;
import it.adesso.awesomepizza.service.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        List<OrderDTO> result = this.orderService.getOrders();
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        return null;
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) {
        return null;
    }


}
