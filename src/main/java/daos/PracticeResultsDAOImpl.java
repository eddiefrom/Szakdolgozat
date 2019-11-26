package daos;

import daos.interfaces.PracticeResultsDAO;
import helper.EntityManagerFactoryHelper;
import models.PracticeResults;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class PracticeResultsDAOImpl implements PracticeResultsDAO {

    private EntityManagerFactory emf ;
    private EntityManager em;

    public PracticeResultsDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public void create(PracticeResults results) {

        em.getTransaction().begin();
        em.persist(results);
        em.getTransaction().commit();
    }

    @Override
    public void update(PracticeResults results) {

        em.getTransaction().begin();
        em.merge(results);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(PracticeResults results) {

        em.getTransaction().begin();
        em.remove(em.contains(results) ? results : em.merge(results));
        em.getTransaction().commit();
    }

    @Override
    public PracticeResults findByID(Long id) {

        return em.find(PracticeResults.class, id);
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public List<PracticeResults> findAllResults() {

        TypedQuery<PracticeResults> query = em.createQuery(
                "select s from models.CompetitionResults s ", PracticeResults.class
        );
        return query.getResultList();
    }
}
