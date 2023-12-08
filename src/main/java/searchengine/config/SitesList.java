package searchengine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import searchengine.services.SiteService;
import searchengine.model.SiteModel;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "indexing-settings")
public class SitesList {

    private SiteService service;

    public static String urls;


    @Autowired
    public  SitesList(SiteService service) {
        this.service = service;
    }

    public static String getUrls() {
        return urls;
    }

    public List<Site> getSites() {


        Iterable<SiteModel> sitesItr =  service.getAllEntities();
        List<Site> sites = new ArrayList<>();


        for (SiteModel site : sitesItr){
            Site siteL = new Site();

            siteL.setName(site.getName());
            siteL.setUrl(site.getUrl());

            sites.add(siteL);
        }

        return sites;
    }
}