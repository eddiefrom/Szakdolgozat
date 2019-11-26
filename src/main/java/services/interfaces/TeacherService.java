package services.interfaces;

import models.Teacher;

import java.util.List;

public interface TeacherService extends GenericService<Teacher, Long> {

    List<Teacher> findAllTeacher();
    Teacher findByEmail(String email);
}
