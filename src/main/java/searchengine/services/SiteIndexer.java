package searchengine.services;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
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



@Slf4j
@AllArgsConstructor
public class SiteIndexer extends RecursiveAction {

    private final List<String> urls;
    private final SitesRepo siteRepo;
    private final PageRepo pageRepo;
    private final LemmaRepo lemmaRepo;
    private final IndexRepo indexRepo;
    private int pageCount;

    private int lemmaCount;
    @Override
    protected void compute() {
        try {
            LemmaFinder finder = LemmaFinder.getInstance();
            for (String url : urls) {
                Document doc = Jsoup.connect(url).get();
                SiteModel site = new SiteModel();
                if (doc == null) {
                    log.error("CONNECTION PROBLEMS ON THE SITE " + url);
                     site = SiteModel.builder()
                            .name(doc.title())
                            .status(Application.Status.FAILED)
                            .statusError("connections problems")
                            .statusTime(new Date())
                            .url(url)
                            .pageCount(0)
                            .lemmaCount(0)
                            .build();
                       siteRepo.save(site);
                } else {

                    site = SiteModel.builder()
                            .name(doc.title())
                            .status(Application.Status.INDEXED)
                            .statusError("")
                            .statusTime(new Date())
                            .url(url)
                            .pageCount(pageCount)
                            .lemmaCount(lemmaCount)
                            .build();
                    siteRepo.save(site);
                    log.info("site add " + site.getName());
                }
                siteRepo.save(site);
                Elements links = doc.getElementsByTag("a");
                for (Element link : links) {
                    String href = link.attr("href");
                    formatLink(href, url,site,doc);
                    }
                site.setPageCount(pageCount);
                site.setLemmaCount(lemmaCount);
                siteRepo.save(site);
             }
            } catch(Exception ex){
                log.warn(ex.getMessage());
            }
    }
    private void createEntities(String documentLink,SiteModel site,Document doc){
    try {
        LemmaFinder finder = LemmaFinder.getInstance();
        Document pageData = Jsoup.connect(documentLink).get();
                 Page page = Page.builder().siteId(site)
                         .path(documentLink)
                         .content((pageData.text() + pageData.title())
                                 .toLowerCase())
                         .title(pageData.title())
                         .code(200)
                         .build();
                pageRepo.save(page);
                Map<String, Integer> lemmaMap = finder.collectLemmas((pageData.text() + pageData.title()).toLowerCase());
                for (String lemmas : lemmaMap.keySet()) {
                    lemmaCount++;
                    Lemma lemma = Lemma.builder()
                            .siteId(site)
                            .lemma(lemmas)
                            .frequency(lemmaMap.get(lemmas)).build();
                    Index index = Index.builder()
                            .lemmaId(lemma).pageId(page)
                            .rank(lemmaMap.get(lemmas).doubleValue())
                            .build();
                    lemmaRepo.save(lemma);
                    indexRepo.save(index);
                }
        }catch(Exception ex){
            log.warn(ex.getMessage());
        }
    }
    private void formatLink(String href,String url,SiteModel site, Document doc){
        if (!href.isEmpty() && !href.equals("/cookie")) {
            switch (String.valueOf(href.charAt(0))){
                case "/":
                    String documentLink = url + href;
                    pageCount++;
                    createEntities(documentLink,site,doc);
                default:
                    documentLink = href;
                    pageCount++;
                    createEntities(documentLink,site,doc);
            }
        }
    }
}