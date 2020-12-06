package btg.spring.boot.userdetails;

import btg.spring.boot.userdetails.dao.AddressRepository;
import btg.spring.boot.userdetails.dao.NameRepository;
import btg.spring.boot.userdetails.dao.UserRepository;
import btg.spring.boot.userdetails.model.Address;
import btg.spring.boot.userdetails.model.PersonName;
import btg.spring.boot.userdetails.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableCircuitBreaker
//@EnableEurekaClient
public class UserDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, NameRepository nameRepository, AddressRepository addressRepository) {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/users.json");
            List<User> users = Collections.EMPTY_LIST;
            try {
                users = objectMapper.readValue(inputStream, typeReference);
                for (User user : users) {
                    // check if an entity with same name already exists, reuse that
                    PersonName provideName = user.getName();
                    if (provideName != null) {
                        Optional<PersonName> name = nameRepository.findByTitleAndFirstNameAndLastName(
                                provideName.getTitle(), provideName.getFirstName(), provideName.getLastName());
                        if (name.isPresent()) {
                            user.setName(name.get());
                        }
                    }
                    // check if an entity with same address already exists, reuse that
                    Address providedAddress = user.getAddress();
                    if (providedAddress!= null) {
                        Optional<Address> address = addressRepository.findByStreetAndCityAndStateAndPostCode(
                                providedAddress.getStreet(), providedAddress.getCity(), providedAddress.getState(), providedAddress.getPostCode());
                        if (address.isPresent()) {
                            user.setAddress(address.get());
                        }
                    }
                    userRepository.save(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
