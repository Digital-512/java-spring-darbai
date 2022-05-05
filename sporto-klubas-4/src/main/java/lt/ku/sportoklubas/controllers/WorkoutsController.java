package lt.ku.sportoklubas.controllers;

import lt.ku.sportoklubas.entities.Workout;
import lt.ku.sportoklubas.services.FileStorageService;
import lt.ku.sportoklubas.services.WorkoutsService;
import lt.ku.sportoklubas.validators.WorkoutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/workouts")
public class WorkoutsController {

    private WorkoutsService workoutsService;
    private FileStorageService fileStorageService;

    @Autowired
    public WorkoutsController(WorkoutsService workoutsService, FileStorageService fileStorageService) {
        this.workoutsService = workoutsService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public String workouts(Model model) {
        model.addAttribute("workouts", workoutsService.getWorkouts());
        return "pages/workouts";
    }

    @GetMapping("/add")
    public String workoutAddGet(Model model) {
        model.addAttribute("workout", new WorkoutValidator());
        return "pages/workout_add";
    }

    @GetMapping("/edit/{id}")
    public String workoutEditGet(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("workout", workoutsService.getWorkout(id));
        return "pages/workout_edit";
    }

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String workoutAddPost(@Valid @ModelAttribute("workout") WorkoutValidator workout, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/workout_add";
        }

        Workout workoutDb = workoutsService.updateWorkout(workout);
        if (workout.getDocument() != null) {
            fileStorageService.store(workout.getDocument(), workoutDb.getId().toString() + "_workout_doc");
        }
        return "redirect:/workouts/";
    }

    @PostMapping(value = "/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String workoutEditPost(@PathVariable("id") Integer id, @Valid @ModelAttribute("workout") WorkoutValidator workout, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/workout_edit";
        }

        Workout workoutDb = workoutsService.updateWorkout(workout);
        if (workout.getDocument() != null) {
            fileStorageService.store(workout.getDocument(), workoutDb.getId().toString() + "_workout_doc");
        }

        return "redirect:/workouts/";
    }

    @GetMapping("/delete/{id}")
    public String workoutDelete(@PathVariable("id") Integer id) {
        workoutsService.deleteWorkout(id);
        return "redirect:/workouts/";
    }

    @GetMapping("/document/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveDocument(@PathVariable Integer id) {
        Resource file = fileStorageService.loadFile(id.toString() + "_workout_doc");
        Workout workout = workoutsService.getWorkout(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + workout.getDocument() + "\"").body(file);
    }
}
