package searchengine.services;



import org.springframework.stereotype.Service;
import searchengine.lemma.SaveWordResponse;
import searchengine.lemma.SearchWordRequest;
import searchengine.lemma.WordsListResponse;

import java.util.List;

@Service
public interface MorphologyService {
    List<String> morphologyForms(String word);

    SaveWordResponse saveWord(String word);

    WordsListResponse searchWords(SearchWordRequest searchWordRequest);
}
