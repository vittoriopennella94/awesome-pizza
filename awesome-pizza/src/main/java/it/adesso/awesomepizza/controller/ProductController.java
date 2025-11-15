package it.adesso.awesomepizza.controller;

import it.adesso.awesomepizza.dto.ApiResponse;
import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.dto.ProductDTO;
import it.adesso.awesomepizza.service.OrderStateService;
import it.adesso.awesomepizza.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller to retrieve order products
 *
 * @author vittorio
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProducts() {
        List<ProductDTO> result = this.productService.getProducts();
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO result = this.productService.getProductDetailsById(id);
        return ResponseEntity.ok(ApiResponse.successNoMessage(result));
    }
}