package helper;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;

public class EntityManagerFactoryHelper implements AutoCloseable {

    private static final EntityManagerFactoryHelper ENTITY_MANAGER_FACTORY_HELPER = new EntityManagerFactoryHelper();

    private static String PERSISTENCE_UNIT_NAME = "PersistenceUnit";

    private EntityManagerFactory emf;


    public static EntityManagerFactoryHelper getInstance() {
        return ENTITY_MANAGER_FACTORY_HELPER;
    }


    public EntityManagerFactory getEmf() {

        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (ServiceException e) {
                e.getMessage();
            }
        }
        return emf;
    }

    public void close() {
        if(emf != null) {
            emf.close();
        }
    }
}

