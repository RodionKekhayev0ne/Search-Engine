package searchengine.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.TypeDefinition;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.query.NamedHqlQueryDefinition;
import org.hibernate.boot.query.NamedNativeQueryDefinition;
import org.hibernate.boot.query.NamedProcedureCallDefinition;
import org.hibernate.boot.query.NamedResultSetMappingDescriptor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.annotations.NamedEntityGraphDefinition;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.mapping.FetchProfile;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.SiteModel;
import searchengine.repos.IndexRepo;
import searchengine.repos.LemmaRepo;
import searchengine.repos.PageRepo;
import searchengine.repos.SitesRepo;
import searchengine.services.*;

import javax.management.Query;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

@RestController
@ConfigurationProperties(prefix = "indexing-settings")
@RequestMapping("/api")
public class ApiController {
    //SELECT * FROM table_name WHERE column_name LIKE '%search_string%';
    private final StatisticsService statisticsService;
    private SitesRepo siteRepo;
    private PageRepo pageRepo;
    private LemmaRepo lemmaRepo;
    private IndexRepo indexrepo;
    private String urls;
    private ForkJoinPool forkJoinPool;

    public void setUrls(String urls) {
        this.urls = urls;
    }

    @Autowired
    public ApiController(SitesRepo service, PageRepo pageService, LemmaRepo lemmaService, IndexRepo indexService, StatisticsService statisticsService) {
        this.siteRepo = service;
        this.pageRepo = pageService;
        this.lemmaRepo = lemmaService;
        this.indexrepo = indexService;
        this.statisticsService = statisticsService;
    }


    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }


    @GetMapping("/stopIndexing")
    public ResponseEntity<StatisticsResponse> stopIndex() {
        forkJoinPool.shutdown();
        System.out.println("INDEXING STOPPED!!!");
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<StatisticsResponse> indexSite() {

        System.out.println("INDEXING STARTED!!!");
        List<String> siteUrl = new ArrayList<>();

        String[] split = urls.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
            siteUrl.add(split[i]);

        }


        RecursiveAction indexation = new SiteIndexerAct(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo);

        forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(indexation);


        System.out.println("INDEXING ENDED!!!");
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    //звездный,
    @GetMapping("/search{site}")
    public ResponseEntity search(@RequestParam String query,@RequestParam int offset,@RequestParam int limit, String site) throws SQLException {

      DbSearcher searcher = new DbSearcher(query,limit);


           return ResponseEntity.status(HttpStatus.OK).body(searcher.search());
    }


}

//Интернет-магазин PlayBack.ru",
//    "Вышний Волочёк портал города",
//    "Центральная городская молодежная библиотека им. М. А. Светлова"
//]