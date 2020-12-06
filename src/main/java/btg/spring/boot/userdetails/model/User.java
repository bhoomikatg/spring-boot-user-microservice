package btg.spring.boot.userdetails.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    private Long empId;

    private String gender;

    @ManyToOne(targetEntity = PersonName.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "nameId")
    private PersonName name;

    @ManyToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;

    /**
     * Merges properties of given user object with this object and return the merged object.
     * Any property of this object will be replaced with the value of that property in the give object provided value is not null.
     */
    public User merge(User u) {
        if (u.getName() != null) {
            this.setName(this.getName().merge(u.getName()));
        }
        if (u.getGender() != null) {
            this.setGender(u.getGender());
        }
        if (u.getAddress() != null) {
            this.setAddress(this.getAddress().merge(u.getAddress()));
        }
        return this;
    }
}
