package lt.ku.sportoklubas.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 64)
    private String surname;

    @Column(nullable = false, length = 128)
    private String email;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
