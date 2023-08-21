package searchengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import searchengine.model.SiteModel;

import java.sql.*;
import java.util.*;

public class DbSearcher {



    private String query;
    private int limitN;
    private String siteId;
    private String limit = " LIMIT ";
    private Integer id;

    public DbSearcher(){}
    public DbSearcher(String query, int limit,Integer siteId) throws SQLException {
        this.query = query;
        this.limitN = limit;
        this.id = siteId;
    }

    public HashMap<String,Double> search() throws SQLException {

        if (id == null){
            siteId = "";
        }else {siteId =  " AND site_id=" + id;}

        if (limitN == 0){
            limit = "";
        }else {
            limit = " LIMIT " + limitN;
        }


        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/sitesdata", "root", "Ognaviastralis06");

        Statement statement = connection.createStatement();

        String[] splitedQuery = query.split("\s+");


        HashMap<Integer,Double> rankMap = new HashMap<>();

        for (int i =0; i < splitedQuery.length;i++) {
            String sqlQuery = "SELECT * FROM sitesdata.lemma WHERE lemma='" + splitedQuery[i] + "'" + siteId + " ORDER BY frequency DESC" + limit;
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            List<Integer> lemmaId = new ArrayList<>();

            ResultSet lemma = statement.executeQuery(sqlQuery);

            while (lemma.next()) {
                lemmaId.add(lemma.getInt("id"));

            }

            for (int id : lemmaId){
                String sqlQueryIndex = "SELECT * FROM sitesdata.index WHERE id=" + id + " ORDER BY `rank` DESC" + limit;
                ResultSet index = statement.executeQuery(sqlQueryIndex);

                while (index.next()){
                    if (rankMap.containsKey(index.getInt("page_id"))){
                        rankMap.put(index.getInt("page_id"),
                                rankMap.get(index.getInt("page_id"))
                                        + index.getDouble("rank"));
                    }else {
                        rankMap.put(index.getInt("page_id"), index.getDouble("rank"));
                    }
                }

            }


        }

        HashMap<String,Double> pageMap = new HashMap<>();
        for(Integer id : rankMap.keySet()) {
            String sqlQueryPages = "SELECT * FROM sitesdata.page WHERE id=" + id + limit;
            ResultSet set = statement.executeQuery(sqlQueryPages);


            while (set.next()) {
                pageMap.put(set.getString("path"),rankMap.get(id));
            }
        }

        HashMap<String,Double>  resultMap = pageMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10)
                .collect(LinkedHashMap::new,
                        (m, c) -> m.put(c.getKey(), c.getValue()),
                        LinkedHashMap::putAll);

        statement.close();
        connection.close();
        return resultMap;
    }

}