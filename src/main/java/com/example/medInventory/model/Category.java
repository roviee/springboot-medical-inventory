package com.example.medInventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category Name is required")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<MedicalItem> medicalItem;

    public Category(String name) {
        this.name = name;
    }
}
