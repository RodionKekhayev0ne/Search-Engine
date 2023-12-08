package searchengine.repos;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Index;
import searchengine.model.Lemma;

import java.util.List;

@Repository
public interface IndexRepo extends CrudRepository<Index, Integer> {
    List<Index> findByIdOrderByRankDesc(Integer id);
}
