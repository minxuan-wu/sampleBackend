package com.example.sampleBackend.dto;

import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponse {
    private String orderGuid;
    private String customerName;
    private Instant createdAt;
    private List<ItemResponse> items;
}