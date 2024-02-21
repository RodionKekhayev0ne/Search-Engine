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
import java.util.*;
import java.util.concurrent.RecursiveAction;


@Slf4j
@AllArgsConstructor
public class SiteIndexer extends RecursiveAction{

    private final List<String> urls;
    private final transient SitesRepo siteRepo;
    private final transient PageRepo pageRepo;
    private final transient LemmaRepo lemmaRepo;
    private final transient IndexRepo indexRepo;
    private int pageCount;
    private int lemmaCount;

    @Override
    protected void compute() {
        try {

            for (String url : urls) {
                Document doc = Jsoup.connect(url).get();
                SiteModel site;
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
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    String href = link.attr("abs:href");
                    formatLink(href, url, site);
                }
                site.setPageCount(pageCount);
                site.setLemmaCount(lemmaCount);
                siteRepo.save(site);
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    private void createEntities(String documentLink, SiteModel site) {
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

            List<String> withoutLemmatization = Arrays.stream(pageData.text().split("\\s+")).toList();
            Map<String, Integer> withoutLemmatizationCount = new HashMap<>();

            for (String word : withoutLemmatization) { withoutLemmatizationCount.put(word, withoutLemmatizationCount.getOrDefault(word, 0) + 1);}

            for (String word : withoutLemmatizationCount.keySet()){
                Lemma lemma = Lemma.builder()
                        .siteId(site)
                        .lemma(word.toLowerCase())
                        .frequency(withoutLemmatizationCount.get(word)).build();
                Index index = Index.builder()
                        .lemmaId(lemma).pageId(page)
                        .rank(withoutLemmatizationCount.get(word).doubleValue())
                        .build();
                lemmaRepo.save(lemma);
                indexRepo.save(index);
                crawl(documentLink,site);
            }
            Map<String, Integer> lemmaMap = finder.collectLemmas((pageData.text() + pageData.title()).toLowerCase());
            for (String lemmas : lemmaMap.keySet()) {
                lemmaCount++;
                Lemma lemma = Lemma.builder()
                        .siteId(site)
                        .lemma(lemmas.toLowerCase())
                        .frequency(lemmaMap.get(lemmas)).build();
                Index index = Index.builder()
                        .lemmaId(lemma).pageId(page)
                        .rank(lemmaMap.get(lemmas).doubleValue())
                        .build();
                lemmaRepo.save(lemma);
                indexRepo.save(index);
                crawl(documentLink,site);
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    private void formatLink(String href, String url, SiteModel site) {
        if (!href.isEmpty() && !href.equals("/cookie")) {
            switch (String.valueOf(href.charAt(0))) {
                case "www":
                    String documentLink = url + href;
                    createEntities(documentLink, site);
                    pageCount++;
                    break;
                case "/":
                    documentLink = url + href;
                    pageCount++;
                    createEntities(documentLink, site);
                    break;
                default:
                    documentLink = href;
                    pageCount++;
                    createEntities(documentLink, site);
                    break;
            }
        }
    }

    public void crawl(String url,SiteModel site) {
        try {
            // Проверка, что URL еще не был посещен
            if (pageRepo.findByPath(url) == null) {
                // Загрузка веб-страницы
                Document doc = Jsoup.connect(url).get();
                // Добавление текущего URL в список посещенных
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    String href = link.attr("abs:href");
                    formatLink(href, url, site);
                }
            }
        } catch (IOException e) {
            log.error("For '" + url + "': " + e.getMessage());
        }
    }
}