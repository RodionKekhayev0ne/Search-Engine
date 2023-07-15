package searchengine.controllers;

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

import java.util.*;


public class Indexator extends Thread {

    private String block = "https://oauth.vk.com/authorize?client_id=8119513&response_type=code&redirect_uri=https%253A%252F%252Fvolochek.life%252Findex.php%253Foption%253Dcom_slogin%2526task%253Dcheck%2526plugin%253Dvkontakte&scope=offline,email";

    private String url;
    private SiteModel siteModel;
    private  List<Page> pageModels = new ArrayList<>();
    private  List<Lemma> lemmasL = new ArrayList<>();
    private List<Index> indices = new ArrayList<>();



    public Indexator(String url) {
        this.url = url;
    }


    @Override
    public void run() {


        try {
            LemmaFinder finder = LemmaFinder.getInstance();

            Document doc = Jsoup.connect(url).get();

            SiteModel site = new SiteModel();
            site.setUrl(url);
            site.setName(doc.title());
            site.setStatus(Application.Status.INDEXED);
            site.setStatusError("none");
            site.setStatusTime(new Date());
            siteModel = site;



            Elements links = doc.getElementsByTag("a");

            List<String> pages = new ArrayList<>();


            for (Element link : links) {
                String href = link.attr("href");
                pages.add(href);
            }

            for (String link : pages) {
                if (link.equals("/cookie") || link.equals(block)) {
                } else {

                    if (link.length() > 0)
                        if (String.valueOf(link.charAt(0)).equals("/")) {
                            Page pageMod = new Page();

                            Document pageDT = Jsoup.connect(url + link).get();
                            pageMod.setSiteId(siteModel);
                            pageMod.setPath(link);
                            pageMod.setContent(pageDT.html());
                            pageMod.setCode(200);
                            pageModels.add(pageMod);

                            Map<String, Integer> lemmaMap = finder.collectLemmas(pageDT.text());
                            for (String lemmas : lemmaMap.keySet()) {
                                Lemma lemma = new Lemma();
                              //  Index index = new Index();
                                lemma.setSiteId(siteModel);
                                lemma.setLemma(lemmas);
                                lemma.setFrequency(lemmaMap.get(lemmas));
                                lemmasL.add(lemma);
//                                index.setLemmaId(lemma);
//                                index.setPageId(pageMod);
//                                index.setRank(lemmaMap.get(lemmas).doubleValue());
//                                indices.add(index);
                            }

                        }
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SiteModel getSiteModel() {return siteModel;}

    public List<Page> getPages() {return pageModels;}

    public List<Lemma> getLemmasL() {
        return lemmasL;
    }

    public List<Index> getIndexes() {
        return indices;
    }

}
