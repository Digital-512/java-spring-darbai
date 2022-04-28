package lt.ku.sportoklubas.repositories;

import lt.ku.sportoklubas.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    // Repozitorijoje sukurkime metodą kuris gražintų vartotoją (klientą) pagal jo vardą.
    Client findByUsername(String username);
}
