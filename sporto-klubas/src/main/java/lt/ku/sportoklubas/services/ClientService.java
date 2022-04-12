package lt.ku.sportoklubas.services;

import lt.ku.sportoklubas.entities.Client;
import lt.ku.sportoklubas.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

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

    public Client updateClient(Client client) {
        Client old = clientRepository.getById(client.getId());

        old.setName(client.getName());
        old.setSurname(client.getSurname());
        old.setEmail(client.getEmail());
        old.setPhone(client.getPhone());
        old.setStatus(client.getStatus());

        clientRepository.save(old);
        return old;
    }

    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }

    public Client getClient(Integer id) {
        return clientRepository.getById(id);
    }
}
