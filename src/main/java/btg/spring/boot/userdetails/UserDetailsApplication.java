package btg.spring.boot.userdetails;

import btg.spring.boot.userdetails.dao.UserRepository;
import btg.spring.boot.userdetails.model.PersonName;
import btg.spring.boot.userdetails.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@EnableCircuitBreaker
//@EnableEurekaClient
public class UserDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/users.json");
            List<User> users = null;
            try {
                users = objectMapper.readValue(inputStream, typeReference);
                /*for (User user : users) {
                    userRepository.save(user);
                }*/
                User user = users.get(1);
                PersonName name = new PersonName();
                for (int i =1 ; i <=1000000 ; i++) {
                    user.setEmpId((long) i);
                    name.setFirstName("abc" + "-"+ i);
                    name.setLastName("xyz" + "-"+ i);
                    user.setName(name);
                    userRepository.save(user);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
