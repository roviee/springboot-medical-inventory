package com.example.medInventory.request;

import com.example.medInventory.dto.SupplierDTO;
import com.example.medInventory.model.Category;
import com.example.medInventory.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalItemUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private Category category;
    private Supplier supplier;
}
