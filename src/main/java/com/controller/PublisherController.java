package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.entity.Publisher;
import com.repo.PublisherRepository;

@Controller
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;
    
    @GetMapping("/publishers")
    public String listPublishers(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                                 Model model) {
        // Retrieve a page of publishers using pagination
        Page<Publisher> publishersPage = publisherRepository.findAll(PageRequest.of(page, size));

        // Add pagination information to the model
        model.addAttribute("pagedPublishers", publishersPage);

        return "publishers/manage-publishers";
    }

    @GetMapping("/addpublisher")
    public String showAddPublisherForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "publishers/add-publisher";
    }

    @PostMapping("/addpublisher")
    public String addPublisher(@ModelAttribute("publisher") Publisher publisher, RedirectAttributes redirectAttributes) {
        publisherRepository.save(publisher);
        redirectAttributes.addFlashAttribute("message", "Publisher added successfully");
        return "redirect:/publishers";
    }

    @GetMapping("/editpublisher/{id}")
    public String showEditPublisherForm(@PathVariable("id") Long id, Model model) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher ID: " + id));
        model.addAttribute("publisher", publisher);
        return "publishers/edit-publisher";
    }

    @PostMapping("/editpublisher/{id}")
    public String updatePublisher(@PathVariable("id") Long id, @ModelAttribute("publisher") Publisher publisherDetails, RedirectAttributes redirectAttributes) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher ID: " + id));
        publisher.setPublisherName(publisherDetails.getPublisherName());
        publisherRepository.save(publisher);
        redirectAttributes.addFlashAttribute("message", "Publisher updated successfully");
        return "redirect:/publishers";
    }

    @GetMapping("/deletepublisher/{id}")
    public String deletePublisher(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher ID: " + id));
        publisherRepository.delete(publisher);
        redirectAttributes.addFlashAttribute("message", "Publisher deleted successfully");
        return "redirect:/publishers";
    }
}
