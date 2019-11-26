package daos;

import daos.interfaces.ResultsDAO;
import helper.EntityManagerFactoryHelper;
import models.CompetitionResults;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ResultsDAOImpl implements ResultsDAO {

    private EntityManagerFactory emf ;
    private EntityManager em;

    public ResultsDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public void create(CompetitionResults results) {

        em.getTransaction().begin();
        em.persist(results);
        em.getTransaction().commit();
    }

    @Override
    public void update(CompetitionResults results) {

        em.getTransaction().begin();
        em.merge(results);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(CompetitionResults results) {

        em.getTransaction().begin();
        em.remove(em.contains(results) ? results : em.merge(results));
        em.getTransaction().commit();
    }

    @Override
    public CompetitionResults findByID(Long id) {

        return em.find(CompetitionResults.class, id);
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public List<CompetitionResults> findAllResults() {

        TypedQuery<CompetitionResults> query = em.createQuery(
                "select s from models.CompetitionResults s ", CompetitionResults.class
        );
        return query.getResultList();
    }
}
