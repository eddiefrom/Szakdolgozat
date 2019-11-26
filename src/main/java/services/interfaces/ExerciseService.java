package services.interfaces;

import models.Exercise;

import java.util.List;

public interface ExerciseService extends GenericService<Exercise, Long> {

    List<Exercise> findAllExercises();
}
