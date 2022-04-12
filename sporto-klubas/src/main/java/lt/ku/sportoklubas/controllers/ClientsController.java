package lt.ku.sportoklubas.controllers;

import lt.ku.sportoklubas.entities.Client;
import lt.ku.sportoklubas.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    private ClientService clientService;

    @Autowired
    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String clients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "pages/clients";
    }

    @GetMapping("/add")
    public String clientAddGet() {
        return "pages/client_add";
    }

    @GetMapping("/edit/{id}")
    public String clientEditGet(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("client", clientService.getClient(id));
        return "pages/client_edit";
    }

    @PostMapping("/add")
    public String clientAddPost(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam Integer status
    ) {
        Client client = new Client(name, surname, email, phone, status);
        clientService.addClient(client);
        return "redirect:/clients/";
    }

    @PostMapping("/edit/{id}")
    public String clientEditPost(@PathVariable("id") Integer id, @ModelAttribute Client client) {
        clientService.updateClient(client);
        return "redirect:/clients/";
    }

    @GetMapping("/delete/{id}")
    public String clientDelete(@PathVariable("id") Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients/";
    }
}
