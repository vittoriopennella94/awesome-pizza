package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.dto.InsertOrderDTO;
import it.adesso.awesomepizza.dto.UpdateOrderDTO;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.exception.ValidationException;

import static it.adesso.awesomepizza.utility.Constants.*;
import static it.adesso.awesomepizza.utility.Utils.formatMessage;


/**
 * Utility class for data validation operations.
 *
 * @author vittorio
 * @see ValidationException
 */
public class ValidationUtils {

    /**
     * Validates the request body for updating an order state.
     *
     * <p>This method checks that:</p>
     * <ul>
     *   <li>The body is not null</li>
     *   <li>The state ID is not null and exists in the system</li>
     *   <li>The order ID is not null</li>
     * </ul>
     *
     * @param body the update order DTO to validate
     * @throws ValidationException if any validation rule fails
     */
    public static void updateStateBodyValidation(UpdateOrderDTO body) throws ValidationException{
        if(body == null){
            throw new ValidationException(UPDATE_STATE_BODY_NULL_MSG);
        }

        if(body.getStateId() == null){
            throw new ValidationException(UPDATE_STATE_BODY_STATE_ID_NULL_MSG);
        }

        if(OrderStateEnum.findById(body.getStateId()) == null){
            throw new ValidationException(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG);
        }

        if(body.getOrderId() == null){
            throw new ValidationException(UPDATE_STATE_BODY_ORDER_ID_NULL_MSG);
        }
    }


    /**
     * Validates if a state transition is allowed according to business rules.
     *
     * @param stateIdFrom the current state ID of the order
     * @param stateIdTo the new state ID
     * @throws ValidationException if the state transition is not allowed
     */
    public static void updateOrderState_TransactionValidation(Long stateIdFrom, Long stateIdTo) throws ValidationException{
        if(!Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo)){
            throw new ValidationException("Transaction state from " + stateIdFrom + " - to " + stateIdTo + " not permitted");
        }
    }


    /**
     * Validates the state ID parameter for retrieving orders by state.
     *
     * @param stateId the state ID to validate
     * @throws ValidationException if the state ID does not exist in the system
     */
    public static void getAllOrdersByState(Long stateId) throws ValidationException{
        if(OrderStateEnum.findById(stateId) == null){
            throw new ValidationException(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG);
        }
    }


    /**
     * Validates the request body for creating a new order.
     *
     * <p>This method verifying:</p>
     * <ul>
     *   <li>Body is not null</li>
     *   <li>Customer information is present and within length limits</li>
     *   <li>At least one product is included in the order</li>
     *   <li>Each product has required fields and valid data</li>
     * </ul>
     *
     * <p>Field length limits:</p>
     * <ul>
     *   <li>Customer name/surname: is required and max 50 characters</li>
     *   <li>Customer address: is required and max 150 characters</li>
     *   <li>Street number: is required max 10 characters</li>
     *   <li>Phone number: is required max 10 characters</li>
     *   <li>Additional info: is not required, but if present max 255 characters</li>
     *   <li>Products: is not empty</li>
     *   <li>Product id: is not null</li>
     *   <li>Product quantity: is not null and is not equals 0</li>
     *   <li>Product notes: max 255 characters</li>
     * </ul>
     *
     * @param body the insert order DTO to validate
     * @throws ValidationException if any validation rule fails, with a detailed error message
     */
    public static void insertOrderValidation(InsertOrderDTO body) throws ValidationException {
        if(body == null){
            throw new ValidationException(INSERT_ORDER_BODY_NULL_MSG);
        }

        if(body.getCustomerName() == null || body.getCustomerName().trim().isEmpty()){
            throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "CustomerName"));
        }else {
            if(body.getCustomerName().trim().length() > 50){
                throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "CustomerName", "50"));
            }
        }

        if(body.getCustomerSurname() == null || body.getCustomerSurname().trim().isEmpty()){
            throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "CustomerSurname"));
        }else {
            if(body.getCustomerSurname().trim().length() > 50){
                throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "CustomerSurname", "50"));
            }
        }

        if(body.getCustomerAddress() == null || body.getCustomerAddress().trim().isEmpty()){
            throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "CustomerAddress"));
        }else {
            if(body.getCustomerAddress().trim().length() > 150){
                throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "CustomerAddress", "150"));
            }
        }

        if(body.getCustomerStreetNumber() == null || body.getCustomerStreetNumber().trim().isEmpty()){
            throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "CustomerStreetNumber"));
        }else {
            if(body.getCustomerAddress().trim().length() > 10){
                throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "CustomerStreetNumber", "10"));
            }
        }

        if(body.getCustomerAddInfo() != null &&  !body.getCustomerAddInfo().trim().isEmpty()){
            if(body.getCustomerAddInfo().trim().length() > 255){
                throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "CustomerAddInfo", "255"));
            }
        }

        if(body.getCustomerPhoneNumber() == null || body.getCustomerPhoneNumber().trim().isEmpty()){
            throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "CustomerPhoneNumber"));
        }else {
            if(body.getCustomerPhoneNumber().trim().length() > 10){
                throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "CustomerPhoneNumber", "10"));
            }
        }

        if(body.getProducts() == null || body.getProducts().isEmpty()){
            throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "Products"));
        }else {
            for(int i = 0; i < body.getProducts().size(); i++){
                if(body.getProducts().get(i).getProductId() == null){
                    throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "Product.ProductId"));
                }

                if(body.getProducts().get(i).getQuantity() == null || body.getProducts().get(i).getQuantity() == 0){
                    throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "Product.Quantity"));
                }

                if(body.getProducts().get(i).getNote() != null && !body.getProducts().get(i).getNote().trim().isEmpty()){
                    if(body.getProducts().get(i).getNote().length() > 255){
                        throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "Product.note", "255"));
                    }
                }
            }
        }
    }


    /**
     * Validates the product ID parameter for the service function ProductService.findProductById.
     *
     * @param productId the product ID to validate
     * @throws ValidationException if the product ID is null
     */
    public static void findProductByIdValidation(Long productId) throws ValidationException{
        if(productId == null){
            throw new ValidationException(formatMessage(FIND_OBJECT_NULL_MSG, "productId"));
        }
    }


    /**
     * Validates the order ID parameter for the function OrderService.getOrderProductDetailsById.
     *
     * @param orderId the order ID to validate
     * @throws ValidationException if the order ID is null
     */
    public static void getOrderProductDetailsByIdValidation(Long orderId) throws ValidationException{
        if(orderId == null){
            throw new ValidationException(formatMessage(FIND_OBJECT_NULL_MSG, "orderId"));
        }
    }


    /**
     * Validates the order ID parameter for the function OrderService.getOrderById.
     *
     * @param orderId the order ID to validate
     * @throws ValidationException if the order ID is null
     */
    public static void getOrderByIdValidation(Long orderId) throws ValidationException{
        if(orderId == null){
            throw new ValidationException(formatMessage(FIND_OBJECT_NULL_MSG, "orderId"));
        }
    }


}
