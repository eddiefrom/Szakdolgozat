package services;

import daos.WorksheetDAOImpl;
import daos.interfaces.WorksheetDAO;
import models.Worksheet;
import services.interfaces.WorksheetService;

import java.util.List;

public class WorksheetServiceImpl implements WorksheetService {

    WorksheetDAO worksheetDAO = new WorksheetDAOImpl();

    @Override
    public void save(Worksheet worksheet) {
        worksheetDAO.create(worksheet);
    }

    @Override
    public void remove(Worksheet worksheet) {
        worksheetDAO.delete(worksheet);
    }

    @Override
    public void modify(Worksheet worksheet) {
        worksheetDAO.update(worksheet);
    }

    @Override
    public Worksheet findByID(Long id) {
        return worksheetDAO.findByID(id);
    }

    @Override
    public void close() {
        worksheetDAO.close();
    }

    @Override
    public List<Worksheet> findAllWorksheet() {
        return worksheetDAO.findAllWorksheet();
    }

    @Override
    public List<Worksheet> findAllWorksheetByCreator(String name) { return worksheetDAO.findAllWorksheetByCreator(name);}

    @Override
    public List<Worksheet> findByCategory(String category) {

        return worksheetDAO.findByCategory(category);
    }


}
