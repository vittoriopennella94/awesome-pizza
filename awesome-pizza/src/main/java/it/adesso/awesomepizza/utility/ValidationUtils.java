package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.dto.InsertOrderDTO;
import it.adesso.awesomepizza.dto.UpdateOrderDTO;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

import static it.adesso.awesomepizza.utility.Constants.*;
import static it.adesso.awesomepizza.utility.Utils.formatMessage;

public class ValidationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtils.class);

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

    public static void updateOrderState_TransactionValidation(Long stateIdFrom, Long stateIdTo) throws ValidationException{
        if(!Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo)){
            throw new ValidationException("Transaction state from " + stateIdFrom + " - to " + stateIdTo + " not permitted");
        }
    }

    public static void getAllOrdersByState(Long stateId) throws ValidationException{
        if(OrderStateEnum.findById(stateId) == null){
            throw new ValidationException(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG);
        }
    }

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

        if(body.getProducts() == null || body.getProducts().isEmpty()){
            throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "Products"));
        }else {
            for(int i = 0; i < body.getProducts().size(); i++){
                if(body.getProducts().get(i).getProductId() == null){
                    throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "Product.ProductId"));
                }

                if(body.getProducts().get(i).getQuantity() == null){
                    throw new ValidationException(formatMessage(INSERT_ORDER_BODY_REQUIRED_MSG, "Product.Quantity"));
                }

                if(body.getProducts().get(i).getNote() != null && body.getProducts().get(i).getNote().trim().isEmpty()){
                    if(body.getProducts().get(i).getNote().length() > 255){
                        throw new ValidationException(formatMessage(INSERT_ORDER_BODY_MAX_LENGTH_MSG, "Product.note", "255"));
                    }
                }
            }
        }
    }
}
