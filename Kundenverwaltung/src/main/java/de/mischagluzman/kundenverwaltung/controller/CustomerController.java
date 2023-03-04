package de.mischagluzman.kundenverwaltung.controller;

import java.util.Comparator;
import java.util.List; 
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import de.mischagluzman.kundenverwaltung.model.Customer;
import de.mischagluzman.kundenverwaltung.model.Sex;
import de.mischagluzman.kundenverwaltung.repository.CustomerRepository;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
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

	// shows additional customers information (email, sex)
	@GetMapping("/customerInformation/{id}")
	public String showCustomerInformation(Model model, @PathVariable("id") int id, @ModelAttribute Customer customer) {
	
		model.addAttribute("customer", customerRepository.findById(customer.getId()).orElse(null));
		return "customerInformation";
	}
	
	// editing data of an existing customer 
	@GetMapping("/editCustomer/{id}")
	public String showEditCustomerForm(@ModelAttribute Customer customer, Model model, @PathVariable("id") int id){
		
		model.addAttribute("customer", customerRepository.findById(customer.getId()).orElse(null));
		return "editCustomer";
	}
	
	// updates the edited information and shows the edited customer
	@PostMapping("/editCustomer/{id}")
	public String updateCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, @PathVariable("id") int id) {
		
		// shows error page with error messages if user input is invalid
		if(result.hasErrors()) {
			setUpModelWithDefaultMessages(model, result);
			return "error";
		}
		customerRepository.save(customer);
		return showCustomerInformation(model, id, customer);
	}
	
	// deletes a customer from database and shows all Customers
	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(Model model,@ModelAttribute Customer customer) {
		
		customerRepository.delete(customer);
		return showIndex(model, customer);
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
