package daos;

import daos.interfaces.CompetitionResultsDAO;
import helper.EntityManagerFactoryHelper;
import models.CompetitionResults;
import models.Exercise;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class CompetitionResultsDAOImpl implements CompetitionResultsDAO {

    private EntityManagerFactory emf ;
    private EntityManager em;

    public CompetitionResultsDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public List<CompetitionResults> findAllResults() {
        TypedQuery<CompetitionResults> query = em.createQuery(
                "select s from models.CompetitionResults s ", CompetitionResults.class
        );
        return query.getResultList();
    }

    @Override
    public void create(CompetitionResults competitionResults) {

        em.getTransaction().begin();
        em.persist(competitionResults);
        em.getTransaction().commit();
    }

    @Override
    public void update(CompetitionResults competitionResults) {

        em.getTransaction().begin();
        em.merge(competitionResults);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(CompetitionResults competitionResults) {

        em.getTransaction().begin();
        em.remove(em.contains(competitionResults) ? competitionResults : em.merge(competitionResults));
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
}
