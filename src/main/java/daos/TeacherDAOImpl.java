package daos;

import daos.interfaces.TeacherDAO;
import helper.EntityManagerFactoryHelper;
import models.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {
    private EntityManagerFactory emf ;
    private EntityManager em;

    public TeacherDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public void create(Teacher teacher) {

        em.getTransaction().begin();
        em.persist(teacher);
        em.getTransaction().commit();
    }

    @Override
    public void update(Teacher teacher) {

        em.getTransaction().begin();
        em.merge(teacher);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(Teacher teacher) {

        em.getTransaction().begin();
        em.remove(em.contains(teacher) ? teacher : em.merge(teacher));
        em.getTransaction().commit();
    }

    @Override
    public Teacher findByID(Long id) {

        return em.find(Teacher.class, id);
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public List<Teacher> findAllTeacher() {

        TypedQuery<Teacher> query = em.createQuery(
                "select s from models.Teacher s ", Teacher.class
        );
        return query.getResultList();
    }

    @Override
    public Teacher findByEmail(String email) {

        TypedQuery<Teacher> query = em.createQuery(
                "select s from models.Teacher s where s.email =: EMAIL", Teacher.class
        ).setParameter("EMAIL",email);

        return  query.getSingleResult();
    }
}
