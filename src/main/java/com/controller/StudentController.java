package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.entity.Department;
import com.entity.Student;
import com.repo.DepartmentRepository;
import com.repo.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/students")
    public String listStudents(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "5") int size,
                               Model model) {
        // Retrieve a page of students using pagination
        Page<Student> studentsPage = studentRepository.findAll(PageRequest.of(page, size));
        
        // Add pagination information to the model
        model.addAttribute("pagedStudents", studentsPage);

        return "students/manage-students";
    }            

    @GetMapping("/addstudent")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        List<Department> departments = departmentRepository.findAll(); // Get list of departments
        model.addAttribute("departments", departments); // Add departments to the model
        return "students/add-student";
    }

    @PostMapping("/addstudent")
    public String addStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        studentRepository.save(student);
        redirectAttributes.addFlashAttribute("message", "Student added successfully");
        return "redirect:/students";
    }

    @GetMapping("/editstudent/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        model.addAttribute("student", student);
        List<Department> departments = departmentRepository.findAll(); // Get list of departments
        model.addAttribute("departments", departments); // Add departments to the model
        return "students/edit-student";
    }

    @PostMapping("/editstudent/{id}")
    public String updateStudent(@PathVariable("id") Long id, @ModelAttribute("student") Student studentDetails, RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        student.setStudentName(studentDetails.getStudentName());
        student.setEmail(studentDetails.getEmail());
        student.setMobileNumber(studentDetails.getMobileNumber());
        student.setRollNo(studentDetails.getRollNo());
        student.setDepartment(studentDetails.getDepartment()); // Update department
        // Update other student details as needed
        studentRepository.save(student);
        redirectAttributes.addFlashAttribute("message", "Student updated successfully");
        return "redirect:/students";
    }

    @GetMapping("/deletestudent/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        studentRepository.delete(student);
        redirectAttributes.addFlashAttribute("message", "Student deleted successfully");
        return "redirect:/students";
    }
}
