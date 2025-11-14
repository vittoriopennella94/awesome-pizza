package it.adesso.awesomepizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.adesso.awesomepizza.dto.*;
import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderProduct;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import it.adesso.awesomepizza.repository.OrderRepository;
import it.adesso.awesomepizza.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.adesso.awesomepizza.utility.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private OrderService orderService;

    @MockitoSpyBean
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getAllOrdersTestOk() throws Exception {
        mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].orderId").exists())
                .andExpect(jsonPath("$.data[0].customerName").exists())
                .andExpect(jsonPath("$.data[0].customerSurname").exists())
                .andExpect(jsonPath("$.data[0].customerAddress").exists())
                .andExpect(jsonPath("$.data[0].customerStreetNumber").exists())
                .andExpect(jsonPath("$.data[0].customerAddInfo").exists())
                .andExpect(jsonPath("$.data[0].orderState").exists());
    }

    @Test
    public void getAllOrdersByStateTestOk() throws Exception {
        mockMvc.perform(get("/api/orders?stateId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].orderId").exists())
                .andExpect(jsonPath("$.data[0].customerName").exists())
                .andExpect(jsonPath("$.data[0].customerSurname").exists())
                .andExpect(jsonPath("$.data[0].customerAddress").exists())
                .andExpect(jsonPath("$.data[0].customerStreetNumber").exists())
                .andExpect(jsonPath("$.data[0].customerAddInfo").exists())
                .andExpect(jsonPath("$.data[0].orderState").exists())
                .andExpect(jsonPath("$.data[0].orderState").value(OrderStateEnum.IN_ATTESA.getName()));

    }

    @Test
    public void getAllOrdersByStateTestKo_StateId_Not_Found() throws Exception {
        mockMvc.perform(get("/api/orders?stateId=12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(200))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG));
    }

    @Test
    public void getAllOrdersTest_thenThrowsException() throws Exception {
        when(this.orderService.getOrders()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(500))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(EXCEPTION_ERROR_MSG));
    }

    @Test
    public void getOrderByIdTestOk() throws Exception {
        mockMvc.perform(get("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderId").exists())
                .andExpect(jsonPath("$.data.customerName").exists())
                .andExpect(jsonPath("$.data.customerSurname").exists())
                .andExpect(jsonPath("$.data.customerAddress").exists())
                .andExpect(jsonPath("$.data.customerStreetNumber").exists())
                .andExpect(jsonPath("$.data.customerAddInfo").exists())
                .andExpect(jsonPath("$.data.orderState").exists());
    }

    @Test
    public void getOrderByIdTest_thenThrowsException() throws Exception {
        when(this.orderService.getOrderById(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(500))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(EXCEPTION_ERROR_MSG));
    }

    @Test
    public void getOrderById_notFound() throws Exception {
        mockMvc.perform(get("/api/orders/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(200))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(NOT_FOUND_EXCEPTION_ERROR_MSG));
    }

    @Test
    public void getOrderDetailsByIdTestOk() throws Exception {
        mockMvc.perform(get("/api/orders/1/details")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].orderId").exists())
                .andExpect(jsonPath("$.data[0].orderId").isNumber())
                .andExpect(jsonPath("$.data[0].productId").exists())
                .andExpect(jsonPath("$.data[0].productId").isNumber())
                .andExpect(jsonPath("$.data[0].productName").exists())
                .andExpect(jsonPath("$.data[0].quantity").exists())
                .andExpect(jsonPath("$.data[0].quantity").isNumber());
    }

    @Test
    public void getOrderDetailsByIdTest_thenThrowsException() throws Exception {
        when(this.orderService.getOrderProductDetailsById(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/orders/1/details")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(500))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(EXCEPTION_ERROR_MSG));
    }

    @Test
    public void getOrderDetailsByIdTest_notFound() throws Exception {
        mockMvc.perform(get("/api/orders/10/details")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(200))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(NOT_FOUND_EXCEPTION_ERROR_MSG));
    }


    @Test
    public void insertOrderTestOk() throws Exception {
        OrderDTO inputDTO = new OrderDTO();
        inputDTO.setCustomerName(getInsertOrderDTO().getCustomerName());
        inputDTO.setCustomerSurname(getInsertOrderDTO().getCustomerSurname());
        inputDTO.setCustomerAddress(getInsertOrderDTO().getCustomerAddress());
        inputDTO.setCustomerStreetNumber(getInsertOrderDTO().getCustomerStreetNumber());
        inputDTO.setOrderState(OrderStateEnum.IN_ATTESA.getName());

        when(this.orderService.saveOrder(getInsertOrderDTO())).thenReturn(inputDTO);


        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(getInsertOrderDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.customerName").exists())
                .andExpect(jsonPath("$.data.customerName").value(getInsertOrderDTO().getCustomerName()))
                .andExpect(jsonPath("$.data.customerSurname").exists())
                .andExpect(jsonPath("$.data.customerSurname").value(getInsertOrderDTO().getCustomerSurname()))
                .andExpect(jsonPath("$.data.customerAddress").exists())
                .andExpect(jsonPath("$.data.customerStreetNumber").exists())
                .andExpect(jsonPath("$.data.orderState").exists());
    }

    @Test
    public void updateOrderStateTest_CustomerName_Null_Or_Empty() throws Exception {
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.message").exists())
                .andExpect(jsonPath("$.data.message").value("CustomerName is required"));
    }

    @Test
    public void updateOrderStateTest_CustomerSurname_Null_Or_Empty() throws Exception {
        InsertOrderDTO body = new InsertOrderDTO();
        body.setCustomerName("pippo");
        body.setCustomerSurname(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.message").exists())
                .andExpect(jsonPath("$.data.message").value("CustomerSurname is required"));
    }


    @Test
    public void updateOrderStateTestOk() throws Exception {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setOrderId(3L);
        updateOrderDTO.setStateId(OrderStateEnum.IN_PREPARAZIONE.getId());

        mockMvc.perform(put("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(updateOrderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderId").exists())
                .andExpect(jsonPath("$.data.orderId").value(3L))
                .andExpect(jsonPath("$.data.customerName").exists())
                .andExpect(jsonPath("$.data.customerSurname").exists())
                .andExpect(jsonPath("$.data.customerAddress").exists())
                .andExpect(jsonPath("$.data.customerStreetNumber").exists());


        mockMvc.perform(get("/api/orders/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderId").exists())
                .andExpect(jsonPath("$.data.customerName").exists())
                .andExpect(jsonPath("$.data.customerSurname").exists())
                .andExpect(jsonPath("$.data.customerAddress").exists())
                .andExpect(jsonPath("$.data.customerStreetNumber").exists())
                .andExpect(jsonPath("$.data.customerAddInfo").exists())
                .andExpect(jsonPath("$.data.orderState").exists())
                .andExpect(jsonPath("$.data.orderState").value(OrderStateEnum.IN_PREPARAZIONE.getName()));
    }

    @Test
    public void updateOrderStateTest_OrderId_Null() throws Exception {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setOrderId(null);
        updateOrderDTO.setStateId(OrderStateEnum.IN_PREPARAZIONE.getId());

        mockMvc.perform(put("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(updateOrderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.message").exists())
                .andExpect(jsonPath("$.data.message").value(UPDATE_STATE_BODY_ORDER_ID_NULL_MSG));
    }

    @Test
    public void updateOrderStateTest_StateId_Null() throws Exception {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setOrderId(3L);
        updateOrderDTO.setStateId(null);

        mockMvc.perform(put("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(updateOrderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.message").exists())
                .andExpect(jsonPath("$.data.message").value(UPDATE_STATE_BODY_STATE_ID_NULL_MSG));
    }

    @Test
    public void updateOrderStateTest_StateId_NotFound() throws Exception {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setOrderId(3L);
        updateOrderDTO.setStateId(6L);

        mockMvc.perform(put("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(updateOrderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.message").exists())
                .andExpect(jsonPath("$.data.message").value(UPDATE_STATE_BODY_STATE_ID_NOT_FOUND_MSG));
    }

    private static InsertOrderDTO getInsertOrderDTO() {
        InsertOrderDTO insertOrderDTO = new InsertOrderDTO();
        insertOrderDTO.setCustomerName("Mario");
        insertOrderDTO.setCustomerSurname("Rossi");
        insertOrderDTO.setCustomerAddress("Via Roma");
        insertOrderDTO.setCustomerStreetNumber("10");
        insertOrderDTO.setCustomerPhoneNumber("1234567890");

        List<InsertOrderProductDTO> insertOrderProductDTOList = new ArrayList<>();
        InsertOrderProductDTO insertOrderProductDTO = new InsertOrderProductDTO();
        insertOrderProductDTO.setProductId(1L);
        insertOrderProductDTO.setQuantity(3);
        insertOrderProductDTO.setNote("PRODUCT_NOTE");
        insertOrderProductDTOList.add(insertOrderProductDTO);

        insertOrderDTO.setProducts(insertOrderProductDTOList);
        return insertOrderDTO;
    }
}
