package com.example.medInventory.service.medical;

import com.example.medInventory.dto.InventoryDto;
import com.example.medInventory.dto.MedicalDto;
import com.example.medInventory.dto.SupplierDTO;
import com.example.medInventory.exceptions.ItemsNotFoundException;
import com.example.medInventory.exceptions.ResourceNotFoundException;
import com.example.medInventory.model.Category;
import com.example.medInventory.model.Inventory;
import com.example.medInventory.model.MedicalItem;
import com.example.medInventory.model.Supplier;
import com.example.medInventory.repository.CategoryRepository;
import com.example.medInventory.repository.InventoryRepository;
import com.example.medInventory.repository.MedicalRepository;
import com.example.medInventory.repository.SupplierRepository;
import com.example.medInventory.request.AddMedicalItemRequest;
import com.example.medInventory.request.MedicalItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalImpl implements MedicalService {

    private final MedicalRepository medicalRepository;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public List<MedicalItem> getAllMedicalItems() {
        return medicalRepository.findAll();
    }

    @Override
    public MedicalItem getMedicalItemById(Long id) {
        return medicalRepository.findById(id)
                .orElseThrow(() -> new ItemsNotFoundException("MedicalItem not found"));
    }

    @Override
    public MedicalItem createMedicalItem(AddMedicalItemRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        Supplier supplier = Optional.ofNullable(supplierRepository.findByName(request.getSupplier().getName()))
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + request.getSupplier().getName()));


        request.setCategory(category);
        request.setSupplier(supplier);
        MedicalItem newItem = addMedicalItem(request, category, supplier);
        return medicalRepository.save(newItem);
    }

    private MedicalItem addMedicalItem(AddMedicalItemRequest request, Category category, Supplier supplier) {
        return new MedicalItem(
                request.getName(),
                request.getDescription(),
                request.getExpiryDate(),
                request.getBatchNumber(),
                request.getUnitPrice(),
                category,
                supplier

        );
    }

    @Override
    public MedicalItem updateMedicalItem(Long id, MedicalItemUpdateRequest request) {
        return medicalRepository.findById(id)
                .map(existingItem -> updatedExistingItem(existingItem, request))
                .map(medicalRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found!"));
    }

    public MedicalItem updatedExistingItem(MedicalItem existingItem, MedicalItemUpdateRequest request) {
        existingItem.setName(request.getName());
        existingItem.setDescription(request.getDescription());
        existingItem.setUnitPrice(request.getUnitPrice());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingItem.setCategory(category);
        Supplier supplier = supplierRepository.findByName(request.getSupplier().getName());
        existingItem.setSupplier(supplier);
        return existingItem;
    }

    @Override
    public List<MedicalItem> getMedicalItemByName(String name) {
        return medicalRepository.findByName(name);
    }

    @Override
    public List<MedicalItem> checkExpiryStatus() {
        return medicalRepository.findAll().stream()
                .filter(medicalItem -> {
                    LocalDate today = LocalDate.now();
                    LocalDate expiry = medicalItem.getExpiryDate();
                    long daysLeft = ChronoUnit.DAYS.between(today, expiry);
                    return !expiry.isBefore(today) && daysLeft <= 7;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteMedicalItem(Long id) {
        medicalRepository.findById(id)
                .ifPresentOrElse(medicalRepository::delete, () -> {
            throw new ResourceNotFoundException("Category not found");
        });
    }

    @Override
    public List<MedicalDto> getConvertedMedicalItems(List<MedicalItem> medicalItems) {
        return medicalItems.stream().map(this::convertToDto).toList();
    }

    @Override
    public MedicalDto convertToDto(MedicalItem medicalItems) {
        MedicalDto medicalDto = new MedicalDto();
        medicalDto.setId(medicalItems.getId());
        medicalDto.setName(medicalItems.getName());
        medicalDto.setDescription(medicalItems.getDescription());
        medicalDto.setExpiryDate(medicalItems.getExpiryDate());
        medicalDto.setBatchNumber(medicalItems.getBatchNumber());
        medicalDto.setUnitPrice(medicalItems.getUnitPrice());
        medicalDto.setCategory(medicalItems.getCategory());

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setName(medicalItems.getSupplier().getName());
        medicalDto.setSupplier(supplierDTO);

        List<Inventory> inventories = inventoryRepository.findByMedicalItemId(medicalItems.getId());
        List<InventoryDto> inventoryDtos = inventories.stream()
                .map(inventory -> {
                    InventoryDto inventoryDto = new InventoryDto();
                    inventoryDto.setId(inventory.getId());
                    inventoryDto.setQuantity(inventory.getQuantity());
                    inventoryDto.setLocation(inventory.getLocation());
                    return inventoryDto;
                }).toList();
        medicalDto.setInventories(inventoryDtos);
        return medicalDto;
    }
}
