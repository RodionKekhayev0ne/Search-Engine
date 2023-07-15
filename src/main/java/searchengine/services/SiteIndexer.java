package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import searchengine.Application;
import searchengine.LemmaFinder;
import searchengine.ModelStorage;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.SiteModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;


public class SiteIndexer extends RecursiveTask<List<ModelStorage>> {


    private List<String> urls;
    private Integer start;
    private Integer end;
    private List<ModelStorage> storage = new ArrayList<>();
    private SiteIndexer firstTask;
    private SiteIndexer secondTask;


    public SiteIndexer(Integer start, Integer end, List<String> urls) {
        this.start = start;
        this.end = end;
        this.urls = urls;
    }

    @Override
    protected List<ModelStorage> compute() {
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

                    System.out.println("site added " + site.getName());
                    storage1.setSite(site);

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
                                    storage1.setPages(page);

                                    Map<String, Integer> lemmaMap = finder.collectLemmas(pageDT.text());
                                    for (String lemmas : lemmaMap.keySet()) {
                                        Lemma lemma = new Lemma();
                                        Index index = new Index();
                                        lemma.setSiteId(site);
                                        lemma.setLemma(lemmas);
                                        lemma.setFrequency(lemmaMap.get(lemmas));
                                        storage1.setLemmas(lemma);
                                        index.setLemmaId(lemma);
                                        index.setPageId(page);
                                        index.setRank(lemmaMap.get(lemmas).doubleValue());
                                        storage1.setIndexList(index);
                                    }
                                }

                        }
                        storage.add(storage1);
                    }

                    start++;
                    firstTask = new SiteIndexer(start, mid, urls);
                    secondTask = new SiteIndexer(mid, end, urls);

                    firstTask.fork();
                    secondTask.fork();

                    for (ModelStorage model : firstTask.join()) {
                        storage.add(model);
                    }
                    for (ModelStorage model : secondTask.join()) {
                        storage.add(model);
                    }



                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return storage;
    }

}