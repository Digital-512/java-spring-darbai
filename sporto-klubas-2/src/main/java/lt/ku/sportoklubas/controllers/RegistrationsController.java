package lt.ku.sportoklubas.controllers;

import lt.ku.sportoklubas.entities.Client;
import lt.ku.sportoklubas.entities.Registration;
import lt.ku.sportoklubas.entities.Workout;
import lt.ku.sportoklubas.services.ClientService;
import lt.ku.sportoklubas.services.RegistrationsService;
import lt.ku.sportoklubas.services.WorkoutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registrations")
public class RegistrationsController {

    private RegistrationsService registrationsService;
    private ClientService clientService;
    private WorkoutsService workoutsService;

    @Autowired
    public RegistrationsController(RegistrationsService registrationsService, ClientService clientService, WorkoutsService workoutsService) {
        this.registrationsService = registrationsService;
        this.clientService = clientService;
        this.workoutsService = workoutsService;
    }

    @GetMapping("/")
    public String registrations(Model model) {
        model.addAttribute("registrations", registrationsService.getRegistrations());
        return "pages/registrations";
    }

    @GetMapping("/add")
    public String registrationAddGet(Model model) {
        model.addAttribute("clients", clientService.getClients());
        model.addAttribute("workouts", workoutsService.getWorkouts());
        return "pages/registration_add";
    }

    @GetMapping("/edit/{id}")
    public String registrationEditGet(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("clients", clientService.getClients());
        model.addAttribute("workouts", workoutsService.getWorkouts());
        model.addAttribute("registration", registrationsService.getRegistration(id));
        return "pages/registration_edit";
    }

    @PostMapping("/add")
    public String registrationAddPost(
            @RequestParam Integer clientId,
            @RequestParam Integer workoutId,
            @RequestParam String registrationDate,
            Model model
    ) {
        Client client = clientService.getClient(clientId);
        Workout workout = workoutsService.getWorkout(workoutId);

        // Patikrinti, ar liko laisvų vietų treniruotės užsiėmime.
        if (registrationsService.placesAvailableInWorkout(workout) < 1) {
            model.addAttribute("error", "Treniruotėje " + workout.getName() + " nebeliko laisvų vietų, todėl daugiau klientų užregistruoti negalima.");
            return registrationAddGet(model);
        }

        // Patikrinti, ar klientas jau užregistruotas į nurodytą treniruotę.
        if (registrationsService.isClientRegisteredInWorkout(client, workout)) {
            model.addAttribute("error", "Šis klientas jau yra priregistruotas treniruotėje " + workout.getName() + ".");
            return registrationAddGet(model);
        }

        Registration registration = new Registration(client, workout, registrationDate);
        registrationsService.addRegistration(registration);
        return "redirect:/registrations/";
    }

    @PostMapping("/edit/{id}")
    public String registrationEditPost(@PathVariable("id") Integer id, @ModelAttribute Registration registration) {
        registrationsService.updateRegistration(registration);
        return "redirect:/registrations/";
    }

    @GetMapping("/delete/{id}")
    public String registrationDelete(@PathVariable("id") Integer id) {
        registrationsService.deleteRegistration(id);
        return "redirect:/registrations/";
    }
}
