package com.example.sampleBackend.service;

import com.example.sampleBackend.dto.OrderResponse;
import com.example.sampleBackend.model.Item;
import com.example.sampleBackend.model.ItemId;
import com.example.sampleBackend.model.Order;
import com.example.sampleBackend.repository.ItemRepository;
import com.example.sampleBackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        itemRepository = mock(ItemRepository.class);
        orderService = new OrderService(orderRepository, itemRepository);
    }

    @Test
    void saveOrderAndItems_success() {
        Order order = new Order();
        List<Item> items = List.of(new Item());
        when(orderRepository.save(order)).thenReturn(order);

        assertDoesNotThrow(() -> orderService.saveOrderAndItems(order, items));
        verify(orderRepository).save(order);
        verify(itemRepository).saveAll(items);
    }

    @Test
    void saveOrderAndItems_duplicateOrderGuid_throwsIllegalArgumentException() {
        Order order = new Order();
        order.setOrderGuid("duplicate-guid");
        List<Item> items = List.of(new Item());
        when(orderRepository.existsById(order.getOrderGuid())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.saveOrderAndItems(order, items));
        assertEquals("Order with this GUID already exists.", ex.getMessage());
        verify(orderRepository, never()).save(any());
        verify(itemRepository, never()).saveAll(any());
    }

    @Test
    void getAllOrders_returnsOrderResponses() {
        Order order = new Order();
        order.setOrderGuid("guid1");
        order.setCustomerName("Alice");
        order.setCreatedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        ItemId itemId = new ItemId();
        itemId.setOrderGuid("guid1");
        itemId.setProductId("p1");
        Item item = new Item();
        item.setId(itemId);
        item.setQuantity(2);

        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<OrderResponse> responses = orderService.getAllOrders();
        assertEquals(1, responses.size());
        OrderResponse resp = responses.get(0);
        assertEquals("guid1", resp.getOrderGuid());
        assertEquals("Alice", resp.getCustomerName());
        assertEquals(1, resp.getItems().size());
        assertEquals("p1", resp.getItems().get(0).getProductId());
        assertEquals(2, resp.getItems().get(0).getQuantity());
    }
}