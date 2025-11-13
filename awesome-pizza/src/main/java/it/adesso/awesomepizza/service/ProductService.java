package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.ProductDTO;
import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.exception.NotFoundException;
import it.adesso.awesomepizza.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> getProducts() {
        List<ProductDTO> result = new ArrayList<>();

        try {
            List<Product> products = productRepository.findAll();

            if(!products.isEmpty()) {
                for (Product product : products) {
                    ProductDTO productDTO = ProductDTO.fromEntity(product);
                    result.add(productDTO);
                }
            }

        }catch(Exception e){
            LOGGER.error("Error getting Products", e);
            throw  new RuntimeException("Error getting Products", e);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductDetailsById(Long productId){
        try {
            Product product = productRepository.findByProductId(productId);

            if(product == null) {
                throw new NotFoundException("Product not found: " + productId);
            }

            return ProductDTO.fromEntity(product);

        } catch(NotFoundException e) {
            LOGGER.warn("Product not found: {}", productId);
            throw e;
        } catch(Exception e) {
            LOGGER.error("Error getting Product By ProductId: {}", productId, e);
            throw new RuntimeException("Error retrieving product", e);
        }
    }

}
