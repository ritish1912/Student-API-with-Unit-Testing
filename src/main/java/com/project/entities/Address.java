package com.project.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Address")
public class Address {
	@Id
	@GeneratedValue
	private Long id;

	@NotBlank(message = "City cannnot be Blank")
	@NotNull(message = "City is mandatory")
	private String city;

	@NotBlank(message = "State cannnot be Blank")
	@NotNull(message = "State is mandatory")
	private String state;

	@NotBlank(message = "Pincode cannnot be Blank")
	@NotNull(message = "Pincode is mandatory")
	private String pincode;



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
}
