package lt.ku.sportoklubas.services;

import lt.ku.sportoklubas.entities.Client;
import lt.ku.sportoklubas.entities.Registration;
import lt.ku.sportoklubas.entities.Workout;
import lt.ku.sportoklubas.repositories.RegistrationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationsService {

    private RegistrationsRepository registrationsRepository;

    @Autowired
    public RegistrationsService(RegistrationsRepository registrationsRepository) {
        this.registrationsRepository = registrationsRepository;
    }

    public Registration addRegistration(Registration registration) {
        return registrationsRepository.save(registration);
    }

    public List<Registration> getRegistrations() {
        return registrationsRepository.findAll();
    }

    public Long getRegistrationsCount() {
        return registrationsRepository.count();
    }

    public Registration updateRegistration(Registration registration) {
        Registration old = registrationsRepository.getById(registration.getId());

        old.setClient(registration.getClient());
        old.setWorkout(registration.getWorkout());
        old.setRegistrationDate(registration.getRegistrationDate());

        registrationsRepository.save(old);
        return old;
    }

    public void deleteRegistration(Integer id) {
        registrationsRepository.deleteById(id);
    }

    public Registration getRegistration(Integer id) {
        return registrationsRepository.getById(id);
    }

    public boolean isClientRegisteredInWorkout(Client client, Workout workout) {
        return !registrationsRepository.findByClientAndWorkout(client, workout).isEmpty();
    }

    public Integer placesAvailableInWorkout(Workout workout) {
        return workout.getPlaces() - registrationsRepository.countAllByWorkout(workout);
    }

    public Integer countRegistrationsByClient(Client client) {
        return registrationsRepository.countAllByClient(client);
    }

    public List<Registration> getRegistrationsByClient(Client client) {
        return registrationsRepository.findByClient(client);
    }
}
