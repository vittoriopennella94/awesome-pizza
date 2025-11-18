package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.ProductDTO;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.exception.NotFoundException;
import it.adesso.awesomepizza.exception.ValidationException;
import it.adesso.awesomepizza.repository.ProductRepository;
import it.adesso.awesomepizza.utility.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for retrieve products
 * @author vittorio
 * @see Product
 * @see ProductDTO
 * @see ProductRepository
 *
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products from the database
     *
     * @return a list of all products in the system
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProducts() {
        List<ProductDTO> result = new ArrayList<>();

        List<Product> products = productRepository.findAll();

        if(!products.isEmpty()) {
            for (Product product : products) {
                ProductDTO productDTO = ProductDTO.fromEntity(product);
                result.add(productDTO);
            }
        }

        return result;
    }


    /**
     * Retrieves a specific product by its unique identifier
     * @param productId the unique identifier of the product to retrieve
     * @return the product with the specified ID
     * @throws NotFoundException if no product exists with the given ID
     * @throws ValidationException if the ID is null
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductDetailsById(Long productId){

        ValidationUtils.findProductByIdValidation(productId);

        Product product = productRepository.findByProductId(productId);

        if(product == null) {
            throw new NotFoundException("Product not found: " + productId);
        }

        return ProductDTO.fromEntity(product);
    }

}
