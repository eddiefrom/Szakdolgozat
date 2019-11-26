package services.interfaces;

import models.Worksheet;

import java.util.List;

public interface WorksheetService extends GenericService<Worksheet, Long> {

    List<Worksheet> findAllWorksheet();
    List<Worksheet> findAllWorksheetByCreator(String name);
    List<Worksheet> findByCategory(String category);
}
