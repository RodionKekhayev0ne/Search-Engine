package searchengine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
