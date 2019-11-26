package services;

import daos.CompetitionResultsDAOImpl;
import daos.interfaces.CompetitionResultsDAO;
import models.CompetitionResults;
import services.interfaces.CompetitionResultsService;

import java.util.List;

public class CompetitionResultsServiceImpl implements CompetitionResultsService {

    CompetitionResultsDAO competitionResultsDAO = new CompetitionResultsDAOImpl();

    @Override
    public List<CompetitionResults> findAllResults() {
        return competitionResultsDAO.findAllResults();
    }

    @Override
    public void save(CompetitionResults competitionResults) {
        competitionResultsDAO.create(competitionResults);
    }

    @Override
    public void remove(CompetitionResults competitionResults) {
        competitionResultsDAO.delete(competitionResults);
    }

    @Override
    public void modify(CompetitionResults competitionResults) {
        competitionResultsDAO.update(competitionResults);
    }

    @Override
    public CompetitionResults findByID(Long aLong) {
        return competitionResultsDAO.findByID(aLong);
    }

    @Override
    public void close() {

        competitionResultsDAO.close();
    }
}
