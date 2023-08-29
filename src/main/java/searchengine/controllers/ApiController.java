package searchengine.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import searchengine.Application;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.Page;
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
        logger.info("INDEXING STOPPED!!!");
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<StatisticsResponse> indexSite() {

        logger.info("INDEXING STARTED!!!");
        List<String> siteUrl = new ArrayList<>();

        String[] split = urls.split(",");
        for (int i = 0; i < split.length; i++) {
            siteUrl.add(split[i]);

        }


        RecursiveAction indexation = new SiteIndexerAct(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo);

        forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(indexation);


        logger.info("INDEXING ENDED!!!");
        return ResponseEntity.ok(statisticsService.getStatistics());
    }


    @PostMapping("/indexPage")
    public ResponseEntity updateSite(@RequestParam("url") String ulr) {


        for (SiteModel siteModel: siteRepo.findAll()) {
            if (siteModel.getUrl().equals(ulr)){
                logger.info("URL ALREADY EXIST");

                return ResponseEntity.status(HttpStatus.OK).body("URL IS ALREADY EXIST IN DB " + "\n" + siteModel );
            }
        }
        logger.info("INDEXING UPDATING!!");


        List<String> siteUrl = new ArrayList<>();
        siteUrl.add(ulr);

        RecursiveAction indexation = new SiteIndexerAct(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo);

        forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(indexation);


        logger.info("INDEXING UPDATED!!!");
        System.out.println();
        return ResponseEntity.status(HttpStatus.OK).body("URL ADDED IN DB " + "\n" + siteRepo.findAll());
    }



    @GetMapping("/search{site}")
    public ResponseEntity search(@RequestParam String query, @RequestParam int offset, @RequestParam int limit, String site, Model model) throws SQLException {

        logger.info("Search query - " + query);


        DbSearcher searcher = new DbSearcher();

        if (site == null){
             searcher = new DbSearcher(query,limit,null);
        }else {


            for (SiteModel siteModel : siteRepo.findAll()) {
                if (siteModel.getUrl().equals(site)) {
                    searcher = new DbSearcher(query, limit,siteModel.getId());

                }
            }

        }

        logger.info("SEARCHING COMPLETED");
           return ResponseEntity.status(HttpStatus.OK).body(searcher.search());
    }


}

//Интернет-магазин PlayBack.ru",
//    "Вышний Волочёк портал города",
//    "Центральная городская молодежная библиотека им. М. А. Светлова"
//]