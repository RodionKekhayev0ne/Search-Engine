package searchengine;

import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.SiteModel;

import java.util.ArrayList;
import java.util.List;

public class ModelStorage {




    private SiteModel site;
    private List<Page> pages = new ArrayList<>();
    private List<Lemma> lemmas = new ArrayList<>();
    private List<Index> indexList = new ArrayList<>();



    public ModelStorage() {
    }

    public SiteModel getSite() {
        return site;
    }

    public void setSite(SiteModel site) {
        this.site = site;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(Page page) {
         pages.add(page);
    }

    public List<Lemma> getLemmas() {
        return lemmas;
    }

    public void setLemmas(Lemma lemma) {
          lemmas.add(lemma);
    }

    public List<Index> getIndexList() {
        return indexList;
    }

    public void setIndexList(Index index) {
        indexList.add(index);
    }

}
