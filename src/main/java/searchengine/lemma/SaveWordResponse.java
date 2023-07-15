package searchengine.lemma;

import lombok.Data;

import java.util.List;

@Data
public class SaveWordResponse {
    private String word;
    private List<String> morphInfo;
}
