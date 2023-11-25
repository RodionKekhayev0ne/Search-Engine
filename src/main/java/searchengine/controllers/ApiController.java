package searchengine.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import searchengine.Application;
import searchengine.dto.statistics.*;
import searchengine.lemma.DetailedStatisticsItem;
import searchengine.model.Page;
import searchengine.model.SearchResult;
import searchengine.model.SiteModel;
import searchengine.repos.IndexRepo;
import searchengine.repos.LemmaRepo;
import searchengine.repos.PageRepo;
import searchengine.repos.SitesRepo;
import searchengine.services.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@ConfigurationProperties(prefix = "indexing-settings")
@RequestMapping("/api")
public class ApiController {
    //SELECT * FROM table_name WHERE column_name LIKE '%search_string%';

    private Logger logger = Application.getLogger();
    private final StatisticsService statisticsService;
    private SitesRepo siteRepo;
    private PageRepo pageRepo;
    private LemmaRepo lemmaRepo;
    private IndexRepo indexrepo;
    private String urls;
    private ForkJoinPool forkJoinPool;


    private Environment environment;


    public void setUrls(String urls) {
        this.urls = urls;
    }

    @Autowired
    public ApiController(SitesRepo service, PageRepo pageService, LemmaRepo lemmaService, IndexRepo indexService, StatisticsService statisticsService,Environment environment) {
        this.siteRepo = service;
        this.pageRepo = pageService;
        this.lemmaRepo = lemmaService;
        this.indexrepo = indexService;
        this.statisticsService = statisticsService;
        this.environment = environment;
    }







    @GetMapping("/stopIndexing")
    public ResponseEntity<StatisticsResponse> stopIndex() {
        forkJoinPool.shutdown();
        logger.info("INDEXING STOPPED!!!");

        StatisticsResponse response = new StatisticsResponse();
        response.setResult(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<IndexResponse> indexSite() {


        boolean result = true;

        if (result) {
            logger.info("INDEXING STARTED!!!");
            List<String> siteUrl = new ArrayList<>();
            String[] split = urls.split(",");
            for (int i = 0; i < split.length; i++) {
                siteUrl.add(split[i]);

            }
            RecursiveAction indexation = new SiteIndexer(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo);
            forkJoinPool = new ForkJoinPool();
            forkJoinPool.invoke(indexation);
            logger.info("INDEXING ENDED!!!");
            result = false;
            IndexResponse response = new IndexResponse();
            response.setResult(true);
            response.setError("");
            return ResponseEntity.ok(response);
        }else {
            IndexResponse response = new IndexResponse();
            response.setResult(false);
            response.setError("Индексация уже запущена");
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("/indexPage")
    public ResponseEntity<IndexResponse> updateSite(@RequestParam("url") String ulr) {

        for (SiteModel siteModel: siteRepo.findAll()) {
            if (siteModel.getUrl().equals(ulr)){
                logger.info("URL ALREADY EXIST");
                IndexResponse response = new IndexResponse();
                response.setResult(false);
                response.setError("Страница уже проиндексирована");
                return ResponseEntity.badRequest().body(response);
            }
        }
        logger.info("INDEXING UPDATING!!");

        List<String> siteUrl = new ArrayList<>();
        siteUrl.add(ulr);

        RecursiveAction indexation = new SiteIndexer(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo);

        forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(indexation);
        logger.info("INDEXING UPDATED!!!");
        System.out.println();

        IndexResponse response = new IndexResponse();
        response.setResult(true);
        response.setError("");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics(){
        StatisticsResponse response = new StatisticsResponse();
        int siteCount = 0;
        int pageCount = 0;
        int lemmaCount = 0;
       for (SiteModel model : siteRepo.findAll()){
           List<SiteModel> list = new ArrayList<>();
           list.add(model);
           siteCount +=1;
       }
        for (SiteModel model : siteRepo.findAll()){
            pageCount+= model.getPageCount();
        }

        for (SiteModel model : siteRepo.findAll()){
            lemmaCount+= model.getLemmaCount();
        }
        TotalStatistics totalStatistics = new TotalStatistics();
        totalStatistics.setIndexing(true);
        totalStatistics.setPages(pageCount);
        totalStatistics.setLemmas(lemmaCount);
        totalStatistics.setSites(siteCount);
        List<DetailedStatisticsItem> list = new ArrayList<>();
        for (SiteModel site : siteRepo.findAll()) {
            DetailedStatisticsItem statisticsItem = new DetailedStatisticsItem();
            statisticsItem.setError(site.getStatusError());
            statisticsItem.setStatus(site.getStatus().toString());
            statisticsItem.setName(site.getName());
            statisticsItem.setUrl(site.getUrl());
            statisticsItem.setStatusTime(site.getStatusTime().getTime());
            statisticsItem.setPages(site.getPageCount());
            statisticsItem.setLemmas(site.getLemmaCount());
            list.add(statisticsItem);
        }
        StatisticsData statisticsData = new StatisticsData();
        statisticsData.setTotal(totalStatistics);
        statisticsData.setDetailed(list);
        response.setResult(true);
        response.setStatistics(statisticsData);
        return ResponseEntity.ok(response);
    }



    @RequestMapping("/search{site}")
    public ResponseEntity<SearchResponse> search(@RequestParam String query, @RequestParam int offset, @RequestParam int limit, String site, Model model) throws SQLException {

        logger.info("Search query - " + query);


        DbSearcher searcher = new DbSearcher();

        if (site == null){
             searcher = new DbSearcher(query,limit,null,pageRepo,siteRepo,lemmaRepo, indexrepo,environment);
        }else {


            for (SiteModel siteModel : siteRepo.findAll()) {
                if (siteModel.getUrl().equals(site)) {
                    searcher = new DbSearcher(query, limit,siteModel.getId(),pageRepo,siteRepo,lemmaRepo, indexrepo,environment);
                }
            }
        }
        List<SearchResult> values = searcher.search();
        logger.info("SEARCHING COMPLETED");
        SearchResponse response = new SearchResponse();
        response.setCount(values.size());
        response.setData(values);
        response.setResult(true);
        response.setCount(values.size());
        return ResponseEntity.ok(response);
    }
}
//Интернет-магазин PlayBack.ru",
//    "Вышний Волочёк портал города",
//    "Центральная городская молодежная библиотека им. М. А. Светлова"
//]