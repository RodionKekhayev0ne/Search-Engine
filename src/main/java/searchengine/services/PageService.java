package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.repos.PageRepo;
import searchengine.model.Page;

@Service
public class PageService {
    private PageRepo repository;

    @Autowired
    public PageService(PageRepo repository) {
        this.repository = repository;
    }

    public void createEntity(Page entity) {
        repository.save(entity);
    }

    public Iterable<Page> getAllEntities(){
        return repository.findAll();
    }

}
