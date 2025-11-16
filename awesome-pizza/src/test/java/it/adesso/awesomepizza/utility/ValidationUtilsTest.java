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
import java.util.Random;

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

        Assertions.assertEquals("CustomerName is required", validationException.getMessage());
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

        Assertions.assertEquals("CustomerSurname is required", validationException.getMessage());
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

        Assertions.assertEquals("CustomerAddress is required", validationException.getMessage());
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

        Assertions.assertEquals("CustomerStreetNumber is required", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerPhoneNumber_Null_Or_Empty(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("aaa");
        body.setCustomerPhoneNumber("");
        body.setProducts(new ArrayList<>());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("CustomerPhoneNumber is required", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_Products_Null_Or_Empty(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("Products is required", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_Products_ProductId_Null(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(null);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("Product.ProductId is required", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_Products_Quantity_Null(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("Product.Quantity is required", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_Products_Quantity_equals_Zero(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        insertOrderProductDTO.setQuantity(0);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("Product.Quantity is required", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_Body_Null(){
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(null);
        });

        Assertions.assertEquals(INSERT_ORDER_BODY_NULL_MSG, validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerName_MaxLength(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName(generateRandomString(51));
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("CustomerName exceeds the maximum allowed length of 50 characters", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerSurname_MaxLength(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname(generateRandomString(51));
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("CustomerSurname exceeds the maximum allowed length of 50 characters", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerAddress_MaxLength(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress(generateRandomString(151));
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("CustomerAddress exceeds the maximum allowed length of 150 characters", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerAddInfo_MaxLength(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo(generateRandomString(256));
        body.setCustomerPhoneNumber("1234567890");
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("CustomerAddInfo exceeds the maximum allowed length of 255 characters", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_CustomerPhoneNumber_MaxLength(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber(generateRandomString(11));
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("CustomerPhoneNumber exceeds the maximum allowed length of 10 characters", validationException.getMessage());
    }

    @Test
    public void insertOrderBodyValidationTest_Products_ProductNote_MaxLength(){
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("Pippo");
        body.setCustomerSurname("Pippo");
        body.setCustomerAddress("Via");
        body.setCustomerStreetNumber("12");
        body.setCustomerAddInfo("");
        body.setCustomerPhoneNumber(generateRandomString(10));
        body.setProducts(new ArrayList<>());
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        insertOrderProductDTO.setQuantity(2);
        insertOrderProductDTO.setNote(generateRandomString(256));
        body.getProducts().add(insertOrderProductDTO);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.insertOrderValidation(body);
        });

        Assertions.assertEquals("Product.note exceeds the maximum allowed length of 255 characters", validationException.getMessage());
    }


    @Test
    public void getProductDetailsByIdTest_ProductId_Null(){
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.findProductByIdValidation(null);
        });

        Assertions.assertEquals("productId is null", validationException.getMessage());
    }

    @Test
    public void getOrderProductDetailsByIdTest_OrderId_Null(){
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.getOrderProductDetailsByIdValidation(null);
        });

        Assertions.assertEquals("orderId is null", validationException.getMessage());
    }

    @Test
    public void getOrderByIdValidationTest_OrderId_Null(){
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            ValidationUtils.getOrderByIdValidation(null);
        });

        Assertions.assertEquals("orderId is null", validationException.getMessage());
    }





    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
