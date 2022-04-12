package lt.ku.sportoklubas.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    public Registration() {
    }

    public Registration(Client client, Workout workout, String registrationDate) {
        this.client = client;
        this.workout = workout;
        this.registrationDate = LocalDateTime.parse(registrationDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public String getRegistrationDate() {
        return registrationDate.toString();
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = LocalDateTime.parse(registrationDate);
    }
}
