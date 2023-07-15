package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.repos.LemmaRepo;
import searchengine.repos.PageRepo;

@Service
public class LemmaService {

    private LemmaRepo repository;

    @Autowired
    public LemmaService(LemmaRepo repository) {
        this.repository = repository;
    }

    public void createEntity(Lemma entity) {
        repository.save(entity);
    }

    public Iterable<Lemma> getAllEntities(){
        return repository.findAll();
    }

}
