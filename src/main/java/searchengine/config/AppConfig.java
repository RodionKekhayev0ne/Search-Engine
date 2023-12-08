package searchengine.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import searchengine.controllers.ApiController;
import searchengine.repos.SitesRepo;

import java.util.concurrent.ForkJoinPool;

@Configuration
@ComponentScan("searchengine.controllers")
@ComponentScan("searchengine.repos")
@ConfigurationProperties(prefix = "indexing-settings")
public class AppConfig {



    private final ApiController apiController;

    @Autowired
    public AppConfig(ApiController apiController) {
        this.apiController = apiController;
    }


    // ...
}