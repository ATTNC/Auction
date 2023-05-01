package com.softIto.Auction.controller;

import com.softIto.Auction.model.Category;
import com.softIto.Auction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public String addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return "Category added";
    }

    @PutMapping("/update/{id}")
    public Category updateCategory(Long id, @RequestBody Category category) {
       return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "Category deleted";
    }

    @GetMapping("/getAll")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/getBy/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

}
