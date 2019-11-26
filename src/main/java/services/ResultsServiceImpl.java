package services;

import daos.ResultsDAOImpl;
import daos.interfaces.ResultsDAO;
import models.CompetitionResults;
import services.interfaces.ResultsService;

import java.util.List;

public class ResultsServiceImpl implements ResultsService {

    ResultsDAO resultsDAO = new ResultsDAOImpl();

    @Override
    public List<CompetitionResults> findAllResults() {
        return resultsDAO.findAllResults();
    }

    @Override
    public void save(CompetitionResults results) {
        resultsDAO.create(results);
    }

    @Override
    public void remove(CompetitionResults results) {
        resultsDAO.delete(results);
    }

    @Override
    public void modify(CompetitionResults results) {
        resultsDAO.update(results);
    }

    @Override
    public CompetitionResults findByID(Long aLong) {
        return resultsDAO.findByID(aLong);
    }

    @Override
    public void close() {
        resultsDAO.close();
    }
}
