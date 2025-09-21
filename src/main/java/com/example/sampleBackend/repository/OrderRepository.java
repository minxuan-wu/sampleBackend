package com.example.sampleBackend.repository;

import com.example.sampleBackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {}