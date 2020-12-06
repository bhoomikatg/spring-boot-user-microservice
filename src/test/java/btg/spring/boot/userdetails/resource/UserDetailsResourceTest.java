package btg.spring.boot.userdetails.resource;

import btg.spring.boot.userdetails.dao.AddressRepository;
import btg.spring.boot.userdetails.dao.NameRepository;
import btg.spring.boot.userdetails.dao.UserRepository;
import btg.spring.boot.userdetails.model.Address;
import btg.spring.boot.userdetails.model.PersonName;
import btg.spring.boot.userdetails.model.User;
import btg.spring.boot.userdetails.service.LoggingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserDetailsResource.class)
public class UserDetailsResourceTest {

    @Autowired
    MockMvc mvc;

    User testUser;
    @Autowired
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private NameRepository nameRepository;
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private LoggingService loggingService;

    @Before
    public void setUp() {
        testUser = new User(10L, "F", new PersonName("Ms", "testFirstName", "testLastName"), new Address("walker street", "Sydney", "NSW", 2000L));
        userRepository.save(testUser);
        Mockito.when(userRepository.findById(testUser.getEmpId()))
                .thenReturn(Optional.ofNullable(testUser));
    }

    @Test
    public void testGetUser() {

        try {
            mvc.perform(get("/user/10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(4))
                    .andExpect(jsonPath("$.empId").value(testUser.getEmpId()))
                    .andExpect(jsonPath("$.name.title").value(testUser.getName().getTitle()))
                    .andExpect(jsonPath("$.name.firstName").value(testUser.getName().getFirstName()))
                    .andExpect(jsonPath("$.name.lastName").value(testUser.getName().getLastName()))
                    .andExpect(jsonPath("$.gender").value(testUser.getGender()))
                    .andExpect(jsonPath("$.address.city").value(testUser.getAddress().getCity()))
                    .andExpect(jsonPath("$.address.street").value(testUser.getAddress().getStreet()))
                    .andExpect(jsonPath("$.address.state").value(testUser.getAddress().getState()))
                    .andExpect(jsonPath("$.address.postCode").value(testUser.getAddress().getPostCode()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateUser() {
        String updateJson = "{\"empId\" : \"10\" , \"name\" : {\"firstName\" : \"xyz\", \"lastName\" : \"abc\"}}";

        try {
            mvc.perform(post("/user/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updateJson))
                    .andExpect(status().isOk());

            mvc.perform(get("/user/10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(4))
                    .andExpect(jsonPath("$.empId").value(testUser.getEmpId()))
                    .andExpect(jsonPath("$.name.title").value(testUser.getName().getTitle()))
                    .andExpect(jsonPath("$.name.firstName").value("xyz"))
                    .andExpect(jsonPath("$.name.lastName").value("abc"))
                    .andExpect(jsonPath("$.gender").value(testUser.getGender()))
                    .andExpect(jsonPath("$.address.city").value(testUser.getAddress().getCity()))
                    .andExpect(jsonPath("$.address.street").value(testUser.getAddress().getStreet()))
                    .andExpect(jsonPath("$.address.state").value(testUser.getAddress().getState()))
                    .andExpect(jsonPath("$.address.postCode").value(testUser.getAddress().getPostCode()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
