package daos.interfaces;

import models.Worksheet;

import java.util.List;

public interface WorksheetDAO extends GenericDAO<Worksheet, Long>{

    List<Worksheet> findAllWorksheet();
    List<Worksheet> findAllWorksheetByCreator(String name);
    List<Worksheet> findByCategory(String category);
}
