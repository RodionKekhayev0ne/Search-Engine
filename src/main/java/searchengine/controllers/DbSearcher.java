package searchengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.EnvironmentPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import searchengine.Application;
import searchengine.LemmaFinder;
import searchengine.config.SearchConfig;
import searchengine.model.*;
import searchengine.repos.IndexRepo;
import searchengine.repos.LemmaRepo;
import searchengine.repos.PageRepo;
import searchengine.repos.SitesRepo;


import java.io.IOException;
import java.sql.*;
import java.util.*;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Slf4j
public class DbSearcher {

    private String query;
    private int limitN;
    private String siteId;

    private Integer id;

    private PageRepo repo;
    private SitesRepo sitesRepo;
    private LemmaRepo lemmaRepo;
    private IndexRepo indexRepo;
    private LemmaFinder finder;
    private Environment environment;

    public DbSearcher() {
    }


    public DbSearcher(String query, int limit, Integer siteId,
                      PageRepo pageRepo, SitesRepo siteRepo,
                      LemmaRepo lemmaRepo, IndexRepo indexRepo,
                      Environment environment) throws SQLException {
        this.query = query;
        this.limitN = limit;
        this.id = siteId;
        repo = pageRepo;
        sitesRepo = siteRepo;
        this.lemmaRepo = lemmaRepo;
        this.indexRepo = indexRepo;
        this.environment = environment;
        try {
            finder = LemmaFinder.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SearchResult> search() throws SQLException, IOException {
        List<SearchResult> results = new ArrayList<>();
        HashMap<String, Double> resultMap = new HashMap<>();
        LemmaFinder finder = LemmaFinder.getInstance();
        try {
            if (id == null) {
                siteId = "";
                log.info("NO SITE PARAM FROM QUERY (ALL SITES)");
            } else {
                siteId = " AND site_id=" + id;
                log.info("SEARCHING FROM SITE WITH id= " + id);
            }
            Connection connection = DriverManager
                    .getConnection(environment.getProperty("searcher-properties.url"),
                            environment.getProperty("searcher-properties.username"),
                            environment.getProperty("searcher-properties.password"));

            Statement statement = connection.createStatement();
            String[] splitedQuery = finder.getLemmaSet(query).toArray(String[]::new);
            HashMap<Integer, Double> rankMap = new HashMap<>();
            for (int i = 0; i < splitedQuery.length; i++) {
                String sqlQuery = "SELECT * FROM sitesdata.lemma WHERE lemma='" + splitedQuery[i] + "'" + siteId + " ORDER BY frequency DESC";
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                List<Integer> lemmaId = new ArrayList<>();
                ResultSet lemma = statement.executeQuery(sqlQuery);
                while (lemma.next()) {
                    lemmaId.add(lemma.getInt("id"));
                }
                for (int id : lemmaId) {
                    String sqlQueryIndex = "SELECT * FROM sitesdata.index WHERE id=" + id + " ORDER BY `rank` DESC";
                    ResultSet index = statement.executeQuery(sqlQueryIndex);
                    while (index.next()) {
                        if (rankMap.containsKey(index.getInt("page_id"))) {
                            rankMap.put(index.getInt("page_id"),
                                    rankMap.get(index.getInt("page_id"))
                                            + index.getDouble("rank"));
                        } else {
                            rankMap.put(index.getInt("page_id"), index.getDouble("rank"));
                        }
                    }
                }
            }
            HashMap<String, Double> pageMap = new HashMap<>();
            for (Integer id : rankMap.keySet()) {
                String sqlQueryPages = "SELECT * FROM sitesdata.page WHERE id=" + id;
                ResultSet set = statement.executeQuery(sqlQueryPages);
                while (set.next()) {
                    pageMap.put(set.getString("path"), rankMap.get(id));
                }
            }
            resultMap = pageMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(LinkedHashMap::new, (m, c) -> m.put(c.getKey(), c.getValue()),
                            LinkedHashMap::putAll);
            statement.close();
            connection.close();
            for (String path : resultMap.keySet()) {
                for (Page page : repo.findByPath(path)) {
                    SearchResult searchResult = new SearchResult();
                    searchResult.setSiteName(sitesRepo.findById(page.getSiteId().getId()).get().getName());
                    searchResult.setUri(page.getPath());
                    searchResult.setTitle(page.getTitle());
                    searchResult.setSite("");
                    String snippet = createSnippet(page.getContent().trim(), query.toLowerCase()).toString();
                    searchResult.setSnippet(snippet);
                    searchResult.setRelevance(resultMap.get(path));
                    results.add(searchResult);
                }
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
        return results;
    }

    private StringBuilder createSnippet(String text, String word) {
        StringBuilder builder = new StringBuilder();
        String lastWord = "";
        int index = 0;
        for (String boldWord : Arrays.stream(word.toLowerCase().split("\s+")).toList()) {
            index = text.indexOf(boldWord);
            if (index == -1) {
                index++;
                builder.append("<b>" + text.substring(index, boldWord.length() + index) + "</b>" + " ");
                lastWord = boldWord;
            } else {
                builder.append("<b>" + text.substring(text.indexOf(boldWord), boldWord.length() + index + 1) + "</b>" + " ");
                lastWord = boldWord;
            }
        }
        builder.append(text.substring(lastWord.length() + index, lastWord.length() + index + 400));
        return builder;
    }
}