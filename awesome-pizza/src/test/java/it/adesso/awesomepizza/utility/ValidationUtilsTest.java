package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.dto.InsertOrderDTO;
import it.adesso.awesomepizza.dto.InsertOrderProductDTO;
import it.adesso.awesomepizza.dto.UpdateOrderDTO;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

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

    @Test
    public void updateOrderState_TransactionValidation_From_IN_ATTESA_to_IN_PREPARAZIONE_Ok() {
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_ATTESA.getId(), OrderStateEnum.IN_PREPARAZIONE.getId());
        });
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_ATTESA_to_ANNULLATO_Ok(){
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_ATTESA.getId(), OrderStateEnum.ANNULLATO.getId());
        });
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_ATTESA_to_IN_CONSEGNA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_ATTESA.getId(), OrderStateEnum.IN_CONSEGNA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_ATTESA.getId() + " - to " + OrderStateEnum.IN_CONSEGNA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_ATTESA_to_CONSEGNATO_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_ATTESA.getId(), OrderStateEnum.CONSEGNATO.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_ATTESA.getId() + " - to " + OrderStateEnum.CONSEGNATO.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_PREPARAZIONE_to_IN_CONSEGNA_Ok() {
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_PREPARAZIONE.getId(), OrderStateEnum.IN_CONSEGNA.getId());
        });
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_PREPARAZIONE_to_ANNULLATO_Ok(){
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_PREPARAZIONE.getId(), OrderStateEnum.ANNULLATO.getId());
        });
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_PREPARAZIONE_to_IN_ATTESA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_PREPARAZIONE.getId(), OrderStateEnum.IN_ATTESA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_PREPARAZIONE.getId() + " - to " + OrderStateEnum.IN_ATTESA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_PREPARAZIONE_to_CONSEGNATO_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_PREPARAZIONE.getId(), OrderStateEnum.CONSEGNATO.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_PREPARAZIONE.getId() + " - to " + OrderStateEnum.CONSEGNATO.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_CONSEGNA_to_CONSEGNATO_Ok(){
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_CONSEGNA.getId(), OrderStateEnum.CONSEGNATO.getId());
        });
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_CONSEGNA_to_IN_PREPARAZIONE_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_CONSEGNA.getId(), OrderStateEnum.IN_PREPARAZIONE.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_CONSEGNA.getId() + " - to " + OrderStateEnum.IN_PREPARAZIONE.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_CONSEGNA_to_IN_ATTESA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_CONSEGNA.getId(), OrderStateEnum.IN_ATTESA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_CONSEGNA.getId() + " - to " + OrderStateEnum.IN_ATTESA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_CONSEGNA_to_ANNULLATO_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_CONSEGNA.getId(), OrderStateEnum.ANNULLATO.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_CONSEGNA.getId() + " - to " + OrderStateEnum.ANNULLATO.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_ANNULLATO_to_IN_ATTESA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.ANNULLATO.getId(), OrderStateEnum.IN_ATTESA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.ANNULLATO.getId() + " - to " + OrderStateEnum.IN_ATTESA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_ANNULLATO_to_IN_PREPARAZIONE_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.ANNULLATO.getId(), OrderStateEnum.IN_PREPARAZIONE.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.ANNULLATO.getId() + " - to " + OrderStateEnum.IN_PREPARAZIONE.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_ANNULLATO_to_IN_CONSEGNA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.ANNULLATO.getId(), OrderStateEnum.IN_CONSEGNA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.ANNULLATO.getId() + " - to " + OrderStateEnum.IN_CONSEGNA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_ANNULLATO_to_CONSEGNATO_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.ANNULLATO.getId(), OrderStateEnum.CONSEGNATO.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.ANNULLATO.getId() + " - to " + OrderStateEnum.CONSEGNATO.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_CONSEGNATO_to_IN_CONSEGNA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.CONSEGNATO.getId(), OrderStateEnum.IN_CONSEGNA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.CONSEGNATO.getId() + " - to " + OrderStateEnum.IN_CONSEGNA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_CONSEGNATO_to_IN_PREPARAZIONE_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.CONSEGNATO.getId(), OrderStateEnum.IN_PREPARAZIONE.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.CONSEGNATO.getId() + " - to " + OrderStateEnum.IN_PREPARAZIONE.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_CONSEGNATO_to_IN_ATTESA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.CONSEGNATO.getId(), OrderStateEnum.IN_ATTESA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.CONSEGNATO.getId() + " - to " + OrderStateEnum.IN_ATTESA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_CONSEGNATO_to_ANNULLATO_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.CONSEGNATO.getId(), OrderStateEnum.ANNULLATO.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.CONSEGNATO.getId() + " - to " + OrderStateEnum.ANNULLATO.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_ATTESA_to_IN_ATTESA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_ATTESA.getId(), OrderStateEnum.IN_ATTESA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_ATTESA.getId() + " - to " + OrderStateEnum.IN_ATTESA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_PREPARAZIONE_to_IN_PREPARAZIONE_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_PREPARAZIONE.getId(), OrderStateEnum.IN_PREPARAZIONE.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_PREPARAZIONE.getId() + " - to " + OrderStateEnum.IN_PREPARAZIONE.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_IN_CONSEGNA_to_IN_CONSEGNA_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.IN_CONSEGNA.getId(), OrderStateEnum.IN_CONSEGNA.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.IN_CONSEGNA.getId() + " - to " + OrderStateEnum.IN_CONSEGNA.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_CONSEGNATO_to_CONSEGNATO_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.CONSEGNATO.getId(), OrderStateEnum.CONSEGNATO.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.CONSEGNATO.getId() + " - to " + OrderStateEnum.CONSEGNATO.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void updateOrderState_TransactionValidation_From_ANNULLATO_to_ANNULLATO_Ko() throws ValidationException {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.updateOrderState_TransactionValidation(OrderStateEnum.ANNULLATO.getId(), OrderStateEnum.ANNULLATO.getId());
        });

        Assertions.assertEquals("Transaction state from " + OrderStateEnum.ANNULLATO.getId() + " - to " + OrderStateEnum.ANNULLATO.getId() + " not permitted", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerName_Null_Or_Empty(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("");
        body.setCustomerSurname("");
        body.setCustomerAddress("");
        body.setCustomerStreetNumber("");
        body.setCustomerAddInfo("");
        body.setProducts(new ArrayList<>());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals(validationException.getMessage(), "CustomerName is required");
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerSurname_Null_Or_Empty(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("");
        body.setCustomerAddress("");
        body.setCustomerStreetNumber("");
        body.setCustomerAddInfo("");
        body.setProducts(new ArrayList<>());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals(validationException.getMessage(), "CustomerSurname is required");
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerAddress_Null_Or_Empty(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("");
        body.setCustomerStreetNumber("");
        body.setCustomerAddInfo("");
        body.setProducts(new ArrayList<>());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals(validationException.getMessage(), "CustomerAddress is required");
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerStreetNumber_Null_Or_Empty(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("");
        body.setCustomerAddInfo("");
        body.setProducts(new ArrayList<>());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals(validationException.getMessage(), "CustomerStreetNumber is required");
    }

    @Test
    public void insertOrderBodyValidationTest_Products_Null_Or_Empty(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setProducts(new ArrayList<>());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals(validationException.getMessage(), "Products is required");
    }

    @Test
    public void insertOrderBodyValidationTest_Products_ProductId_Null(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(null);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals(validationException.getMessage(), "Product.ProductId is required");
    }

    @Test
    public void insertOrderBodyValidationTest_Products_Quantity_Null(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals(validationException.getMessage(), "Product.Quantity is required");
    }

}
