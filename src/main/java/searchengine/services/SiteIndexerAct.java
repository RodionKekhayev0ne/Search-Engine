package searchengine.services;
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


                LemmaFinder finder = LemmaFinder.getInstance();
                Document doc = Jsoup.connect(url).get();


                SiteModel site = new SiteModel();
                site.setUrl(url);
                site.setName(doc.title());
                site.setStatus(Application.Status.INDEXED);
                site.setStatusError("none");
                site.setStatusTime(new Date());

                siteRepo.save(site);

                System.out.println("site added " + site.getName());
//


                Elements links = doc.getElementsByTag("a");

                List<String> pages = new ArrayList<>();

                for (Element link : links) {
                    String href = link.attr("href");
                    pages.add(href);
                }

                for (String link : pages) {

                    if (link.equals("/cookie")) {
                    } else {

                        if (link.length() > 0)
                            if (String.valueOf(link.charAt(0)).equals("/")) {
                                Page page = new Page();

                                Document pageDT = Jsoup.connect(url + link).get();
                                page.setSiteId(site);
                                page.setPath(link);
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
                            }

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}