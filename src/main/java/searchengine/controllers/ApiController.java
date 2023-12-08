package searchengine.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.*;
import searchengine.lemma.DetailedStatisticsItem;
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


@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    private final StatisticsService statisticsService;
    private final SitesRepo siteRepo;
    private final PageRepo pageRepo;
    private final LemmaRepo lemmaRepo;
    private final IndexRepo indexrepo;
    private final Environment environment;
     private ForkJoinPool forkJoinPool = new ForkJoinPool();


    @GetMapping("/stopIndexing")
    public ResponseEntity<StatisticsResponse> stopIndex() {



        forkJoinPool.shutdown();
        log.info("INDEXING STOPPED!!!");

        StatisticsResponse response = new StatisticsResponse();
        response.setResult(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<IndexResponse> indexSite() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        boolean result = true;

        if (result) {
            log.info("INDEXING STARTED!!!");
            List<String> siteUrl = new ArrayList<>();
            String[] split = environment.getProperty("indexing-settings.urls").split(",");
            for (int i = 0; i < split.length; i++) {
                siteUrl.add(split[i]);

            }

            RecursiveAction indexation = new SiteIndexer(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo,0,0);
            forkJoinPool = new ForkJoinPool();
            forkJoinPool.invoke(indexation);
            log.info("INDEXING ENDED!!!");
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
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (SiteModel siteModel: siteRepo.findAll()) {
            if (siteModel.getUrl().equals(ulr)){
                log.info("URL ALREADY EXIST");
                IndexResponse response = new IndexResponse();
                response.setResult(false);
                response.setError("Страница уже проиндексирована");
                return ResponseEntity.badRequest().body(response);
            }
        }
        log.info("INDEXING UPDATING!!");

        List<String> siteUrl = new ArrayList<>();
        siteUrl.add(ulr);

        RecursiveAction indexation = new SiteIndexer(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo,0,0);

        forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(indexation);
        log.info("INDEXING UPDATED!!!");
        System.out.println();

        IndexResponse response = new IndexResponse();
        response.setResult(true);
        response.setError("");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics(){
        StatisticsResponse response = new StatisticsResponse();
        Integer siteCount = 0;
        Integer pageCount = 0;
        Integer lemmaCount = 0;
        List<SiteModel> sitesList = new ArrayList<>();
       for (SiteModel model : siteRepo.findAll()){

           sitesList.add(model);
           siteCount +=1;
       }
        for (SiteModel model : sitesList){
            pageCount  += model.getPageCount().intValue();
        }

        for (SiteModel model : sitesList){
            lemmaCount += model.getLemmaCount();
        }
        TotalStatistics totalStatistics = new TotalStatistics();
        totalStatistics.setIndexing(true);
        totalStatistics.setPages(pageCount);
        totalStatistics.setLemmas(lemmaCount);
        totalStatistics.setSites(siteCount);
        List<DetailedStatisticsItem> statisticList = new ArrayList<>();
        for (SiteModel site : siteRepo.findAll()) {
            DetailedStatisticsItem statisticsItem = new DetailedStatisticsItem();
            statisticsItem.setError(site.getStatusError());
            statisticsItem.setStatus(site.getStatus().toString());
            statisticsItem.setName(site.getName());
            statisticsItem.setUrl(site.getUrl());
            statisticsItem.setStatusTime(site.getStatusTime().getTime());
            statisticsItem.setPages(site.getPageCount());
            statisticsItem.setLemmas(site.getLemmaCount());
            statisticList.add(statisticsItem);
        }
        StatisticsData statisticsData = new StatisticsData();
        statisticsData.setTotal(totalStatistics);
        statisticsData.setDetailed(statisticList);
        response.setResult(true);
        response.setStatistics(statisticsData);
        return ResponseEntity.ok(response);
    }



    @RequestMapping("/search{site}")
    public ResponseEntity<SearchResponse> search(@RequestParam String query, @RequestParam int offset, @RequestParam int limit, String site, Model model) throws SQLException {

        log.info("Search query - " + query);


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
        log.info("SEARCHING COMPLETED");
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