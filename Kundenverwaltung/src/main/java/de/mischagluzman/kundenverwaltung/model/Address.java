package de.mischagluzman.kundenverwaltung.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
	private int addressId;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "house_number")
	private String houseNumber;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "customer_address_id")
	private int customerAddressId;
	

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(int addressId, String street, String houseNumber, String postalCode, String city, String country,
			int customerAddressId) {
		super();
		this.addressId = addressId;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.customerAddressId = customerAddressId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCustomerAddressId() {
		return customerAddressId;
	}

	public void setCustomerAddressId(int customerAddressId) {
		this.customerAddressId = customerAddressId;
	}
	
	
	

}
