package com.example.sampleBackend.dto;

import lombok.Data;

@Data
public class ItemResponse {
    private String productId;
    private int quantity;
}