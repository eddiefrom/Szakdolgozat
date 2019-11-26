package services.interfaces;

import java.io.Serializable;

public interface GenericService<T, ID extends Serializable> {

    void save(T t);
    void remove(T t);
    void modify(T t);
    T findByID(ID id);
    void close();
}
