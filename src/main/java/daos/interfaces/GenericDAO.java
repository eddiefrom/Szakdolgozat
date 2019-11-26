package daos.interfaces;

import java.io.Serializable;

public interface GenericDAO<T, ID extends Serializable> {

    void create(T t);
    void update(T t);
    void delete(T t);
    T findByID(ID id);
    void close();
}
