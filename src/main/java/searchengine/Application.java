package searchengine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
    private static Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("APPLICATION STARTED");


}

    public enum Status {
        INDEXING,
        INDEXED,
        FAILED

    }

    public static Logger getLogger() {
        return logger;
    }
}
