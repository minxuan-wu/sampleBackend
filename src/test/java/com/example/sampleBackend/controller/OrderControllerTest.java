package com.example.sampleBackend.controller;

import com.example.sampleBackend.dto.OrderRequest;
import com.example.sampleBackend.model.Item;
import com.example.sampleBackend.model.Order;
import com.example.sampleBackend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private OrderService orderService;
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    void createOrder_success_returns201() {
        OrderRequest request = new OrderRequest();
        request.setOrderGuid("123e4567-e89b-12d3-a456-426614174001");
        request.setCustomerName("Alice");
        request.setCreatedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        request.setItems(List.of(new OrderRequest.ItemRequest("p1", 2)));

        ResponseEntity<String> response = orderController.createOrder(request);
        assertEquals(201, response.getStatusCodeValue());
        verify(orderService).saveOrderAndItems(any(Order.class), anyList());
    }

    @Test
    void createOrder_serviceThrowsException_returns400WithMessage() {
        OrderRequest request = new OrderRequest();
        request.setOrderGuid("123e4567-e89b-12d3-a456-426614174001");
        request.setCustomerName("Alice");
        request.setCreatedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        request.setItems(List.of(new OrderRequest.ItemRequest("p1", 2)));

        doThrow(new IllegalArgumentException("Order with this guid already exists."))
                .when(orderService).saveOrderAndItems(any(Order.class), anyList());

        ResponseEntity<String> response = orderController.createOrder(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Order with this guid already exists.", response.getBody());
    }
}