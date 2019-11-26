package services.interfaces;

import models.Parent;

import java.util.List;

public interface ParentService extends GenericService<Parent, Long> {

    List<Parent> findAllParent();
    Parent findByEmail(String email);

}
