package lt.ku.sportoklubas.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Vardo laukelis negali būti tuščias.")
    @Length(min = 3, max = 64, message = "Vardas turi būti ilgesnis nei 3 simboliai ir trumpesnis už 64 simbolius.")
    @Column(nullable = false, length = 64)
    private String name;

    @NotBlank(message = "Pavardės laukelis negali būti tuščias.")
    @Length(min = 3, max = 64, message = "Pavardė turi būti ilgesnė nei 3 simboliai ir trumpesnė už 64 simbolius.")
    @Column(nullable = false, length = 64)
    private String surname;

    @NotBlank(message = "El. pašto laukelis negali būti tuščias.")
    @Email(message = "Klaidingas el. pašto adreso formatas.")
    @Column(nullable = false, length = 128)
    private String email;

    // https://stackabuse.com/java-regular-expressions-validate-phone-number/
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$"
            + "| ((\\(\\d{3}\\) ?)|(\\d{3}-))?\\d{3}-\\d{4}"
            + "|^\\+\\d{10,12}", message = "Klaidingas telefono numerio formatas.")
    @Length(min = 4, max = 15, message = "Telefono numeris turi būti ilgesnis nei 4 simboliai ir trumpesnis už 15 simbolių.")
    @Column(length = 15)
    private String phone;

    @NotNull(message = "Būsenos laukelis negali būti tuščias.")
    @Column(nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Registration> registrations;

    public Client() {
    }

    public Client(String name, String surname, String email, String phone, Integer status) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public String getNamedStatus() {
        return switch (status) {
            case 1 -> "Aktyvus";
            case 2 -> "Neaktyvus";
            default -> null;
        };
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public Integer getRegistrationsCount() {
        return registrations.size();
    }
}
