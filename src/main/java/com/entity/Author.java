package com.entity;

import jakarta.persistence.*;

@Entity
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String authorName;
    
    // Constructors, getters, and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Author(Long id, String authorName) {
		super();
		this.id = id;
		this.authorName = authorName;
	}
	public Author() {
		super();
	}    
}
