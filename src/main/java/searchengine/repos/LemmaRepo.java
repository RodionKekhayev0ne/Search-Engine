package searchengine.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Lemma;

import java.util.List;

@Repository
public interface LemmaRepo extends CrudRepository<Lemma, Integer> {
      List<Lemma> findByLemmaOrderByFrequencyDesc(String lemma);
}
