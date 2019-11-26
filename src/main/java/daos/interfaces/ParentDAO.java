package daos.interfaces;

import models.Parent;

import java.util.List;

public interface ParentDAO extends GenericDAO<Parent, Long> {

    List<Parent> findAllParent();
    Parent findByEmail(String email);
}
