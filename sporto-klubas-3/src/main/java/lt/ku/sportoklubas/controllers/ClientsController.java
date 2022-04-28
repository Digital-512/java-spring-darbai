package lt.ku.sportoklubas.controllers;

import lt.ku.sportoklubas.entities.Client;
import lt.ku.sportoklubas.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String clientAddGet(Model model) {
        model.addAttribute("client", new Client());
        return "pages/client_add";
    }

    @GetMapping("/edit/{id}")
    public String clientEditGet(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("client", clientService.getClient(id));
        return "pages/client_edit";
    }

    @GetMapping("/edit-account")
    public String accountEditGet(Model model, Authentication auth) {
        Client authClient = (Client) auth.getPrincipal();
        model.addAttribute("client", clientService.getClient(authClient.getId()));
        model.addAttribute("editAccount", "true");
        return "pages/client_edit";
    }

    @PostMapping("/add")
    public String clientAddPost(@Valid @ModelAttribute Client client, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/client_add";
        }
        client.setPassword((new BCryptPasswordEncoder()).encode(client.getPassword()));
        clientService.addClient(client);
        return "redirect:/clients/";
    }

    @PostMapping("/edit/{id}")
    public String clientEditPost(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute Client client,
            BindingResult result,
            Authentication auth
    ) {
        if (result.hasErrors()) {
            return "pages/client_edit";
        }
        Client authClient = (Client) auth.getPrincipal();
        clientService.updateClient(client, authClient);
        return "redirect:/clients/";
    }

    @PostMapping("/edit-account")
    public String accountEditPost(
            @Valid @ModelAttribute Client client,
            BindingResult result,
            Authentication auth
    ) {
        if (result.hasErrors()) {
            return "pages/client_edit";
        }
        Client authClient = (Client) auth.getPrincipal();
        client.setId(authClient.getId());
        clientService.updateClient(client, authClient);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String clientDelete(@PathVariable("id") Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients/";
    }
}
