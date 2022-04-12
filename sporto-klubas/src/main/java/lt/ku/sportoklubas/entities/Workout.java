package lt.ku.sportoklubas.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column
    private String places;

    @Column
    private String location;

    @OneToMany(mappedBy = "workout", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Registration> registrations;

    public Workout() {
    }

    public Workout(String name, String date, String places, String location) {
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
        return date.toString();
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date);
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
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
