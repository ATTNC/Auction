package com.softIto.Auction.service;

import com.softIto.Auction.model.Category;
import com.softIto.Auction.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryRepositoryService implements IRepositoryService<Category> {
    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryRepositoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category entity) {
        categoryRepository.save(entity);
    }

    @Override
    public void update(Category entity) {
        categoryRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}

