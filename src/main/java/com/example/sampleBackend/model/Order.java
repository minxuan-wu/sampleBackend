package com.example.sampleBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
public class Order {
    @Id
    private String orderGuid;
    private String customerName;
    private Instant createdAt;
}