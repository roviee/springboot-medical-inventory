package com.example.medInventory.service.supplier;

import com.example.medInventory.exceptions.ItemsNotFoundException;
import com.example.medInventory.exceptions.ResourceNotFoundException;
import com.example.medInventory.model.Supplier;
import com.example.medInventory.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class SupplierImpl implements SupplierService{

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found!"));
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Long id, Supplier supplier) {
        Supplier supplier1 = getSupplierById(id);

        supplier1.setName(supplier.getName());
        supplier1.setAddress(supplier.getAddress());
        supplier1.setContact(supplier.getContact());

        return supplierRepository.save(supplier1);
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.findById(id)
            .ifPresentOrElse(supplierRepository :: delete, () -> {
                throw new ResourceNotFoundException("Supper not found");
            });
    }
}
