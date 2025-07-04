package com.example.medInventory.service.medical;

import com.example.medInventory.dto.MedicalDto;
import com.example.medInventory.model.MedicalItem;
import com.example.medInventory.request.AddMedicalItemRequest;
import com.example.medInventory.request.MedicalItemUpdateRequest;
import com.example.medInventory.response.MedicalItemResponse;

import java.util.List;
import java.util.Optional;

public interface MedicalService {
    List<MedicalItem> getAllMedicalItems();
    MedicalItem getMedicalItemById(Long id);
    MedicalItem createMedicalItem(AddMedicalItemRequest request);
    MedicalItem  updateMedicalItem(Long id, MedicalItemUpdateRequest request);
    List<MedicalItem> getMedicalItemByName(String name);
    void deleteMedicalItem(Long id);
    List<MedicalItem> checkExpiryStatus();

    List<MedicalDto> getConvertedMedicalItems(List<MedicalItem> medicalItems);
    MedicalDto convertToDto(MedicalItem medicalItem);

    MedicalItemResponse getAllMedicalItemPagination(Integer pageNumber, Integer pageSize);
    MedicalItemResponse getAllMedicalItemWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                                  String sortBy, String dir);
}
