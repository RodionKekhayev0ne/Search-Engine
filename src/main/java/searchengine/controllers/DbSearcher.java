package searchengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import searchengine.LemmaFinder;
import searchengine.model.*;
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
    private String siteId;
    private static final String INDEX_SELECTOR = "page_id";
    private static final String PROPERTY_URL = "searcher-properties.url";
    private static final String PROPERTY_USERNAME = "searcher-properties.username";
    private static final String PROPERTY_PASS = "searcher-properties.password";
    private Integer id;

    private PageRepo repo;
    private SitesRepo sitesRepo;
    private Environment environment;

    private HashMap<Integer, Double> rankMap = new HashMap<>();
    private List<SearchResult> results = new ArrayList<>();

    public DbSearcher() {
    }


    public DbSearcher(String query, Integer siteId, PageRepo pageRepo, SitesRepo siteRepo, Environment environment) {
        this.query = query;
        this.id = siteId;
        repo = pageRepo;
        sitesRepo = siteRepo;

        this.environment = environment;
    }

    public List<SearchResult> search() throws IOException {

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
            Connection connection = DriverManager.getConnection(
                    Objects.requireNonNull(
                            environment.getProperty(PROPERTY_URL)),
                            environment.getProperty(PROPERTY_USERNAME),
                            environment.getProperty(PROPERTY_PASS));

            Statement statement = connection.createStatement();
            String[] splitedQuery = finder.getLemmaSet(query).toArray(String[]::new);

            for (int i = 0; i < splitedQuery.length; i++) {
                String sqlQuery = "SELECT * FROM sitesdata.lemma WHERE lemma='" + splitedQuery[i] + "'" + siteId + " ORDER BY frequency DESC";
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                List<Integer> lemmaId = new ArrayList<>();
                ResultSet lemma = statement.executeQuery(sqlQuery);
                while (lemma.next()) {
                    lemmaId.add(lemma.getInt("id"));
                }
                setLemmasId(lemmaId, statement);
            }
            HashMap<String, Double> pageMap = new HashMap<>();
            inputPageMap(pageMap, statement);

            resultMap = pageMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(LinkedHashMap::new, (m, c) -> m.put(c.getKey(), c.getValue()), LinkedHashMap::putAll);

            createSearchResult(resultMap);
            statement.close();
            connection.close();

        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
        return results;
    }

    private StringBuilder createSnippet(String text, String word) {
        StringBuilder builder = new StringBuilder();
        String lastWord = "";

        int index = 0;
        for (String boldWord : Arrays.stream(word.toLowerCase().split("\\s+")).toList()) {
            index = text.indexOf(findSimilarWord(boldWord, Arrays.stream(text.split("\\s+")).toList()));
            if (index == -1) {
                index++;
                builder.append("<b>" + text.substring(index, boldWord.length() + index) + "</b>" + " ");
                lastWord = boldWord;
            } else {
                builder.append("<b>" + text.substring(index, boldWord.length() + index) + "</b>" + " ");
                lastWord = boldWord;
            }
        }
        builder.append(text.substring(lastWord.length() + index, lastWord.length() + index + 400));
        return builder;
    }

    public static int levenshteinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1), dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static String findSimilarWord(String target, List<String> wordList) {
        int minDistance = Integer.MAX_VALUE;
        String similarWord = "";
        for (String word : wordList) {
            int distance = levenshteinDistance(target, word);
            if (distance < minDistance) {
                minDistance = distance;
                similarWord = word;
            }
        }
        return similarWord;
    }

    private void setLemmasId(List<Integer> lemmaList, Statement statement) throws SQLException {
        for (int lemmaId : lemmaList) {
            String sqlQueryIndex = "SELECT * FROM sitesdata.index WHERE id=" + lemmaId + " ORDER BY `rank` DESC";
            ResultSet index = statement.executeQuery(sqlQueryIndex);
            while (index.next()) {
                if (rankMap.containsKey(index.getInt(INDEX_SELECTOR))) {
                    rankMap.put(index.getInt(INDEX_SELECTOR), rankMap.get(index.getInt(INDEX_SELECTOR)) + index.getDouble("rank"));
                } else {
                    rankMap.put(index.getInt(INDEX_SELECTOR), index.getDouble("rank"));
                }
            }
        }
    }

    private void createSearchResult(HashMap<String, Double> resultMap) {
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
    }

    private void inputPageMap(HashMap<String, Double> pageMap, Statement statement) throws SQLException {
        for (Integer rankId : rankMap.keySet()) {
            String sqlQueryPages = "SELECT * FROM sitesdata.page WHERE id=" + rankId;
            ResultSet set = statement.executeQuery(sqlQueryPages);
            while (set.next()) {
                pageMap.put(set.getString("path"), rankMap.get(rankId));
            }
        }
    }
}