package searchengine;

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


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//
//try {
//
//    Document doc = Jsoup.connect("https://www.svetlovka.ru/").get();
//
//    Elements links = doc.getElementsByTag("a");
//
//    List<String> pages = new ArrayList<>();
//
//    for (Element link : links) {
//        if (link.attr("href") == null){
//            continue;
//        }else {
//            String href = link.attr("href");
//            pages.add(href);
//        }
//        pages.forEach(System.out::println);
//    }
//
//}catch (Exception ex){
//    ex.printStackTrace();
//        }
}

    public enum Status {
        INDEXING,
        INDEXED,
        FAILED

    }
}
