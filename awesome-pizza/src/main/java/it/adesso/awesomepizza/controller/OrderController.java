package it.adesso.awesomepizza.controller;

import it.adesso.awesomepizza.dto.*;
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
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable Long id) {
        OrderDTO result = this.orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<List<OrderProductDTO>>> getOrderDetailsById(@PathVariable Long id) {
        List<OrderProductDTO> result = this.orderService.getOrderProductDetailsById(id);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        List<OrderDTO> result = this.orderService.getOrders();
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody InsertOrderDTO body) {
        OrderDTO result = this.orderService.saveOrder(body);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderState(@RequestBody UpdateOrderDTO body) {
        OrderDTO result = this.orderService.updateOrderState(body);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }
}
