package services;

import daos.ExerciseDAOImpl;
import daos.interfaces.ExerciseDAO;
import models.Exercise;
import services.interfaces.ExerciseService;

import java.util.List;

public class ExerciseServiceImpl implements ExerciseService {

    ExerciseDAO exerciseDAO = new ExerciseDAOImpl();


    @Override
    public List<Exercise> findAllExercises() {
        return exerciseDAO.findAllExercises();
    }

    @Override
    public void close() {
        exerciseDAO.close();
    }

    @Override
    public void save(Exercise exercise) {
        exerciseDAO.create(exercise);
    }

    @Override
    public void remove(Exercise exercise) {
        exerciseDAO.delete(exercise);
    }

    @Override
    public void modify(Exercise exercise) {
        exerciseDAO.update(exercise);
    }

    @Override
    public Exercise findByID(Long aLong) {
        return exerciseDAO.findByID(aLong);
    }
}
