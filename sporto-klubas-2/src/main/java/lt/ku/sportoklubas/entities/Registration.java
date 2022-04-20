package lt.ku.sportoklubas.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @NotNull(message = "Datos laukelis negali būti tuščias.")
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    public Registration() {
        registrationDate = LocalDateTime.now();
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
        return registrationDate.toString().substring(0, 16);
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = LocalDateTime.parse(registrationDate);
    }
}
