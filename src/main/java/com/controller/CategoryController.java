package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.entity.Category;
import com.repo.CategoryRepository;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String listCategories(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                                 Model model) {
        // Retrieve a page of categories using pagination
        Page<Category> categoriesPage = categoryRepository.findAll(PageRequest.of(page, size));

        // Add pagination information to the model
        model.addAttribute("pagedCategories", categoriesPage);

        return "categories/manage-categories";
    }

    @GetMapping("/addcategory")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add-category";
    }

    @PostMapping("/addcategory")
    public String addCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("message", "Category added successfully");
        return "redirect:/categories";
    }

    @GetMapping("/editcategory/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addAttribute("category", category);
        return "categories/edit-category";
    }

    @PostMapping("/editcategory/{id}")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute("category") Category categoryDetails, RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        category.setCategoryName(categoryDetails.getCategoryName());
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("message", "Category updated successfully");
        return "redirect:/categories";
    }

    @GetMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        categoryRepository.delete(category);
        redirectAttributes.addFlashAttribute("message", "Category deleted successfully");
        return "redirect:/categories";
    }
}
