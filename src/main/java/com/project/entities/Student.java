package com.project.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Student",uniqueConstraints = @UniqueConstraint(columnNames = "enrollmentId"))
public class Student {
	@Id
    @Column(unique = true, nullable = false)
	@NotBlank
	@Pattern(regexp = "^MAGIC\\d{4}$")
	private String enrollmentId;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@NotBlank(message = "Address cannnot be Blank")
	@NotNull(message = "Name is mandatory")
	@Pattern(regexp = "[A-Za-z' ']*", message = "Only alphabets are allowed")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;

	@NotBlank(message = "Date of birth cannot be blank")
	@NotNull(message = "Date of birth is mandatory")
	private String dob;

	@NotBlank(message = "Branch cannnot be Blank")
	@Pattern(regexp = "^(Computer science and Engg|Electrical engineering|Mechanical engineering)$")
	@NotNull(message = "Branch  is mandatory")
	private String branch;

	

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	

}
