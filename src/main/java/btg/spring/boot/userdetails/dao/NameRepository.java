package btg.spring.boot.userdetails.dao;

import btg.spring.boot.userdetails.model.PersonName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NameRepository extends JpaRepository<PersonName,Long>{

    Optional<PersonName> findByTitleAndFirstNameAndLastName(String title, String firstName, String lastName);
}
