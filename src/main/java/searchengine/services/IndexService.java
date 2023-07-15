package searchengine.services;


import org.springframework.stereotype.Service;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.repos.IndexRepo;

@Service
public class IndexService {

    private IndexRepo repository;


    public IndexService(IndexRepo repository) {
        this.repository = repository;
    }

    public void createEntity(Index entity) {
        repository.save(entity);
    }

    public Iterable<Index> getAllEntities(){
        return repository.findAll();
    }
}
