package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findProductsByIdTest() {
        Product product = this.productRepository.findByProductId(1L);
        Assertions.assertNotNull(product);
        Assertions.assertEquals(1L, product.getProductId());
    }

    @Test
    public void findAllProductsTest() {
        List<Product> products = this.productRepository.findAll();
        Assertions.assertNotNull(products);
        Assertions.assertFalse(products.isEmpty());
    }
}
