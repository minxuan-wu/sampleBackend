package com.example.sampleBackend.controller;

import com.example.sampleBackend.dto.OrderRequest;
import com.example.sampleBackend.model.Item;
import com.example.sampleBackend.model.ItemId;
import com.example.sampleBackend.model.Order;
import com.example.sampleBackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequest request) {
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

        orderService.saveOrderAndItems(order, items);
        return ResponseEntity.status(201).build();
    }
}