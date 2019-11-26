package daos.interfaces;

import models.CompetitionResults;

import java.util.List;

public interface CompetitionResultsDAO extends GenericDAO<CompetitionResults, Long> {

    List<CompetitionResults> findAllResults();
}
