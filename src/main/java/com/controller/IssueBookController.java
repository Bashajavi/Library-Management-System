package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Book;
import com.entity.Student;
import com.entity.IssueBook;
import com.repo.BookRepository;
import com.repo.StudentRepository;
import com.repo.IssueBookRepository;

@Controller
public class IssueBookController {

    @Autowired
    private IssueBookRepository issueBookRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/issuebooks")
    public String listIssueBooks(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                                 Model model) {
        // Retrieve a page of issue books using pagination
        Page<IssueBook> issueBooksPage = issueBookRepository.findAll(PageRequest.of(page, size));
        
        // Add pagination information to the model
        model.addAttribute("pagedIssueBooks", issueBooksPage);

        return "issuebooks/manage-issuebooks";
    }            

    @GetMapping("/addissuebook")
    public String createIssueBookForm(Model model) {
        model.addAttribute("issueBook", new IssueBook());
        List<Book> books = bookRepository.findAll(); // Get list of books
        model.addAttribute("books", books); // Add books to the model
        List<Student> students = studentRepository.findAll(); // Get list of students
        model.addAttribute("students", students); // Add students to the model
        return "issuebooks/add-issuebook";
    }

    @PostMapping("/addissuebook")
    public String addIssueBook(@ModelAttribute("issueBook") IssueBook issueBook, RedirectAttributes redirectAttributes) {
        issueBookRepository.save(issueBook);
        redirectAttributes.addFlashAttribute("message", "Issue book added successfully");
        return "redirect:/issuebooks";
    }

    @GetMapping("/editissuebook/{id}")
    public String showEditIssueBookForm(@PathVariable("id") Long id, Model model) {
        // Check if the issue book with the given id exists
        Optional<IssueBook> optionalIssueBook = issueBookRepository.findById(id);
        if (optionalIssueBook.isPresent()) {
            // Issue book exists, retrieve it from the repository
            IssueBook issueBook = optionalIssueBook.get();
            model.addAttribute("issueBook", issueBook);
            List<Book> books = bookRepository.findAll(); // Get list of books
            model.addAttribute("books", books); // Add books to the model
            List<Student> students = studentRepository.findAll(); // Get list of students
            model.addAttribute("students", students); // Add students to the model
            return "issuebooks/edit-issuebook";
        } else {
            // Issue book with the given id does not exist, throw an exception
            throw new IllegalArgumentException("Invalid issue book ID: " + id);
        }
    }

    
    @PostMapping("/editissuebook/{id}")
    public String updateIssueBook(@PathVariable("id") Long id, @ModelAttribute("issueBook") IssueBook issueBookDetails, RedirectAttributes redirectAttributes) {
        IssueBook issueBook = issueBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue book ID: " + id));
        issueBook.setBook(issueBookDetails.getBook());
        issueBook.setStudent(issueBookDetails.getStudent());
        issueBook.setIssueDate(issueBookDetails.getIssueDate());
        issueBook.setReturnDate(issueBookDetails.getReturnDate());
        issueBook.setReturned(issueBookDetails.isReturned()); // Update status
        // Update other issue book details as needed
        issueBookRepository.save(issueBook);
        redirectAttributes.addFlashAttribute("message", "Issue book updated successfully");
        return "redirect:/issuebooks";
    }

    @GetMapping("/deleteissuebook/{id}")
    public String deleteIssueBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        IssueBook issueBook = issueBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue book ID: " + id));
        issueBookRepository.delete(issueBook);
        redirectAttributes.addFlashAttribute("message", "Issue book deleted successfully");
        return "redirect:/issuebooks";
    }
}
