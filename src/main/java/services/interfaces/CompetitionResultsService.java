package services.interfaces;

import models.CompetitionResults;

import java.util.List;

public interface CompetitionResultsService extends GenericService<CompetitionResults, Long> {

    List<CompetitionResults> findAllResults();
}
