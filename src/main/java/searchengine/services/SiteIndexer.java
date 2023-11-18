package searchengine.services;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class SiteIndexerAct extends RecursiveAction {


    private final List<String> urls;
    private final SitesRepo siteRepo;
    private final PageRepo pageRepo;
    private final LemmaRepo lemmaRepo;
    private final IndexRepo indexRepo;
    private Document doc;
    private SiteModel site;
    private Logger logger = Application.getLogger();


    public SiteIndexerAct(List<String> urls, SitesRepo siteRepo, PageRepo pageRepo, LemmaRepo lemmaRepo, IndexRepo indexRepo) {
        this.urls = urls;
        this.siteRepo = siteRepo;
        this.pageRepo = pageRepo;
        this.lemmaRepo = lemmaRepo;
        this.indexRepo = indexRepo;
    }


    @Override
    protected void compute() {


        try {


            for (String url : urls) {


                doc = Jsoup.connect(url).get();
                site = new SiteModel();

                if (doc == null) {

                    logger.error("CONNECTION PROBLEMS ON THE SITE " + url);
                    site.setUrl(url);
                    site.setName(doc.title());
                    site.setStatus(Application.Status.INDEXED);
                    site.setStatusError("connections problems");
                    site.setStatusTime(new Date());
                } else {

                    site.setUrl(url);
                    site.setName(doc.title());
                    site.setStatus(Application.Status.INDEXED);
                    site.setStatusError("none");
                    site.setStatusTime(new Date());

                    logger.info("site added " + site.getName());
                    siteRepo.save(site);
                }


                Elements links = doc.getElementsByTag("a");

                List<String> pages = new ArrayList<>();

                for (Element link : links) {
                    if (link.attr("href") == null) {
                        logger.error("link not found or equal null");
                    } else {
                        String href = link.attr("href");
                        if (!href.isEmpty() || !href.isBlank()) {
                            pages.add(href);
                            logger.info("ADDED NEW LINK" + href);
                        }
                    }
                }

                for (String link : pages) {

                    String docLink = link;
                    if (link.equals("/cookie")) {

                    } else {
                        Page page = new Page();
                        if (link.startsWith("/")) {
                            docLink = site.getUrl() + link;
                            createEnttPLI(docLink);
                        }
                        if (link.startsWith("www")) {
                            docLink = "http://" + link;
                            createEnttPLI(docLink);
                        }
                        if (link.startsWith("#")) {
                            continue;
                        }
                        if (link.startsWith("http://catalog.svetlovka.ru/jirbis2/index.php") || link.startsWith("https://www.svetlovka.ru//events/lektsii/")) {
                            continue;
                        }
                        if (link.startsWith("http://")) {
                            docLink = link;
                            createEnttPLI(docLink);
                        }


                    }
                }

            }
            } catch (Exception ex) {
            logger.warn(ex.getMessage());
            }


    }
    private void createEnttPLI(String docLink){
try {

        LemmaFinder finder = LemmaFinder.getInstance();
        Page page = new Page();
        Document pageDT = Jsoup.connect(docLink).get();
        page.setSiteId(site);
        page.setPath(docLink);
        page.setContent(pageDT.html());
        page.setCode(200);
        pageRepo.save(page);

        Map<String, Integer> lemmaMap = finder.collectLemmas(pageDT.text());
        for (String lemmas : lemmaMap.keySet()) {
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
    }catch (Exception ex){
             logger.warn(ex.getMessage());
}
    }


}