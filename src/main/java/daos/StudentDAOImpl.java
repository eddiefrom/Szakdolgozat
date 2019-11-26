package daos;

import daos.interfaces.StudentDAO;
import helper.EntityManagerFactoryHelper;
import models.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private EntityManagerFactory emf ;
    private EntityManager em;

    public StudentDAOImpl() {

        emf = EntityManagerFactoryHelper.getInstance().getEmf();
        em = emf.createEntityManager();
    }

    @Override
    public void create(Student student) {

        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
    }

    @Override
    public void update(Student student) {

        em.getTransaction().begin();
        em.merge(student);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void delete(Student student) {

        em.getTransaction().begin();
        em.remove(em.contains(student) ? student : em.merge(student));
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public Student findByID(Long id) {

        return em.find(Student.class, id);
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public List<Student> findAllStudent() {

        TypedQuery<Student> query = em.createQuery(
                "select s from models.Student s ", Student.class
        );
        return query.getResultList();
    }

    @Override
    public Student findByEmail(String email) {

        TypedQuery<Student> query = em.createQuery(
                "select s from models.Student s where s.email =: EMAIL", Student.class
        ).setParameter("EMAIL",email);

        return  query.getSingleResult();
    }

    @Override
    public List<Student> findByClass(int classNumber) {

        TypedQuery<Student> query = em.createQuery(
                "select s from models.Student s where s.classNumber =: CLASS", Student.class
        ).setParameter("CLASS",classNumber);

        return  query.getResultList();
    }


}
