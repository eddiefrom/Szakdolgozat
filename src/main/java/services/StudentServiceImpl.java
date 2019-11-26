package services;

import daos.interfaces.StudentDAO;
import daos.StudentDAOImpl;
import models.Student;
import services.interfaces.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    StudentDAO studentDAO = new StudentDAOImpl();

    @Override
    public void save(Student student) {
        studentDAO.create(student);
    }

    @Override
    public void remove(Student student) {
        studentDAO.delete(student);
    }

    @Override
    public void modify(Student student) {
        studentDAO.update(student);
    }

    @Override
    public Student findByID(Long id) {
        return studentDAO.findByID(id);
    }

    @Override
    public void close() {
        studentDAO.close();
    }

    @Override
    public List<Student> findAllStudent() {
        return studentDAO.findAllStudent();
    }

    @Override
    public Student findByEmail(String email) {

        return studentDAO.findByEmail(email);
    }

    @Override
    public List<Student> findByClass(int classNumber) {


        return studentDAO.findByClass(classNumber);
    }
}
