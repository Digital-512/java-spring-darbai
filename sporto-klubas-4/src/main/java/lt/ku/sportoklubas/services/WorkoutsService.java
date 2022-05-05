package lt.ku.sportoklubas.services;

import lt.ku.sportoklubas.entities.Workout;
import lt.ku.sportoklubas.repositories.WorkoutsRepository;
import lt.ku.sportoklubas.validators.WorkoutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WorkoutsService {

    private WorkoutsRepository workoutsRepository;

    @Autowired
    public WorkoutsService(WorkoutsRepository workoutsRepository) {
        this.workoutsRepository = workoutsRepository;
    }

    public List<Workout> getWorkouts() {
        return workoutsRepository.findAll();
    }

    public Long getWorkoutsCount() {
        return workoutsRepository.count();
    }

    public Workout updateWorkout(WorkoutValidator workout) {
        Workout old = null;

        if (workout.getId() != null) {
            old = workoutsRepository.getById(workout.getId());
        } else {
            old = new Workout(workout);
        }

        old.setName(workout.getName());
        old.setDate(workout.getDate());
        old.setPlaces(workout.getPlaces());
        old.setLocation(workout.getLocation());

        if (!Objects.equals(workout.getDocument().getOriginalFilename(), "") && workout.getDocument() != null) {
            old.setDocument(workout.getDocument().getOriginalFilename());
        }

        workoutsRepository.save(old);
        return old;
    }

    public void deleteWorkout(Integer id) {
        workoutsRepository.deleteById(id);
    }

    public Workout getWorkout(Integer id) {
        return workoutsRepository.getById(id);
    }
}
