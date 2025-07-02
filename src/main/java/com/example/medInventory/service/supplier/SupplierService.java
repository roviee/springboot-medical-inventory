package com.example.medInventory.service.supplier;

import com.example.medInventory.model.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier getSupplierById(Long id);
    List<Supplier> getAllSupplier();
    Supplier createSupplier(Supplier supplier);
    Supplier updateSupplier(Long id, Supplier supplier);
    void deleteSupplier(Long id);
}
