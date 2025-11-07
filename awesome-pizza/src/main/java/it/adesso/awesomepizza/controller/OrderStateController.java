package it.adesso.awesomepizza.controller;

import it.adesso.awesomepizza.dto.ApiResponse;
import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.service.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class OrderStateController {

    @Autowired
    private OrderStateService orderStateService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderStateDTO>>> getStates() {
        List<OrderStateDTO> orderStates = this.orderStateService.getStates();
        return ResponseEntity.ok(ApiResponse.successNoMessage(orderStates));
    }

    @GetMapping("/api/test")
    public String test() {
        throw new RuntimeException("test");
    }

}
