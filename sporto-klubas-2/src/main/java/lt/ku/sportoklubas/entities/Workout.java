package lt.ku.sportoklubas.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Pavadinimo laukelis negali būti tuščias.")
    @Length(min = 3, max = 128, message = "Pavadinimas turi būti ilgesnis nei 3 simboliai ir trumpesnis už 128 simbolius.")
    @Column(nullable = false, length = 128)
    private String name;

    @NotNull(message = "Datos laukelis negali būti tuščias.")
    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull(message = "Vietų skaičiaus laukelis negali būti tuščias.")
    @Min(value = 0, message = "Vietų skaičius negali būti neigiamas.")
    @Column(nullable = false)
    private Integer places;

    @NotBlank(message = "Adreso laukelis negali būti tuščias.")
    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Registration> registrations;

    public Workout() {
        this.date = LocalDateTime.now();
    }

    public Workout(String name, String date, Integer places, String location) {
        this.name = name;
        this.date = LocalDateTime.parse(date);
        this.places = places;
        this.location = location;
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

    public String getDate() {
        return date.toString().substring(0, 16);
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date);
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public Integer getRegistrationsCount() {
        return registrations.size();
    }
}
