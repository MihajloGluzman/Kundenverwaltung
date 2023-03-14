package de.mischagluzman.kundenverwaltung.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "last_name")
	@NotBlank(message = "Familienname darf nicht leer sein.")
	private String lastName;
	
	@Column(name = "first_name")
	@NotBlank(message = "Vorname darf nicht leer sein.")
	private String firstName;
	
	@Column(name = "sex")
	private Sex sex;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "mail")
	@Email(message = "Ungültige E-Mail Adresse")
	private String email;

	public Customer() {
		
	}
	
	public Customer(int id, @NotBlank(message = "Familienname darf nicht leer sein.") String lastName,
			@NotBlank(message = "Vorname darf nicht leer sein.") String firstName, Sex sex, Date dateOfBirth,
			@Email(message = "Ungültige E-Mail Adresse") String email) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.sex = sex;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	



	
	

	

	
	
	
}
