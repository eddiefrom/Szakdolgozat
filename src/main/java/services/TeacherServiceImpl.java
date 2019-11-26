package services;

import daos.TeacherDAOImpl;
import daos.interfaces.TeacherDAO;
import models.Teacher;
import services.interfaces.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    TeacherDAO teacherDAO = new TeacherDAOImpl();

    @Override
    public void save(Teacher teacher) {
        teacherDAO.create(teacher);
    }

    @Override
    public void remove(Teacher teacher) {
        teacherDAO.delete(teacher);
    }

    @Override
    public void modify(Teacher teacher) {
        teacherDAO.update(teacher);
    }

    @Override
    public Teacher findByID(Long id) {
        return teacherDAO.findByID(id);
    }

    @Override
    public void close() {
        teacherDAO.close();
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return teacherDAO.findAllTeacher();
    }

    @Override
    public Teacher findByEmail(String email) {

        return teacherDAO.findByEmail(email);
    }
}
