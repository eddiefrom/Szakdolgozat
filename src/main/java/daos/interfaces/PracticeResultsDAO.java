package daos.interfaces;

import models.PracticeResults;

import java.util.List;

public interface PracticeResultsDAO extends GenericDAO<PracticeResults, Long> {

    List<PracticeResults> findAllResults();
}
