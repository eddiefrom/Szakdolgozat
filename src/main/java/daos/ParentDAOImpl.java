package daos;

import daos.interfaces.ParentDAO;
import helper.EntityManagerFactoryHelper;
import models.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ParentDAOImpl implements ParentDAO {

    private EntityManagerFactory emf ;
    private EntityManager em;

    public ParentDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public void create(Parent parent) {

        em.getTransaction().begin();
        em.persist(parent);
        em.getTransaction().commit();
    }

    @Override
    public void update(Parent parent) {

        em.getTransaction().begin();
        em.merge(parent);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(Parent parent) {

        em.getTransaction().begin();
        em.remove(em.contains(parent) ? parent : em.merge(parent));
        em.getTransaction().commit();
    }

    @Override
    public Parent findByID(Long id) {

        return em.find(Parent.class, id);
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public List<Parent> findAllParent() {

        TypedQuery<Parent> query = em.createQuery(
                "select s from models.Parent s ", Parent.class
        );
        return query.getResultList();
    }

    @Override
    public Parent findByEmail(String email) {

        TypedQuery<Parent> query = em.createQuery(
                "select s from models.Parent s where s.email =: EMAIL", Parent.class
        ).setParameter("EMAIL",email);

        return  query.getSingleResult();
    }
}
