package lt.ku.sportoklubas.services;

import lt.ku.sportoklubas.entities.Client;
import lt.ku.sportoklubas.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClientService implements UserDetailsService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Long getClientsCount() {
        return clientRepository.count();
    }

    public Client updateClient(Client client, Client authClient) {
        Client old = clientRepository.getById(client.getId());

        old.setName(client.getName());
        old.setSurname(client.getSurname());
        old.setEmail(client.getEmail());
        old.setPhone(client.getPhone());
        old.setUsername(client.getUsername());

        // Tik administratorius gali nustatyti vartotojo rolę ir būseną.
        if (authClient.hasAuthority("admin")) {
            old.setStatus(client.getStatus());
            old.setType(client.getType());
        }

        // Jeigu vartotojas nekeičia slaptažodžio, gaunama reikšmė `_no_password_change_by_default`.
        if (!Objects.equals(client.getPassword(), "_no_password_change_by_default")) {
            old.setPassword((new BCryptPasswordEncoder()).encode(client.getPassword()));
        }

        clientRepository.save(old);
        return old;
    }

    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }

    public Client getClient(Integer id) {
        return clientRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username);
        if (client == null) {
            throw new UsernameNotFoundException("Klientas nerastas");
        }
        return client;
    }
}
