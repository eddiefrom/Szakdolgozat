package services.interfaces;

import models.Student;

import java.util.List;

public interface StudentService extends GenericService<Student, Long> {

    List<Student> findAllStudent();
    Student findByEmail(String email);
    List<Student> findByClass(int classNumber);
}
