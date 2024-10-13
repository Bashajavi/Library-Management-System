package com.entity;

import jakarta.persistence.*;

@Entity
public class Publisher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String publisherName;
    // Constructors, getters, and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public Publisher(Long id, String publisherName) {
		super();
		this.id = id;
		this.publisherName = publisherName;
	}
	public Publisher() {
		super();
	}    
}
