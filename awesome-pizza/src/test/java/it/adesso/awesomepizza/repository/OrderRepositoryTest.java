package it.adesso.awesomepizza.repository;

import it.adesso.awesomepizza.dto.InsertOrderProductDTO;
import it.adesso.awesomepizza.entity.Order;
import it.adesso.awesomepizza.entity.OrderProduct;
import it.adesso.awesomepizza.entity.OrderState;
import it.adesso.awesomepizza.entity.Product;
import it.adesso.awesomepizza.enums.OrderStateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findByOrderIdTest() {
        Order order = this.orderRepository.findOrderById(1L);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertNotNull(order.getOrderState());
    }

    @Test
    public void findOrderProductsDetailsById() {
        Order order = this.orderRepository.findOrderDetailsById(1L);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertNotNull(order.getOrderProducts());
        Assertions.assertFalse(order.getOrderProducts().isEmpty());
    }

    @Test
    public void findAllTest() {
        List<Order> orders = this.orderRepository.getAllOrders();
        Assertions.assertNotNull(orders);
        Assertions.assertFalse(orders.isEmpty());
    }

    @Test
    public void saveOrderTest(){
        Order order = new Order();
        OrderState orderState = new OrderState();

        orderState.setStateId(OrderStateEnum.IN_ATTESA.getId());
        order.setOrderState(orderState);

        order.setCustomerName("CUSTOMER_NAME");
        order.setCustomerSurname("CUSTOMER_SURNAME");
        order.setCustomerAddress("CUSTOMER_ADDRESS");
        order.setCustomerStreetNumber("CUSTOMER_STREET");
        order.setCustomerAddInfo("CUSTOMER_ADDINFO");

        List<OrderProduct> orderProducts = new ArrayList<>();

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);

        Product product = new Product();
        product.setProductId(1L);
        orderProduct.setProduct(product);

        orderProduct.setQuantity(3);
        orderProduct.setNote("PRODUCT_NOTE");

        orderProducts.add(orderProduct);

        order.setOrderProducts(orderProducts);

        Order savedOrder = this.orderRepository.save(order);

        Assertions.assertNotNull(savedOrder);
        Assertions.assertNotNull(savedOrder.getOrderId());
        Assertions.assertNotNull(savedOrder.getOrderState());
        Assertions.assertEquals(OrderStateEnum.IN_ATTESA.getId(), savedOrder.getOrderState().getStateId());
        Assertions.assertNotNull(savedOrder.getCustomerName());
        Assertions.assertEquals(order.getCustomerSurname(), savedOrder.getCustomerSurname());
        Assertions.assertNotNull(savedOrder.getOrderProducts());
        Assertions.assertFalse(order.getOrderProducts().isEmpty());
    }

    @Test
    public void updateOrderStateTest(){
        Order order = this.orderRepository.findOrderById(1L);

        Assertions.assertNotNull(order);

        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertNotNull(order.getOrderState());

        OrderState orderState = new OrderState();
        orderState.setStateId(OrderStateEnum.ANNULLATO.getId());

        order.setOrderState(orderState);

        order = this.orderRepository.save(order);

        Assertions.assertNotNull(order);
        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertNotNull(order.getOrderState());
        Assertions.assertEquals(OrderStateEnum.ANNULLATO.getId(), order.getOrderState().getStateId());
    }
}
