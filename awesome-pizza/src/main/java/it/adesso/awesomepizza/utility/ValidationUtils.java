package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.dto.UpdateOrderDTO;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static it.adesso.awesomepizza.utility.Constants.*;

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

    public static void getAllOrdersByState(Long stateId) throws ValidationException{
        if(OrderStateEnum.findById(stateId) == null){
            throw new ValidationException(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG);
        }
    }
}
