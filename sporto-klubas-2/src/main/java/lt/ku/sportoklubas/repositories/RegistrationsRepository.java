package lt.ku.sportoklubas.repositories;

import lt.ku.sportoklubas.entities.Client;
import lt.ku.sportoklubas.entities.Registration;
import lt.ku.sportoklubas.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationsRepository extends JpaRepository<Registration, Integer> {

    List<Registration> findByClientAndWorkout(Client client, Workout workout);

    Integer countAllByWorkout(Workout workout);
}
