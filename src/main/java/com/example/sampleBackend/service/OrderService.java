package com.example.sampleBackend.service;

import com.example.sampleBackend.model.Order;
import com.example.sampleBackend.model.Item;
import com.example.sampleBackend.repository.OrderRepository;
import com.example.sampleBackend.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void saveOrderAndItems(Order order, List<Item> items) {
        orderRepository.save(order);
        itemRepository.saveAll(items);
    }
}