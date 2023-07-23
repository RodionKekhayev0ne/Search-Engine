package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.repos.IndexRepo;
import searchengine.repos.LemmaRepo;
import searchengine.repos.PageRepo;
import searchengine.repos.SitesRepo;
import searchengine.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

@RestController
@ConfigurationProperties(prefix = "indexing-settings")
@RequestMapping("/api")
public class ApiController {


    private final StatisticsService statisticsService;
    private SitesRepo siteRepo;
    private PageRepo pageRepo;
    private LemmaRepo lemmaRepo;
    private IndexRepo indexrepo;
    private String urls;

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

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        forkJoinPool.invoke(indexation);

        System.out.println("INDEXING ENDED!!!");
        return ResponseEntity.ok(statisticsService.getStatistics());
    }


}