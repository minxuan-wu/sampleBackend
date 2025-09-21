package com.example.sampleBackend.repository;

import com.example.sampleBackend.model.Item;
import com.example.sampleBackend.model.ItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, ItemId> {}