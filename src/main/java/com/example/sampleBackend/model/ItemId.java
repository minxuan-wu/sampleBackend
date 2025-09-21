package com.example.sampleBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ItemId implements Serializable {
    @Column(name = "order_guid")
    private String orderGuid;
    @Column(name = "product_id")
    private String productId;
}