package com.example.medInventory.repository;

import com.example.medInventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByMedicalItemId(Long id);
}
