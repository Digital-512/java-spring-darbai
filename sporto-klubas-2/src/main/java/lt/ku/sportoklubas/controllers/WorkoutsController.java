package lt.ku.sportoklubas.controllers;

import lt.ku.sportoklubas.entities.Workout;
import lt.ku.sportoklubas.services.WorkoutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/workouts")
public class WorkoutsController {

    private WorkoutsService workoutsService;

    @Autowired
    public WorkoutsController(WorkoutsService workoutsService) {
        this.workoutsService = workoutsService;
    }

    @GetMapping("/")
    public String workouts(Model model) {
        model.addAttribute("workouts", workoutsService.getWorkouts());
        return "pages/workouts";
    }

    @GetMapping("/add")
    public String workoutAddGet(Model model) {
        model.addAttribute("workout", new Workout());
        return "pages/workout_add";
    }

    @GetMapping("/edit/{id}")
    public String workoutEditGet(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("workout", workoutsService.getWorkout(id));
        return "pages/workout_edit";
    }

    @PostMapping("/add")
    public String workoutAddPost(@Valid @ModelAttribute Workout workout, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/workout_add";
        }
        workoutsService.addWorkout(workout);
        return "redirect:/workouts/";
    }

    @PostMapping("/edit/{id}")
    public String workoutEditPost(@PathVariable("id") Integer id, @Valid @ModelAttribute Workout workout, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/workout_edit";
        }
        workoutsService.updateWorkout(workout);
        return "redirect:/workouts/";
    }

    @GetMapping("/delete/{id}")
    public String workoutDelete(@PathVariable("id") Integer id) {
        workoutsService.deleteWorkout(id);
        return "redirect:/workouts/";
    }
}
