package com.entity;

import jakarta.persistence.*;

@Entity
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String departmentName;
    
    // Constructors, getters, and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department(Long id, String departmentName) {
        super();
        this.id = id;
        this.departmentName = departmentName;
    }

    public Department() {
        super();
    }    
}
