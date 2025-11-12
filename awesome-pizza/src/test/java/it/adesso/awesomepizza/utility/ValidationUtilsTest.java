package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.dto.UpdateOrderDTO;
import it.adesso.awesomepizza.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static it.adesso.awesomepizza.utility.Constants.*;

@SpringBootTest
public class ValidationUtilsTest {

    @Test
    public void updateStateBodyValidation_BodyNull_Test() throws ValidationException {
        UpdateOrderDTO updateOrderDTO = null;

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateStateBodyValidation(updateOrderDTO);
        });

        Assertions.assertEquals(UPDATE_STATE_BODY_NULL_MSG, validationException.getMessage());
    }

    @Test
    public void updateStateBodyValidation_StateIdNull_Test() throws ValidationException {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setStateId(null);
        updateOrderDTO.setOrderId(1L);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateStateBodyValidation(updateOrderDTO);
        });

        Assertions.assertEquals(UPDATE_STATE_BODY_STATE_ID_NULL_MSG, validationException.getMessage());
    }

    @Test
    public void updateStateBodyValidation_StateNotFound_Test() throws ValidationException {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setStateId(10L);
        updateOrderDTO.setOrderId(1L);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateStateBodyValidation(updateOrderDTO);
        });

        Assertions.assertEquals(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG, validationException.getMessage());
    }

    @Test
    public void updateStateBodyValidation_OrderIdNull_Test() throws ValidationException {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setStateId(1L);
        updateOrderDTO.setOrderId(null);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateStateBodyValidation(updateOrderDTO);
        });

        Assertions.assertEquals(UPDATE_STATE_BODY_ORDER_ID_NULL_MSG, validationException.getMessage());
    }

    @Test
    public void updateStateBodyValidationOk() {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setStateId(1L);
        updateOrderDTO.setOrderId(1L);

        Assertions.assertDoesNotThrow(() -> {
            ValidationUtils.updateStateBodyValidation(updateOrderDTO);
        });
    }

    @Test
    public void getAllOrdersByStateValidation_StateIdNotFound_Test() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.getAllOrdersByState(10L);
        });

        Assertions.assertEquals(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG, validationException.getMessage());
    }

    @Test
    public void getAllOrdersByStateValidationOk() {
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtils.getAllOrdersByState(1L);
        });
    }
}
