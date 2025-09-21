package com.example.sampleBackend.controller;

import com.example.sampleBackend.dto.OrderRequest;
import com.example.sampleBackend.dto.OrderResponse;
import com.example.sampleBackend.model.Item;
import com.example.sampleBackend.model.ItemId;
import com.example.sampleBackend.model.Order;
import com.example.sampleBackend.service.OrderService;
import com.example.sampleBackend.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        if(!Util.isValidOrderRequest(request)) {
            return ResponseEntity.badRequest().body("Invalid order request");
        }
        Order order = new Order();
        order.setOrderGuid(request.getOrderGuid());
        order.setCustomerName(request.getCustomerName());
        order.setCreatedAt(request.getCreatedAt());

        var items = request.getItems().stream().map(i -> {
            Item item = new Item();
            ItemId itemId = new ItemId();
            itemId.setOrderGuid(request.getOrderGuid());
            itemId.setProductId(i.getProductId());
            item.setId(itemId);
            item.setQuantity(i.getQuantity());
            return item;
        }).collect(Collectors.toList());
        try {
            orderService.saveOrderAndItems(order, items);
        } catch (Exception e) {
            logger.error("Failed to create order: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(201).build();
    }

    // Test api to get all orders
    @GetMapping
    public List<OrderResponse> getAllOrders() {
       return orderService.getAllOrders();
    }
}