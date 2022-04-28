package lt.ku.sportoklubas.entities;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Vardo laukelis negali būti tuščias.")
    @Length(min = 3, max = 20, message = "Vardas turi būti ilgesnis nei 3 simboliai ir trumpesnis už 20 simbolių.")
    @Column(nullable = false, length = 64)
    private String name;

    @NotBlank(message = "Pavardės laukelis negali būti tuščias.")
    @Length(min = 3, max = 25, message = "Pavardė turi būti ilgesnė nei 3 simboliai ir trumpesnė už 25 simbolius.")
    @Column(nullable = false, length = 64)
    private String surname;

    @NotBlank(message = "Vartotojo vardo laukelis negali būti tuščias.")
    @Length(min = 3, max = 25, message = "Vartotojo vardas turi būti ilgesnis nei 3 simboliai ir trumpesnis už 25 simbolius.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Slaptažodžio laukelis negali būti tuščias.")
    @Length(min = 6, message = "Slaptažodį turi sudaryti mažiausiai 6 simboliai.")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 32)
    private String type = "user";

    @NotBlank(message = "El. pašto laukelis negali būti tuščias.")
    @Email(message = "Klaidingas el. pašto adreso formatas.")
    @Column(nullable = false, length = 128)
    private String email;

    // https://stackabuse.com/java-regular-expressions-validate-phone-number/
    @NotBlank(message = "Telefono numerio laukelis negali būti tuščias.")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$" + "| ((\\(\\d{3}\\) ?)|(\\d{3}-))?\\d{3}-\\d{4}" + "|^\\+\\d{10,12}", message = "Klaidingas telefono numerio formatas.")
    @Length(max = 15, message = "Telefono numeris turi būti ne ilgesnis nei 15 simbolių.")
    @Column(length = 15)
    private String phone;

    @NotNull(message = "Būsenos laukelis negali būti tuščias.")
    @Column(nullable = false)
    private Integer status = 1;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Registration> registrations;

    public Client() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> auth = new HashSet<>();
        auth.add(new SimpleGrantedAuthority(this.type));
        return auth;
    }

    public boolean hasAuthority(String name) {
        return getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(name));
    }
}
