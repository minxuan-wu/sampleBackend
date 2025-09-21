package com.example.sampleBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "item")
@Getter
@Setter
public class Item {
    @EmbeddedId
    private ItemId id;
    private int quantity;
}