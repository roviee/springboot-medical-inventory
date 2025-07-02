package com.example.medInventory.repository;

import com.example.medInventory.dto.SupplierDTO;
import com.example.medInventory.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);
}