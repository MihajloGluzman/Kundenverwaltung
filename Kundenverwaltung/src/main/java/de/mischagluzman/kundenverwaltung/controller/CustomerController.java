package de.mischagluzman.kundenverwaltung.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import de.mischagluzman.kundenverwaltung.model.Address;
import de.mischagluzman.kundenverwaltung.model.Customer;
import de.mischagluzman.kundenverwaltung.model.Sex;
import de.mischagluzman.kundenverwaltung.repository.AddressRepository;
import de.mischagluzman.kundenverwaltung.repository.CustomerRepository;
import de.mischagluzman.kundenverwaltung.service.AddressService;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressService addressService;
	
	
	// shows all customers saved in database
	@GetMapping("/")
	public String showIndex(Model model, @ModelAttribute Customer customer) {
	
		// sorting customers by id in natural order
		List<Customer> customerList = customerRepository.findAll();
		customerList.sort(Comparator.comparing(Customer::getId));
		
		// filters customers by sex (female)
		List<Customer> customerListFemale = customerList.stream()
				 .filter(customers -> customers.getSex() == Sex.FEMALE)
				 .collect(Collectors.toList());
		
		model.addAttribute("customerlist", customerList);
		model.addAttribute("customerlistFemale", customerListFemale);
		
		return "index";
	}
	
	// shows a form for adding a new customer
	@GetMapping("/newCustomer")
	public String showAddCustomerForm() {
		return "newCustomer";
	}
	
	// processing the form data 
	@PostMapping("/newCustomer")
	public String addNewCustomer(Model model, @Valid @ModelAttribute Customer customer, BindingResult result){
		
		// shows error page with error messages if user input is invalid
		if(result.hasErrors()) {
		    setUpModelWithDefaultMessages(model, result);
			return "error";
		}
	
		// capitalize first letters of firstName and lastName
		customer.setFirstName(StringUtils.capitalize(customer.getFirstName()));
		customer.setLastName(StringUtils.capitalize(customer.getLastName()));
		
		// saves the new customer and shows all customers in database
		customerRepository.save(customer);
		return showIndex(model, customer);
		
	}

	// shows additional customers information (email, sex, addresses)
	@GetMapping("/customerInformation/{id}")
	public String showCustomerInformation(Model model, @PathVariable("id") int id, @ModelAttribute Customer customer, @ModelAttribute Address address) {
		
		Customer customerById = customerRepository.findById(customer.getId()).orElse(null);
		
		if(customerById != null) {
			model.addAttribute("customer", customerById);
		}
		
		List<Address> addressList = addressService.getAllCustomersAddresses(id);
		model.addAttribute("addressList", addressList);
		
		// displays the current standard address
		Address customersStandardAddress = addressList.stream()
				.filter(customersAddresses -> customersAddresses.isStandardAddress()).findFirst().orElse(null);
		
		if(null != customersStandardAddress) {
			model.addAttribute("standardAddress", customersStandardAddress);
		}
		
		return "customerInformation";
	}
	
	// editing data of an existing customer 
	@GetMapping("/editCustomer/{id}")
	public String showEditCustomerForm(@ModelAttribute Customer customer, Model model, @PathVariable("id") int id){
		
		Customer customerById = customerRepository.findById(customer.getId()).orElse(null);
		
		if(customerById != null) {
			model.addAttribute("customer", customerById);
		}
		
		return "editCustomer";
	}
	
	
	// updates the edited information and shows the edited customer
	@PostMapping("/editCustomer/{id}")
	public String updateCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, @PathVariable("id") int id, @ModelAttribute Address address) {
		
		// shows error page with error messages if user input is invalid
		if(result.hasErrors()) {
			setUpModelWithDefaultMessages(model, result);
			return "error";
		}
		customerRepository.save(customer);
		return showCustomerInformation(model, id, customer, address);
	}
	
	// deletes a customer from database and shows all Customers
	// deletes also all customers addresses 
	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(Model model,@ModelAttribute Customer customer) {
		
		// deletes first all addresses because exception could be thrown
		List<Address> allCustomersAddresses = addressService.getAllCustomersAddresses(customer.getId());
		
		if(!allCustomersAddresses.isEmpty()) {
			addressRepository.deleteAll(allCustomersAddresses);
		}
		
		customerRepository.delete(customer);
		return showIndex(model, customer);
	}
	
	@GetMapping("/addAddress/{id}")
	public String showAddressForm(Model model, @ModelAttribute Address address, @PathVariable("id") int id) {
		
		return "addAddress";
	}
	
	@PostMapping("/addAddress/{id}")
	public String addAddressToCustomer(Model model, @ModelAttribute Address address, @PathVariable("id")int id, @ModelAttribute Customer customer){
		
		address.setCustomerAddressId(customer.getId());
		addressRepository.save(address);
		
		return showCustomerInformation(model, id, customer, address);
	}
	
	// deletes a specific address from a customer when address is present
	@GetMapping("/deleteAddress/{addressId}/{id}")
	public String deleteAddress(Model model,@PathVariable("addressId") int addressId,@PathVariable("id") int id, Customer customer, Address address) {
		
		if(addressRepository.findById(addressId).isPresent()) {
		addressRepository.deleteById(addressId);
		}
		
		return showCustomerInformation(model, id, customer, address);
	}
	
	// shows a form to edit a customers address
	@GetMapping("/editAddress/{addressId}/{id}")
	public String editAddress(@ModelAttribute Address address, Model model, @PathVariable("addressId") int addressId) {
		
		Address addressById = addressRepository.findById(address.getAddressId()).orElse(null);
		
		if(addressById != null) {
			model.addAttribute("address", addressById);
		}
		
		return "editAddress";
	}
	
	// edits a specific address from a customer
	@PostMapping("/editAddress/{addressId}/{id}")
	public String updateCustomer(Model model, @PathVariable("id") int id, Customer customer, @ModelAttribute Address address) {
		
		address.setCustomerAddressId(customer.getId());
		addressRepository.save(address);
		return showCustomerInformation(model, id, customer, address);
	}
	
	// sets a specific address to the standard address and changes the previous standard address to a regular address
	@GetMapping("/setStandardAddress/{addressId}/{id}")
	public String setAddressToStandardAddress(Model model, @PathVariable("id") int id, @PathVariable("addressId") int addressId, Customer customer, @ModelAttribute Address address ) throws Exception {
		
		Address addressById = addressRepository.findById(address.getAddressId()).orElse(null);
		 
		if(addressById != null) {
			handleOldStandardAddress(id);
			addressById.setStandardAddress(true);
			addressRepository.save(addressById);
			return showCustomerInformation(model, id, customer, address);
		}
		throw new Exception("Es wurde keine Adresse gefunden");
	}

	private void handleOldStandardAddress(int id) {
		
		//Example definieren mit Id und isStandard=true
		Address searchAddressWithIdAndStandard = new Address();
		searchAddressWithIdAndStandard.setCustomerAddressId(id);
		searchAddressWithIdAndStandard.setStandardAddress(true);
		Example<Address> addressExample = Example.of(searchAddressWithIdAndStandard);
		//Lesen aus der Db mit Example
		Address oldStandardAddress = addressRepository.findOne(addressExample).orElse(null);
		
		//Wert auf false setzen
		if(oldStandardAddress != null) {
			oldStandardAddress.setStandardAddress(false);
			//in DB speichern
			addressRepository.save(oldStandardAddress);
		}
	}
		
	// shows error page
	@GetMapping("/error")
	public String showErrorPage() {
		return "error";
	}
	
	// set up model with default (error) messages
	private void setUpModelWithDefaultMessages(Model model, BindingResult result) {
		List<String> defaultMessages = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
		model.addAttribute("errorMessage", defaultMessages);
	}

}
