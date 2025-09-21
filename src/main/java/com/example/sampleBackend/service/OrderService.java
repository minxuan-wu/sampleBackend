package com.example.sampleBackend.service;

import com.example.sampleBackend.dto.ItemResponse;
import com.example.sampleBackend.dto.OrderResponse;
import com.example.sampleBackend.model.Order;
import com.example.sampleBackend.model.Item;
import com.example.sampleBackend.repository.OrderRepository;
import com.example.sampleBackend.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void saveOrderAndItems(Order order, List<Item> items) {
        try {
            orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to insert order as guid duplicated: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Order with this guid already exists.");
        }
        itemRepository.saveAll(items);
    }

    public List<OrderResponse> getAllOrders() {
        List<Item> items = itemRepository.findAll().stream().toList();
        return orderRepository.findAll().stream().map(order -> {
            OrderResponse dto = new OrderResponse();
            dto.setOrderGuid(order.getOrderGuid());
            dto.setCustomerName(order.getCustomerName());
            dto.setCreatedAt(order.getCreatedAt());
            dto.setItems(items.stream().filter(item -> item.getId().getOrderGuid().equals(order.getOrderGuid())).map(item -> {
                ItemResponse ir = new ItemResponse();
                ir.setProductId(item.getId().getProductId());
                ir.setQuantity(item.getQuantity());
                return ir;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }
}