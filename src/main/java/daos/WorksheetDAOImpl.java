package daos;

import daos.interfaces.WorksheetDAO;
import helper.EntityManagerFactoryHelper;
import models.Worksheet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public  class WorksheetDAOImpl implements WorksheetDAO {

    private EntityManagerFactory emf ;
    @PersistenceContext
    private EntityManager em;

    public WorksheetDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public void create(Worksheet worksheet) {

        em.getTransaction().begin();
        em.persist(worksheet);
        em.getTransaction().commit();
    }

    @Override
    public void update(Worksheet worksheet) {

        em.getTransaction().begin();
        em.merge(worksheet);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(Worksheet worksheet) {

        em.getTransaction().begin();
        em.remove(em.contains(worksheet) ? worksheet : em.merge(worksheet));
        em.getTransaction().commit();
    }

    @Override
    public Worksheet findByID(Long id) {

        return em.find(Worksheet.class, id);
    }

    @Override
    public void close() {
        em.clear();
    }

    @Override
    public List<Worksheet> findAllWorksheet() {

        TypedQuery<Worksheet> query = em.createQuery(
                "select s from models.Worksheet s ", Worksheet.class
        );
        return query.getResultList();
    }

    @Override
    public List<Worksheet> findAllWorksheetByCreator(String name) {

        TypedQuery<Worksheet> query = em.createQuery(
                "select s from models.Worksheet s where s.creator =: NAME", Worksheet.class
        ).setParameter("NAME",name);
        return query.getResultList();
    }

    @Override
    public List<Worksheet> findByCategory(String category) {

        TypedQuery<Worksheet> query = em.createQuery(
                "select s from models.Worksheet s where s.categories =: CAT", Worksheet.class
        ).setParameter("CAT", category);
        return query.getResultList();
    }


}
