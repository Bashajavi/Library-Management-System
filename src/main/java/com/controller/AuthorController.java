package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import com.entity.Author;
import com.repo.AuthorRepository;


@Controller
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/authors")
    public String listAuthors(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              Model model) {
        // Retrieve a page of authors using pagination
        Page<Author> authorsPage = authorRepository.findAll(PageRequest.of(page, size));

        // Add pagination information to the model
        model.addAttribute("pagedAuthors", authorsPage);

        return "authors/manage-authors";
    }

    @GetMapping("/addauthor")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/add-author";
    }

    @PostMapping("/addauthor")
    public String addAuthor(@ModelAttribute("author") Author author, RedirectAttributes redirectAttributes) {
        authorRepository.save(author);
        redirectAttributes.addFlashAttribute("message", "Author added successfully");
        return "redirect:/authors";
    }

    @GetMapping("/editauthor/{id}")
    public String showEditAuthorForm(@PathVariable("id") Long id, Model model) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author ID: " + id));
        model.addAttribute("author", author);
        return "authors/edit-author";
    }

    @PostMapping("/editauthor/{id}")
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute("author") Author authorDetails, RedirectAttributes redirectAttributes) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author ID: " + id));
        author.setAuthorName(authorDetails.getAuthorName());
        authorRepository.save(author);
        redirectAttributes.addFlashAttribute("message", "Author updated successfully");
        return "redirect:/authors";
    }

    @GetMapping("/deleteauthor/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author ID: " + id));
        authorRepository.delete(author);
        redirectAttributes.addFlashAttribute("message", "Author deleted successfully");
        return "redirect:/authors";
    }
}
