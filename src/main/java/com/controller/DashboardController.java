package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.repo.AuthorRepository;
import com.repo.BookRepository;
import com.repo.CategoryRepository;
import com.repo.DepartmentRepository;
import com.repo.IssueBookRepository;
import com.repo.PublisherRepository;
import com.repo.StudentRepository;

@Controller
public class DashboardController {

    // Autowire your database repositories
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private IssueBookRepository issueBookRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String dashboard(Model model) {
        // Retrieve counts from the database
        long totalAuthors = authorRepository.count();
        long totalPublishers = publisherRepository.count();
        long totalBooks = bookRepository.count();
        long totalStudents = studentRepository.count();
        long totalIssueBooks = issueBookRepository.count();
        long totalDepartments = departmentRepository.count();
        long totalCategories = categoryRepository.count();

        // Pass counts to the Thymeleaf template as model attributes
        model.addAttribute("totalAuthors", totalAuthors);
        model.addAttribute("totalPublishers", totalPublishers);
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalIssueBooks", totalIssueBooks);
        model.addAttribute("totalDepartments", totalDepartments);
        model.addAttribute("totalCategories", totalCategories);

        // Return the name of the Thymeleaf template to render
        return "dashboard";
    }
}