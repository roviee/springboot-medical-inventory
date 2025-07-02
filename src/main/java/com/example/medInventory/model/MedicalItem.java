package com.example.medInventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medical_items")
public class MedicalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "medicalItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();

    public MedicalItem(String name, String description, LocalDate expiryDate, String batchNumber, BigDecimal unitPrice, Category category, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.expiryDate = expiryDate;
        this.batchNumber = batchNumber;
        this.unitPrice = unitPrice;
        this.category = category;
        this.supplier = supplier;
    }
}
