package com.example.sampleBackend.utils;

import com.example.sampleBackend.dto.OrderRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RequestValidatorTest {

    @Test
    void testNullRequest() {
        assertFalse(RequestValidator.isValidOrderRequest(null));
    }

    @Test
    void testNullOrderGuid() {
        OrderRequest req = new OrderRequest();
        req.setOrderGuid(null);
        req.setCustomerName("Alice");
        req.setItems(List.of(new OrderRequest.ItemRequest("p1", 1)));
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testBlankOrderGuid() {
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("   ");
        req.setCustomerName("Alice");
        req.setItems(List.of(new OrderRequest.ItemRequest("p1", 1)));
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testNullCustomerName() {
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("guid");
        req.setCustomerName(null);
        req.setItems(List.of(new OrderRequest.ItemRequest("p1", 1)));
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testBlankCustomerName() {
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("guid");
        req.setCustomerName(" ");
        req.setItems(List.of(new OrderRequest.ItemRequest("p1", 1)));
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testNullItems() {
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("guid");
        req.setCustomerName("Alice");
        req.setItems(null);
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testEmptyItems() {
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("guid");
        req.setCustomerName("Alice");
        req.setItems(List.of());
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testItemWithNullProductId() {
        OrderRequest.ItemRequest item = new OrderRequest.ItemRequest(null, 1);
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("guid");
        req.setCustomerName("Alice");
        req.setItems(List.of(item));
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testItemWithBlankProductId() {
        OrderRequest.ItemRequest item = new OrderRequest.ItemRequest(" ", 1);
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("guid");
        req.setCustomerName("Alice");
        req.setItems(List.of(item));
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testItemWithQuantityLessThanOne() {
        OrderRequest.ItemRequest item = new OrderRequest.ItemRequest("p1", 0);
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("guid");
        req.setCustomerName("Alice");
        req.setItems(List.of(item));
        assertFalse(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testValidRequest() {
        OrderRequest.ItemRequest item = new OrderRequest.ItemRequest("p1", 2);
        OrderRequest req = new OrderRequest();
        req.setOrderGuid("123e4567-e89b-12d3-a456-426614174001");
        req.setCustomerName("Alice");
        req.setItems(List.of(item));
        assertTrue(RequestValidator.isValidOrderRequest(req));
    }

    @Test
    void testIsValidGuid_withValidUUID() {
        String uuid = UUID.randomUUID().toString();
        assertTrue(RequestValidator.isValidGuid(uuid));
    }

    @Test
    void testIsValidGuid_withInvalidUUID() {
        assertFalse(RequestValidator.isValidGuid("not-a-uuid"));
    }
}