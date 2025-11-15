package it.adesso.awesomepizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.adesso.awesomepizza.dto.ApiResponse;
import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.service.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller to retrieve order states
 *
 * @author vittorio
 */
@RestController
@RequestMapping("/api/states")
@Tag(name = "OrderStateController", description = "Retrieve order states endpoints")
public class OrderStateController {

    @Autowired
    private OrderStateService orderStateService;

    @Operation(summary = "Retrieve all order states")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderStateDTO>>> getStates() {
        List<OrderStateDTO> orderStates = this.orderStateService.getStates();
        return ResponseEntity.ok(ApiResponse.successNoMessage(orderStates));
    }
}
