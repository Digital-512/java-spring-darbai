package lt.ku.sportoklubas.repositories;

import lt.ku.sportoklubas.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationsRepository extends JpaRepository<Registration, Integer> {
}
