package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderDTO;
import it.adesso.awesomepizza.dto.OrderDetailsDTO;
import it.adesso.awesomepizza.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findAllProductsTest(){
        List<ProductDTO> result = this.productService.getProducts();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }


    @Test
    public void findProductByIdTest(){
        ProductDTO result = this.productService.getProductDetailsById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getProductId());
    }
}
