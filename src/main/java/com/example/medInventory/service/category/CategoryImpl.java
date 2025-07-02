package com.example.medInventory.service.category;

import com.example.medInventory.exceptions.AlreadyExistsException;
import com.example.medInventory.exceptions.ResourceNotFoundException;
import com.example.medInventory.model.Category;
import com.example.medInventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category addCategory(Category category) {

        boolean exists = categoryRepository.existsByName(category.getName());

        if (exists) {
            throw new AlreadyExistsException(category.getName() + " already exists");
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long id) {

        Category existingCategory = getCategoryById(id);

        if (existingCategory == null) {
            throw new ResourceNotFoundException("Category not found");
        }

        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
            throw new ResourceNotFoundException("Category not found");
        });
    }
}
