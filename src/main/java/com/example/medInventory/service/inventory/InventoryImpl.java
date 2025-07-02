package com.example.medInventory.service.inventory;

import com.example.medInventory.dto.InventoryDto;
import com.example.medInventory.exceptions.ItemsNotFoundException;
import com.example.medInventory.exceptions.ResourceNotFoundException;
import com.example.medInventory.model.Inventory;
import com.example.medInventory.model.MedicalItem;
import com.example.medInventory.repository.InventoryRepository;
import com.example.medInventory.repository.MedicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MedicalRepository medicalRepository;

    @Override
    public InventoryDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ItemsNotFoundException("Inventory not found with id = " + id));

        return new InventoryDto(
                inventory.getId(),
                inventory.getQuantity(),
                inventory.getLocation()
        );
    }

    @Override
    public List<InventoryDto> saveInventory(Long itemId, List<InventoryDto> inventoryDtos) {
        MedicalItem medicalItem = medicalRepository.findById(itemId)
                .orElseThrow(() -> new ItemsNotFoundException("MedicalItem not found"));
        List<Inventory> inventories = inventoryDtos.stream()
                .map(dto -> {
                    Inventory inventory = new Inventory();
                    inventory.setId(dto.getId());
                    inventory.setQuantity(dto.getQuantity());
                    inventory.setLocation(dto.getLocation());
                    inventory.setMedicalItem(medicalItem);
                    return inventory;
                }).collect(Collectors.toList());

        List<Inventory> savedInventories = inventoryRepository.saveAll(inventories);

        return savedInventories.stream()
                .map(entity -> {
                    InventoryDto dto = new InventoryDto();
                    dto.setId(entity.getId());
                    dto.setQuantity(entity.getQuantity());
                    dto.setLocation(entity.getLocation());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public Inventory updateInventoryItem(Long id, Inventory request) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ItemsNotFoundException("InventoryItem not found"));

        inventory.setQuantity(request.getQuantity());
        inventory.setLocation(request.getLocation());

        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventoryItem(Long id) {
        inventoryRepository.findById(id)
                .ifPresentOrElse(inventoryRepository::delete, () -> {
            throw new ResourceNotFoundException("Category not found");
        });
    }
}
