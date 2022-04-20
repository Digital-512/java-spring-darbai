package lt.ku.sportoklubas.repositories;

import lt.ku.sportoklubas.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutsRepository extends JpaRepository<Workout, Integer> {
}
