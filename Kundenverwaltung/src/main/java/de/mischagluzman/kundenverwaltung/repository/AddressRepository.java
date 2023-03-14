package de.mischagluzman.kundenverwaltung.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.mischagluzman.kundenverwaltung.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	 List<Optional<Address>> findByCustomerAddressId(int customerAddressId);

}
