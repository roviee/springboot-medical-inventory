package com.example.medInventory.controller;

import com.example.medInventory.dto.MedicalDto;
import com.example.medInventory.exceptions.ItemsNotFoundException;
import com.example.medInventory.exceptions.ResourceNotFoundException;
import com.example.medInventory.model.MedicalItem;
import com.example.medInventory.request.AddMedicalItemRequest;
import com.example.medInventory.request.MedicalItemUpdateRequest;
import com.example.medInventory.response.ApiResponse;
import com.example.medInventory.response.MedicalItemResponse;
import com.example.medInventory.service.medical.MedicalService;
import com.example.medInventory.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/items")
public class MedicalController {
    private final MedicalService medicalService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllMedicalItems() {
        List<MedicalItem> medicalItems = medicalService.getAllMedicalItems();
        List<MedicalDto> medicalDto = medicalService.getConvertedMedicalItems(medicalItems);
        return ResponseEntity.ok(new ApiResponse("success", medicalDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMedicalItemById(@PathVariable Long id) {
        try {
            MedicalItem medicalItem = medicalService.getMedicalItemById(id);
            return ResponseEntity.ok(new ApiResponse("success", medicalItem));
        } catch (ItemsNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/medical/expired")
    public List<MedicalItem> expired() {
        return medicalService.checkExpiryStatus();
    }

    @GetMapping("/medical/{name}")
    public ResponseEntity<ApiResponse> getMedicalItemByName(@PathVariable String name) {
        try {
           List<MedicalItem> medicalName = medicalService.getMedicalItemByName(name);
           if (medicalName.isEmpty()) {
               return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Item found", null));
           }
           return ResponseEntity.ok(new ApiResponse("success", medicalName));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getMedicalItemWithPagination(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
            MedicalItemResponse medicalItemResponse = medicalService.getAllMedicalItemPagination(pageNumber, pageSize);
            return ResponseEntity.ok(new ApiResponse("success", medicalItemResponse));
    }
    @GetMapping("/allPageSort")
    public ResponseEntity<ApiResponse> getMedicalItemWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir

    ) {
        MedicalItemResponse medicalItemResponse = medicalService.getAllMedicalItemWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir);
        return ResponseEntity.ok(new ApiResponse("success", medicalItemResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createMedicalItem(@Valid @RequestBody AddMedicalItemRequest request) {
        try {
            MedicalItem saveMedicalItem = medicalService.createMedicalItem(request);
            MedicalDto medicalDto = medicalService.convertToDto(saveMedicalItem);
            return ResponseEntity.ok(new ApiResponse("Item added successfully", medicalDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/updated")
    public ResponseEntity<ApiResponse> updateMedicalItem(@Valid @PathVariable Long id, @RequestBody MedicalItemUpdateRequest request) {
        try {
            MedicalItem updatedItem = medicalService.updateMedicalItem(id, request);
            MedicalDto medicalDto = medicalService.convertToDto(updatedItem);
            return ResponseEntity.ok(new ApiResponse("Update Item success!", medicalDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMedicalItem(@PathVariable Long id) {
        try {
             medicalService.deleteMedicalItem(id);
             return ResponseEntity.ok(new ApiResponse("Delete Item!", id));
        } catch (ItemsNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
