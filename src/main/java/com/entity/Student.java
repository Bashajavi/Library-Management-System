package com.entity;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String studentName;
    private String email;
    private String mobileNumber;    
    private String rollNo;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    // Constructors, getters, and setters
    public Student(Long id, String studentName, String email, String mobileNumber, String password, String rollNo,
                   Department department) {
        this.id = id;
        this.studentName = studentName;
        this.email = email;
        this.mobileNumber = mobileNumber;    
        this.rollNo = rollNo;
        this.department = department;
    }

	public Student() {
		super();
	}    
    // Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}	
}
