package com.example.medInventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contact;
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<MedicalItem> medicalItems;

    public Supplier(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }
}
