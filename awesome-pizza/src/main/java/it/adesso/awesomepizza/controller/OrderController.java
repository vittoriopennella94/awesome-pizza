package it.adesso.awesomepizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.adesso.awesomepizza.dto.*;
import it.adesso.awesomepizza.service.OrderService;
import it.adesso.awesomepizza.service.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST Controller for order management
 * @author vittorio
 */
@CrossOrigin()
@RestController
@RequestMapping("/api/orders")
@Tag(name = "OrderController", description = "Order management endpoints")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Retrieve order by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@Parameter(description = "Order ID") @PathVariable Long id) {
        OrderDTO result = this.orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @Operation(summary = "Retrieve order details by id")
    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<List<OrderProductDTO>>> getOrderDetailsById(@Parameter(description = "Order ID") @PathVariable Long id) {
        List<OrderProductDTO> result = this.orderService.getOrderProductDetailsById(id);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @Operation(summary = "Retrieve all orders or retrieve orders by stateId")
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders(@Parameter(description = "State ID")@RequestParam(value = "stateId", required = false) Long stateId) {
        List<OrderDTO> result = new ArrayList<>();

        if(stateId != null) {
            result = this.orderService.getOrdersByState(stateId);
        }else {
            result = this.orderService.getOrders();
        }

        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }


    @Operation(summary = "Create new order")
    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody InsertOrderDTO body) {
        OrderDTO result = this.orderService.saveOrder(body);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }


    @Operation(summary = "Update order state")
    @PutMapping
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderState(@RequestBody UpdateOrderDTO body) {
        OrderDTO result = this.orderService.updateOrderState(body);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }
}
