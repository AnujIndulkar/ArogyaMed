package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
