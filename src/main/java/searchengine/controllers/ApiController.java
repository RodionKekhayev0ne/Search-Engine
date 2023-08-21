package searchengine.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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


    @PostMapping("/indexPage")
    public ResponseEntity updateSite(@RequestParam("url") String ulr) {


        for (SiteModel siteModel: siteRepo.findAll()) {
            if (siteModel.getUrl().equals(ulr)){
                System.out.println("ALREADY EXIST");
                return ResponseEntity.status(HttpStatus.OK).body("URL IS ALREADY EXIST IN DB " + "\n" + siteModel );
            }
        }
        System.out.println("INDEXING UPDATING!!!");

        List<String> siteUrl = new ArrayList<>();
        siteUrl.add(ulr);

        RecursiveAction indexation = new SiteIndexerAct(siteUrl, siteRepo, pageRepo, lemmaRepo, indexrepo);

        forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(indexation);


        System.out.println("INDEXING UPDATED!!!");
        return ResponseEntity.status(HttpStatus.OK).body("URL ADDED IN DB " + "\n" + siteRepo.findAll());
    }



    @GetMapping("/search{site}")
    public ResponseEntity search(@RequestParam String query, @RequestParam int offset, @RequestParam int limit, String site, Model model) throws SQLException {

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

           return ResponseEntity.status(HttpStatus.OK).body(searcher.search());
    }


}

//Интернет-магазин PlayBack.ru",
//    "Вышний Волочёк портал города",
//    "Центральная городская молодежная библиотека им. М. А. Светлова"
//]