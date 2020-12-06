package btg.spring.boot.userdetails.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonName {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    public PersonName(String title, String firstName, String lastName) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Merges properties of given object with this object and return the merged object.
     * Any property of this object will be replaced with the value of that property in the give object provided value is not null.
     */
    public PersonName merge(PersonName u) {
        if (u.getFirstName() != null) {
            this.setFirstName(u.getFirstName());
        }
        if (u.getLastName() != null) {
            this.setLastName(u.getLastName());
        }
        if (u.getTitle() != null) {
            this.setTitle(u.getTitle());
        }
        return this;
    }
}
