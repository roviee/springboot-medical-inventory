package com.example.medInventory.request;

import com.example.medInventory.model.Category;
import com.example.medInventory.model.Supplier;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddMedicalItemRequest {
    private Long id;
    private String name;
    private String description;
    private LocalDate expiryDate;
    private String batchNumber;
    private BigDecimal unitPrice;
    private Category category;
    private Supplier supplier;
}
