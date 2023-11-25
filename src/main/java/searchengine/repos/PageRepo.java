package searchengine.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Index;
import searchengine.model.Page;

import java.util.List;

@Repository
public interface PageRepo extends CrudRepository<Page, Integer> {
    List<Page> findByPath(String path);
}
