package com.example.medInventory.dto;


import com.example.medInventory.model.Category;
import com.example.medInventory.model.Inventory;
import com.example.medInventory.model.Supplier;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDto {
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @Future(message = "Expiry date must be in the future")
    @NotNull(message = "Expiry date is required")
    @Column(nullable = false, updatable = false)
    private LocalDate expiryDate;

    @NotBlank(message = "Batch number is required")
    @Column(nullable = false, updatable = false)
    private String batchNumber;

    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;

    private Category category;

    private SupplierDTO supplier;

    private List<InventoryDto> inventories;

}
