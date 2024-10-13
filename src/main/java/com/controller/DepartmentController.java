package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.entity.Department;
import com.repo.DepartmentRepository;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @GetMapping("/departments")
    public String listDepartments(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size,
                                  Model model) {
        // Retrieve a page of departments using pagination
        Page<Department> departmentsPage = departmentRepository.findAll(PageRequest.of(page, size));

        // Add pagination information to the model
        model.addAttribute("pagedDepartments", departmentsPage);

        return "departments/manage-departments";
    }

    @GetMapping("/adddepartment")
    public String showAddDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "departments/add-department";
    }

    @PostMapping("/adddepartment")
    public String addDepartment(@ModelAttribute("department") Department department, RedirectAttributes redirectAttributes) {
        departmentRepository.save(department);
        redirectAttributes.addFlashAttribute("message", "Department added successfully");
        return "redirect:/departments";
    }

    @GetMapping("/editdepartment/{id}")
    public String showEditDepartmentForm(@PathVariable("id") Long id, Model model) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID: " + id));
        model.addAttribute("department", department);
        return "departments/edit-department";
    }

    @PostMapping("/editdepartment/{id}")
    public String updateDepartment(@PathVariable("id") Long id, @ModelAttribute("department") Department departmentDetails, RedirectAttributes redirectAttributes) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID: " + id));
        department.setDepartmentName(departmentDetails.getDepartmentName());
        departmentRepository.save(department);
        redirectAttributes.addFlashAttribute("message", "Department updated successfully");
        return "redirect:/departments";
    }

    @GetMapping("/deletedepartment/{id}")
    public String deleteDepartment(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID: " + id));
        departmentRepository.delete(department);
        redirectAttributes.addFlashAttribute("message", "Department deleted successfully");
        return "redirect:/departments";
    }
}
