package searchengine.dto.statistics;

import lombok.Data;
import searchengine.model.Page;
import searchengine.model.SearchResult;

import java.util.List;

@Data
public class SearchResponse {

    private boolean result;
    private int count;
    private List<SearchResult> data;

}
