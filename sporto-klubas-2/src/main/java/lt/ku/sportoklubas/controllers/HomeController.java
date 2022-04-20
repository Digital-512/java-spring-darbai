package lt.ku.sportoklubas.controllers;

import lt.ku.sportoklubas.services.ClientService;
import lt.ku.sportoklubas.services.RegistrationsService;
import lt.ku.sportoklubas.services.WorkoutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private ClientService clientService;
    private WorkoutsService workoutsService;
    private RegistrationsService registrationsService;

    @Autowired
    public HomeController(ClientService clientService, WorkoutsService workoutsService, RegistrationsService registrationsService) {
        this.clientService = clientService;
        this.workoutsService = workoutsService;
        this.registrationsService = registrationsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("clientsCount", clientService.getClientsCount());
        model.addAttribute("workoutsCount", workoutsService.getWorkoutsCount());
        model.addAttribute("registrationsCount", registrationsService.getRegistrationsCount());
        return "pages/home";
    }
}
