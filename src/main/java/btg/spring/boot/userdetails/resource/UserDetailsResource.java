package btg.spring.boot.userdetails.resource;

import btg.spring.boot.userdetails.dao.AddressRepository;
import btg.spring.boot.userdetails.dao.NameRepository;
import btg.spring.boot.userdetails.dao.UserRepository;
import btg.spring.boot.userdetails.model.Address;
import btg.spring.boot.userdetails.model.PersonName;
import btg.spring.boot.userdetails.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserDetailsResource {

    private static final ResponseEntity<String> GENERAL_ERROR_RESPONSE = new ResponseEntity<>("Something went wrong, please check the inputs", HttpStatus.INTERNAL_SERVER_ERROR);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping({"/{employeeId}"})
    @HystrixCommand(fallbackMethod = "fallbackForGet")
    public Optional<User> getUser(@PathVariable Long employeeId) {
        return userRepository.findById(employeeId);
    }

    @PostMapping("/update")
    @Transactional(rollbackFor = {RuntimeException.class})
    @HystrixCommand(fallbackMethod = "fallbackForUpdate")
    public ResponseEntity updateUser(@RequestBody String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<User> typeReference = new TypeReference<User>() {
        };
        User user = null;
        try {
            user = objectMapper.readValue(request, typeReference);
        } catch (IOException e) {
            return GENERAL_ERROR_RESPONSE;
        }
        Optional<User> existingUser = userRepository.findById(user.getEmpId());
        if (existingUser.isPresent()) {
            user = existingUser.get().merge(user);
        }
        // check if an entity with same name already exists, reuse that
        PersonName userName = user.getName();
        if (userName != null) {
            Optional<PersonName> name = nameRepository.findByTitleAndFirstNameAndLastName(userName.getTitle(), userName.getFirstName(), userName.getLastName());
            if (name.isPresent()) {
                user.setName(name.get());
            }
        }
        // check if an entity with same address already exists, reuse that
        Address userAddress = user.getAddress();
        if (userAddress!= null) {
            Optional<Address> address = addressRepository.findByStreetAndCityAndStateAndPostCode(
                    userAddress.getStreet(), userAddress.getCity(), userAddress.getState(), userAddress.getPostCode());
            if (address.isPresent()) {
                user.setAddress(address.get());
            }
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @GetMapping("/invalidEmpId")
    public String invalidEmpId() {
        return "Employee id must be numeric";
    }

    public Optional<User> fallbackForGet(Long employeeId) {
        return Optional.of(new User());
    }

    public ResponseEntity fallbackForUpdate(String request) {
        return new ResponseEntity<>("There are some technical issues being experienced, please retry after some time.", HttpStatus.OK);
    }
}
