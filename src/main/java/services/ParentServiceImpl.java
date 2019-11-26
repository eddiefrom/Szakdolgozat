package services;

import daos.ParentDAOImpl;
import daos.interfaces.ParentDAO;
import models.Parent;
import services.interfaces.ParentService;

import java.util.List;

public class ParentServiceImpl implements ParentService {

    ParentDAO parentDAO = new ParentDAOImpl();

    @Override
    public void save(Parent parent) {
        parentDAO.create(parent);
    }

    @Override
    public void remove(Parent parent) {
        parentDAO.delete(parent);
    }

    @Override
    public void modify(Parent parent) {
        parentDAO.update(parent);
    }

    @Override
    public Parent findByID(Long id) {
        return parentDAO.findByID(id);
    }

    @Override
    public void close() {
        parentDAO.close();
    }

    @Override
    public List<Parent> findAllParent() {
        return parentDAO.findAllParent();
    }

    @Override
    public Parent findByEmail(String email) {

        return parentDAO.findByEmail(email);
    }
}
