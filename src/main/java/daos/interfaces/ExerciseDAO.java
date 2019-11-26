package daos.interfaces;

import models.Exercise;

import java.util.List;

public interface ExerciseDAO extends GenericDAO<Exercise, Long>{

    List<Exercise> findAllExercises();
}
