package services.interfaces;

import models.CompetitionResults;

import java.util.List;

public interface ResultsService extends GenericService<CompetitionResults, Long>{

    List<CompetitionResults> findAllResults();
}
