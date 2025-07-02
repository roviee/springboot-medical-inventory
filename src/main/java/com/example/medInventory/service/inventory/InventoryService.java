package com.example.medInventory.service.inventory;

import com.example.medInventory.dto.InventoryDto;
import com.example.medInventory.model.Inventory;

import java.util.List;

public interface InventoryService {
    InventoryDto getInventoryById(Long id);
    List<InventoryDto> saveInventory(Long itemId, List<InventoryDto> inventoryDtos);
    Inventory updateInventoryItem(Long id, Inventory request);
    void deleteInventoryItem(Long id);
}
