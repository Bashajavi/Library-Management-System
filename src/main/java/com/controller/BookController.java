package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.entity.Book;
import com.entity.Category;
import com.entity.Publisher;
import com.entity.Author;
import com.repo.BookRepository;
import com.repo.CategoryRepository;
import com.repo.PublisherRepository;
import com.repo.AuthorRepository;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/books")
    public String listBooks(@RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "5") int size,
                            Model model) {
        // Retrieve a page of books using pagination
        Page<Book> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        
        // Add pagination information to the model
        model.addAttribute("pagedBooks", booksPage);

        return "books/manage-books";
    }            

    @GetMapping("/addbook")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        List<Category> categories = categoryRepository.findAll(); // Get list of categories
        model.addAttribute("categories", categories); // Add categories to the model
        List<Publisher> publishers = publisherRepository.findAll(); // Get list of publishers
        model.addAttribute("publishers", publishers); // Add publishers to the model
        List<Author> authors = authorRepository.findAll(); // Get list of authors
        model.addAttribute("authors", authors); // Add authors to the model
        return "books/add-book";
    }

    @PostMapping("/addbook")
    public String addBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        bookRepository.save(book);
        redirectAttributes.addFlashAttribute("message", "Book added successfully");
        return "redirect:/books";
    }

    @GetMapping("/editbook/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        model.addAttribute("book", book);
        List<Category> categories = categoryRepository.findAll(); // Get list of categories
        model.addAttribute("categories", categories); // Add categories to the model
        List<Publisher> publishers = publisherRepository.findAll(); // Get list of publishers
        model.addAttribute("publishers", publishers); // Add publishers to the model
        List<Author> authors = authorRepository.findAll(); // Get list of authors
        model.addAttribute("authors", authors); // Add authors to the model
        return "books/edit-book";
    }

    @PostMapping("/editbook/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book bookDetails, RedirectAttributes redirectAttributes) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        book.setBookName(bookDetails.getBookName());
        book.setCategory(bookDetails.getCategory());
        book.setPublisher(bookDetails.getPublisher()); // Update publisher
        book.setAuthor(bookDetails.getAuthor()); // Update author
        book.setIsbn(bookDetails.getIsbn());
        book.setPrice(bookDetails.getPrice());
        // Update other book details as needed
        bookRepository.save(book);
        redirectAttributes.addFlashAttribute("message", "Book updated successfully");
        return "redirect:/books";
    }

    @GetMapping("/deletebook/{id}")
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        bookRepository.delete(book);
        redirectAttributes.addFlashAttribute("message", "Book deleted successfully");
        return "redirect:/books";
    }
}
