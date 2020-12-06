package btg.spring.boot.userdetails.dao;

import btg.spring.boot.userdetails.model.Address;
import btg.spring.boot.userdetails.model.PersonName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long>{

    Optional<Address> findByStreetAndCityAndStateAndPostCode(String street, String city, String state, Long postCode);
}
