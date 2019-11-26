package daos.interfaces;

import models.Teacher;
import java.util.List;

public interface TeacherDAO extends GenericDAO<Teacher, Long> {

    List<Teacher> findAllTeacher();
    Teacher findByEmail(String email);
}
