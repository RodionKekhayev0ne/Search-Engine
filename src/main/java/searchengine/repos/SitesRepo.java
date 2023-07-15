package searchengine.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.SiteModel;

@Repository
public interface SitesRepo extends CrudRepository<SiteModel, Integer> {

}
