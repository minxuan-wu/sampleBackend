package com.example.sampleBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
public class Order {
    @Id
    @PrimaryKeyJoinColumn
    @Column(name = "order_guid", unique = true)
    private String orderGuid;
    private String customerName;
    private Instant createdAt;
}