package searchengine.services;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import searchengine.Application;
import searchengine.LemmaFinder;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.SiteModel;
import searchengine.repos.IndexRepo;
import searchengine.repos.LemmaRepo;
import searchengine.repos.PageRepo;
import searchengine.repos.SitesRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class SiteIndexer extends RecursiveAction {


    private final List<String> urls;
    private final SitesRepo siteRepo;
    private final PageRepo pageRepo;
    private final LemmaRepo lemmaRepo;
    private final IndexRepo indexRepo;
    private Document doc;
    private SiteModel site;
    private Logger logger = Application.getLogger();
    LemmaFinder finder;


    public SiteIndexer(List<String> urls, SitesRepo siteRepo, PageRepo pageRepo, LemmaRepo lemmaRepo, IndexRepo indexRepo) {
        this.urls = urls;
        this.siteRepo = siteRepo;
        this.pageRepo = pageRepo;
        this.lemmaRepo = lemmaRepo;
        this.indexRepo = indexRepo;
        try {
            finder = LemmaFinder.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int lemmaCount = 0;
    @Override
    protected void compute() {
        try {
            for (String url : urls) {
                doc = Jsoup.connect(url).get();
                site = new SiteModel();
                int pageCount = 0;
                if (doc == null) {
                    logger.error("CONNECTION PROBLEMS ON THE SITE " + url);
                    site.setUrl(url);
                    site.setName(doc.title());
                    site.setStatus(Application.Status.FAILED);
                    site.setStatusError("connections problems");
                    site.setStatusTime(new Date());
                    site.setPageCount(0);
                    site.setLemmaCount(0);
                } else {
                    site.setUrl(url);
                    site.setName(doc.title());
                    site.setStatus(Application.Status.INDEXED);
                    site.setStatusError("");
                    site.setStatusTime(new Date());
                    site.setPageCount(pageCount);
                    site.setLemmaCount(lemmaCount);
                    logger.info("site added " + site.getName());
                }
                siteRepo.save(site);
                Elements links = doc.getElementsByTag("a");
                for (Element link : links) {
                    String href = link.attr("href");
                    if (!href.isEmpty() && !href.equals("/cookie")) {
                            switch (String.valueOf(href.charAt(0))){
                                case "/":
                                    String documentLink = url + href;
                                    pageCount++;
                                    createEntities(documentLink);
                                default:
                                    documentLink = href;
                                    pageCount++;
                                    createEntities(documentLink);
                            }
                        }
                    }
                site.setPageCount(pageCount);
                site.setLemmaCount(lemmaCount);
                siteRepo.save(site);
             }
            } catch(Exception ex){
                logger.warn(ex.getMessage());
            }
    }
    private void createEntities(String documentLink){
    try {
        Page page = new Page();
        Document pageData = Jsoup.connect(documentLink).get();
                page.setSiteId(site);
                page.setPath(documentLink);
                page.setContent((pageData.text() + pageData.title()).toLowerCase());
                page.setTitle(pageData.title());
                page.setCode(200);
                pageRepo.save(page);
                Map<String, Integer> lemmaMap = finder.collectLemmas((pageData.text() + pageData.title()).toLowerCase());
                for (String lemmas : lemmaMap.keySet()) {
                    lemmaCount++;
                    Lemma lemma = new Lemma();
                    Index index = new Index();
                    lemma.setSiteId(site);
                    lemma.setLemma(lemmas);
                    lemma.setFrequency(lemmaMap.get(lemmas));
                    lemmaRepo.save(lemma);
                    index.setLemmaId(lemma);
                    index.setPageId(page);
                    index.setRank(lemmaMap.get(lemmas).doubleValue());
                    indexRepo.save(index);
                }
        }catch(Exception ex){
            logger.warn(ex.getMessage());
        }

    }
}