package de.mischagluzman.kundenverwaltung.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.mischagluzman.kundenverwaltung.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
}
