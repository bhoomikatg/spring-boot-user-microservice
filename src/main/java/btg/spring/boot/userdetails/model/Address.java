package btg.spring.boot.userdetails.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String street;
    private String city;
    private String state;
    private Long postCode;

    public Address(String street, String city, String state, long postCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
    }

    public Address merge(Address address) {
        if (address == null) {
            return this;
        }
        if (address.getCity() != null) {
            this.setCity(address.getCity());
        }
        if (address.getStreet() != null) {
            this.setStreet(address.getStreet());
        }
        if (address.getState() != null) {
            this.setState(address.getState());
        }
        if (address.getPostCode() != null) {
            this.setPostCode(address.getPostCode());
        }
        return this;
    }
}
