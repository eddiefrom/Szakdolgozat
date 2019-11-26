package daos;

import daos.interfaces.ExerciseDAO;
import helper.EntityManagerFactoryHelper;
import models.Exercise;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public  class ExerciseDAOImpl implements ExerciseDAO {

    private EntityManagerFactory emf ;
    private EntityManager em;

    public ExerciseDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public void create(Exercise exercise) {

        em.getTransaction().begin();
        em.persist(exercise);
        em.getTransaction().commit();
    }

    @Override
    public void update(Exercise exercise) {

        em.getTransaction().begin();
        em.merge(exercise);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(Exercise exercise) {

        em.getTransaction().begin();
        em.remove(em.contains(exercise) ? exercise : em.merge(exercise));
        em.getTransaction().commit();
    }

    @Override
    public Exercise findByID(Long id) {

        return  em.find(Exercise.class, id);

    }

    @Override
    public List<Exercise> findAllExercises() {

        TypedQuery<Exercise> query = em.createQuery(
                "select s from models.Exercise s ", Exercise.class
        );
        return query.getResultList();
    }

    public void close()
    {
        emf.close();
    }
}
