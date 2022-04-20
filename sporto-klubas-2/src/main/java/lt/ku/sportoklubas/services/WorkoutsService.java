package lt.ku.sportoklubas.services;

import lt.ku.sportoklubas.entities.Workout;
import lt.ku.sportoklubas.repositories.WorkoutsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutsService {

    private WorkoutsRepository workoutsRepository;

    @Autowired
    public WorkoutsService(WorkoutsRepository workoutsRepository) {
        this.workoutsRepository = workoutsRepository;
    }

    public Workout addWorkout(Workout workout) {
        return workoutsRepository.save(workout);
    }

    public List<Workout> getWorkouts() {
        return workoutsRepository.findAll();
    }

    public Long getWorkoutsCount() {
        return workoutsRepository.count();
    }

    public Workout updateWorkout(Workout workout) {
        Workout old = workoutsRepository.getById(workout.getId());

        old.setName(workout.getName());
        old.setDate(workout.getDate());
        old.setPlaces(workout.getPlaces());
        old.setLocation(workout.getLocation());

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
