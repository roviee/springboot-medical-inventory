package com.example.medInventory.repository;


import com.example.medInventory.model.MedicalItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRepository extends JpaRepository<MedicalItem, Long> {
      List<MedicalItem> findByName(String name);
}
