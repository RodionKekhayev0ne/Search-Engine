package searchengine.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Index;

@Repository
public interface IndexRepo extends CrudRepository<Index, Integer> {
}
