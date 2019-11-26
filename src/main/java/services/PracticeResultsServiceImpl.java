package services;

import daos.PracticeResultsDAOImpl;
import daos.interfaces.PracticeResultsDAO;
import models.PracticeResults;
import services.interfaces.PracticeResultsService;

import java.util.List;

public class PracticeResultsServiceImpl implements PracticeResultsService {

    PracticeResultsDAO practiceResultsDAO = new PracticeResultsDAOImpl();

    @Override
    public List<PracticeResults> findAllResults() {
        return practiceResultsDAO.findAllResults();
    }

    @Override
    public void save(PracticeResults practiceResults) {
        practiceResultsDAO.create(practiceResults);
    }

    @Override
    public void remove(PracticeResults practiceResults) {
        practiceResultsDAO.delete(practiceResults);
    }

    @Override
    public void modify(PracticeResults practiceResults) {
        practiceResultsDAO.update(practiceResults);
    }

    @Override
    public PracticeResults findByID(Long aLong) {
        return practiceResultsDAO.findByID(aLong);
    }

    @Override
    public void close() {
        practiceResultsDAO.close();
    }
}
