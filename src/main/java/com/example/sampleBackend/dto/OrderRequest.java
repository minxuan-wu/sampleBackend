package com.example.sampleBackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private String orderGuid;
    private String customerName;
    private List<ItemRequest> items;
    private Instant createdAt;

    @Getter
    @Setter
    public static class ItemRequest {
        private String productId;
        private int quantity;
    }
}