package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.repos.SitesRepo;
import searchengine.model.SiteModel;

@Service
public class SiteService {

    private SitesRepo repository;

    @Autowired
    public SiteService(SitesRepo repository) {
        this.repository = repository;
    }
    public Iterable<SiteModel> getAllEntities(){
        return repository.findAll();
    }
}
