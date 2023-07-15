package searchengine.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("searchengine.controllers")
public class AppConfig {
    // ...
}