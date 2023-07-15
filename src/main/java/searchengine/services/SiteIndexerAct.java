package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import searchengine.Application;
import searchengine.LemmaFinder;
import searchengine.ModelStorage;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.SiteModel;
import searchengine.repos.SitesRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;


public class SiteIndexerAct extends RecursiveAction {


    private List<String> urls;
    private Integer start;
    private Integer end;
    private List<ModelStorage> storage = new ArrayList<>();
    private SiteIndexer firstTask;
    private SiteIndexer secondTask;
    private SiteService service;
    private PageService pageService;
    private LemmaService lemmaService;
    private IndexService indexService;


    public SiteIndexerAct(Integer start, Integer end, List<String> urls,SiteService siteService,PageService pageService,LemmaService lemmaService,IndexService indexService) {
        this.start = start;
        this.end = end;
        this.urls = urls;
        this.service = siteService;
        this.pageService = pageService;
        this.lemmaService = lemmaService;
        this.indexService = indexService;
    }

    @Override
    protected void compute() {
        try {
            int mid = (end - start) / 2;

            if (mid == 0) {
                System.out.println("indexing ended i hope");
            } else {
                for (String url : urls) {

                    ModelStorage storage1 = new ModelStorage();


                    LemmaFinder finder = LemmaFinder.getInstance();
                    Document doc = Jsoup.connect(url).get();


                    SiteModel site = new SiteModel();
                    site.setUrl(url);
                    site.setName(doc.title());
                    site.setStatus(Application.Status.INDEXED);
                    site.setStatusError("none");
                    site.setStatusTime(new Date());

                    service.createEntity(site);

                    System.out.println("site added " + site.getName());
//                    storage1.setSite(site);


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
                                    pageService.createEntity(page);

                                    Map<String, Integer> lemmaMap = finder.collectLemmas(pageDT.text());
                                    for (String lemmas : lemmaMap.keySet()) {
                                        Lemma lemma = new Lemma();
                                        Index index = new Index();
                                        lemma.setSiteId(site);
                                        lemma.setLemma(lemmas);
                                        lemma.setFrequency(lemmaMap.get(lemmas));
                                        lemmaService.createEntity(lemma);

                                        index.setLemmaId(lemma);
                                        index.setPageId(page);
                                        index.setRank(lemmaMap.get(lemmas).doubleValue());
                                        indexService.createEntity(index);
                                    }
                                }

                        }
                        storage.add(storage1);
                    }


                    firstTask = new SiteIndexer(start, mid, urls);
                    secondTask = new SiteIndexer(mid, end, urls);

                    firstTask.fork();
                    secondTask.fork();

                    firstTask.join();
                    secondTask.join();


                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}