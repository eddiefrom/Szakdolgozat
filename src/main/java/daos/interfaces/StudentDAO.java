package daos.interfaces;

import models.Student;

import java.util.List;

public interface StudentDAO extends GenericDAO<Student, Long> {

    List<Student> findAllStudent();
    Student findByEmail(String email);
    List<Student> findByClass(int classNumber);
}
