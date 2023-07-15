package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.ModelStorage;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.SiteModel;
import searchengine.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

@RestController
@ConfigurationProperties(prefix = "indexing-settings")
@RequestMapping("/api")
public class ApiController {


    private final StatisticsService statisticsService;
    private SiteService service;
    private PageService pageService;
    private LemmaService lemmaService;
    private IndexService indexService;
    private String urls;

    public void setUrls(String urls){
        this.urls = urls;
    }

    @Autowired
    public ApiController(SiteService service, PageService pageService, LemmaService lemmaService, IndexService indexService, StatisticsService statisticsService) {
        this.service = service;
        this.pageService = pageService;
        this.lemmaService = lemmaService;
        this.indexService = indexService;
        this.statisticsService = statisticsService;
    }





    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }


    @GetMapping("/startIndexing")
    public ResponseEntity<StatisticsResponse>  indexSite(){


        System.out.println("INDEXING STARTED!!!");
        List<String> siteUrl = new ArrayList<>();

        String[] split = urls.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
            siteUrl.add(split[i]);

        }

        RecursiveAction indexation = new SiteIndexerAct(0,siteUrl.size(),siteUrl,service,pageService,lemmaService,indexService);

//        RecursiveTask<List<ModelStorage>> indexation = new SiteIndexer(0, siteUrl.size(),siteUrl,service);
          ForkJoinPool forkJoinPool = new ForkJoinPool();

          forkJoinPool.invoke(indexation);

//        if (forkJoinPool.invoke(indexation) == null){
//            System.out.println("not yet");
//        }else {
//
//            System.out.println("сколько обьектов " + forkJoinPool.invoke(indexation).size());
//            for (ModelStorage model : forkJoinPool.invoke(indexation)) {
//                service.createEntity(model.getSite());
//
//                for (Page page : model.getPages()) {
//                    pageService.createEntity(page);
//                }
//
//                for (Lemma lemma : model.getLemmas()){
//                    lemmaService.createEntity(lemma);
//                }

//                for (Index index : model.getIndexList()){
//                    indexService.createEntity(index);
//                }
 //           }




        System.out.println("INDEXING ENDED!!!");
        return ResponseEntity.ok(statisticsService.getStatistics());
    }


}