package searchengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "searcherproperties")
public class SearchConfig {



    private static String url;
    private static String password;
    private static String username;


    public static String getUrl() {
        return url;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUser() {
        return username;
    }


}
