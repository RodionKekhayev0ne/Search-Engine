package searchengine.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Lemma;

@Repository
public interface LemmaRepo extends CrudRepository<Lemma, Integer> {
}
