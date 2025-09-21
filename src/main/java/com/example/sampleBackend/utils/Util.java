package com.example.sampleBackend.utils;

import com.example.sampleBackend.dto.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    public static boolean isValidOrderRequest(OrderRequest request) {
        if (request == null) {
            logger.error("request is null");
            return false;
        }
        if (request.getOrderGuid() == null || request.getOrderGuid().isBlank()){
            logger.error("orderGuid is null or blank");
            return false;
        }
        if (request.getCustomerName() == null || request.getCustomerName().isBlank()) {
            logger.error("customerName is null or blank");
            return false;
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            logger.error("items is null or empty");
            return false;
        }
        for (OrderRequest.ItemRequest item : request.getItems()) {
            if (item.getProductId() == null || item.getProductId().isBlank()) {
                logger.error("productId is null or blank");
                return false;
            }
            if (item.getQuantity() < 1) {
                logger.error("quantity smaller than 1");
                return false;
            }
        }
        return true;
    }
}
