package lt.ku.sportoklubas.entities;

import lt.ku.sportoklubas.validators.WorkoutValidator;

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

    @Column(nullable = false)
    private Integer places;

    @Column(nullable = false)
    private String location;

    private String document = null;

    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Registration> registrations;

    public Workout() {
        this.date = LocalDateTime.now();
    }

    public Workout(WorkoutValidator workoutValidator) {
        this.name = workoutValidator.getName();
        this.date = LocalDateTime.parse(workoutValidator.getDate());
        this.places = workoutValidator.getPlaces();
        this.location = workoutValidator.getLocation();
        this.document = workoutValidator.getDocument().getOriginalFilename();
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
