package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.ProductDTO;
import it.adesso.awesomepizza.exception.ValidationException;
import it.adesso.awesomepizza.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @MockitoSpyBean
    private ProductService productService;

    @MockitoSpyBean
    private ProductRepository productRepository;

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

    @Test
    public void findAllProduct_Exception(){
        when(this.productRepository.findAll()).thenThrow(new RuntimeException());
        Assertions.assertThrows(Exception.class, () -> {
            this.productService.getProducts();
        });
    }

    @Test
    public void findProductById_Exception(){
        when(this.productRepository.findByProductId(1L)).thenThrow(new RuntimeException());
        Assertions.assertThrows(Exception.class, () -> {
            this.productService.getProductDetailsById(1L);
        });
    }

    @Test
    public void findProductById_ValidationException(){
        Assertions.assertThrows(ValidationException.class, () -> {
            this.productService.getProductDetailsById(null);
        });
    }
}
