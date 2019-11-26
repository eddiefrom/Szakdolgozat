package services.interfaces;

import models.PracticeResults;

import java.util.List;

public interface PracticeResultsService extends GenericService<PracticeResults, Long>{

    List<PracticeResults> findAllResults();
}
