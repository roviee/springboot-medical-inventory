package com.example.medInventory.response;

import com.example.medInventory.dto.MedicalDto;

import java.util.List;

public record MedicalItemResponse(List<MedicalDto> medicalDtos,
                                  Integer pageNumber,
                                  Integer pageSize,
                                  long totalElements,
                                  int totalPages,
                                  boolean isLast) {
}
