package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.dto.OrderStateDTO;
import it.adesso.awesomepizza.dto.ProductDTO;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.exception.NotFoundException;
import it.adesso.awesomepizza.exception.ServiceException;
import it.adesso.awesomepizza.exception.ValidationException;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import it.adesso.awesomepizza.repository.ProductRepository;
import it.adesso.awesomepizza.utility.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products from the database
     * @return a list of all products in the system
     */
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
            throw new ServiceException(e.getMessage());
        }

        return result;
    }


    /**
     * Retrieves a specific product by its unique identifier
     * @param productId the unique identifier of the product to retrieve
     * @return the product with the specified ID
     * @throws NotFoundException if no product exists with the given ID
     * @throws ValidationException if the ID is null
     * @throws ServiceException for generic exception
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductDetailsById(Long productId){

        try {
            ValidationUtils.findProductByIdValidation(productId);

            Product product = productRepository.findByProductId(productId);

            if(product == null) {
                throw new NotFoundException("Product not found: " + productId);
            }

            return ProductDTO.fromEntity(product);

        } catch(NotFoundException e) {
            LOGGER.warn("Product not found: {}", productId);
            throw e;
        } catch(ValidationException e) {
            LOGGER.warn("Validation exception: {}", productId);
            throw e;
        } catch(Exception e) {
            LOGGER.error("Error getting Product By ProductId: {}", productId, e);
            throw new ServiceException(e.getMessage());
        }
    }

}
