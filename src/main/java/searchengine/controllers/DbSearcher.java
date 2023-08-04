package searchengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbSearcher {



    private String query;
    private String limit = " limit ";
    private Connection connection;

    public DbSearcher(String query, int limit) throws SQLException {
        this.query = query;
        this.limit += limit;
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/sitesdata", "root", "Ognaviastralis06");
    }

    public List<Object> search() throws SQLException {

        String[] splitedQuery = query.split("\s+");

        List<HashMap<Integer,Double>> resultIds = new ArrayList<>();

        for (int i =0; i <= splitedQuery.length;i++){
                String sqlQuery = "SELECT * FROM sitesdata.lemma WHERE lemma='" + splitedQuery[i] + "' " + limit;
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                List<Integer> lemmaId = new ArrayList<>();
                Statement statement = connection.createStatement();
                ResultSet lemma = statement.executeQuery(sqlQuery);

                while (lemma.next()) {
                    lemmaId.add(lemma.getInt("id"));
                }
                lemma.close();

                ResultSet index = null;
                ResultSet page = null;
                HashMap<Integer,Double> idNrank = new HashMap<>();
                for (int id = 0; id < lemmaId.size(); id++) {
                    String sqlQuery2 = "SELECT * FROM sitesdata.index WHERE lemma_id=" + lemmaId.get(id) + " ORDER BY sitesdata.index.rank DESC " + limit;
                    index = statement.executeQuery(sqlQuery2);
                    while (index.next()) {
                       idNrank.put(index.getInt("page_id"),index.getDouble("rank"));
                       resultIds.add(idNrank);
                    }
                    index.close();
                }

                for (HashMap<Integer,Double> map : resultIds){

                    List<HashMap<Integer,Double>> comparingMap = new ArrayList<>();
                    for (Integer key : map.keySet()){


                    }
                }

//                List<Integer> pages = new ArrayList<>();
//                for (int id = 0; id < pageId.size(); id++) {
//                    String sqlQuery3 = "SELECT * FROM sitesdata.page WHERE id=" + id + limit;
//                    page = statement.executeQuery(sqlQuery3);
//                    while (page.next()) {
//                        pages.add(page.getInt("id"));
//                    }
//                    resultIds.add(pages);
//                    page.close();
//                }
                statement.close();
                connection.close();
            }
        return new ArrayList<>();
    }

}
