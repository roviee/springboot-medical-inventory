package com.example.medInventory.controller;

import com.example.medInventory.dto.InventoryDto;
import com.example.medInventory.dto.MedicalDto;
import com.example.medInventory.exceptions.ItemsNotFoundException;
import com.example.medInventory.exceptions.ResourceNotFoundException;
import com.example.medInventory.model.Inventory;
import com.example.medInventory.response.ApiResponse;
import com.example.medInventory.service.inventory.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getInventoryById(@PathVariable Long id) {
        try {
            InventoryDto inventoryDto = inventoryService.getInventoryById(id);
            return ResponseEntity.ok(new ApiResponse("success", inventoryDto));
        } catch (ItemsNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/medical-item/{itemId}")
    public ResponseEntity<ApiResponse> saveInventory(@Valid @PathVariable Long itemId,  @RequestBody List<InventoryDto> inventoryDto) {
        try {
            List<InventoryDto> savedInventories = inventoryService.saveInventory(itemId, inventoryDto);
            return ResponseEntity.ok(new ApiResponse("success", savedInventories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateInventoryItem(@Valid @PathVariable Long id, @RequestBody Inventory request) {
        try {
            Inventory inventory = inventoryService.updateInventoryItem(id, request);
            return ResponseEntity.ok(new ApiResponse("Item Updated", inventory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventoryItem(@PathVariable Long id) {
       try {
          inventoryService.deleteInventoryItem(id);
           return ResponseEntity.ok(new ApiResponse("Delete Item!", id));
       } catch (ItemsNotFoundException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
       }
    }
}
