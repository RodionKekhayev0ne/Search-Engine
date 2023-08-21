package com.company;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public class LinkParser {

    public static final String DEFAULT_URL = "https://skillbox.ru/";
    public static final List<String> STOP_WORDS = Arrays.asList("instagram", ".pdf", "twitter", "facebook", "utm", "vkontakte");

    public static Set<String> parsePage(String pageUrl) throws InterruptedException {
        //таймаут чтобы не выбивал сервер
        Thread.sleep(150);
        Set<String> urls = new HashSet<>();
        try {
            try {
                Document doc = Jsoup.connect(pageUrl).timeout(5000).get();
                for (Element element : doc.select("a")) {
                    String url = element.attr("href");
                    if (url.startsWith("/")) {
                        url = DEFAULT_URL + url.substring(1);
                        if (STOP_WORDS.stream().noneMatch(url::contains)) {
                            synchronized (Main.siteMapSet) {
                                //обязательно используем синхронизацию ресурсов, иначе будет Concurrent Exception
                                if (!Main.siteMapSet.contains(url)) {
                                    //впринципе мы могли бы обойтись без urls, это скорее заготовка под использование join конструкции, если мы будем считать все ссылки не в общей мапе
                                    urls.add(url);
                                    Main.siteMapSet.add(url);
                                }
                            }
                        }
                    } else if (url.startsWith("http") && url.contains("skillbox.ru")) {
                        if (STOP_WORDS.stream().noneMatch(url::contains)) {
                            //обязательно используем синхронизацию ресурсов, иначе будет Concurrent Exception
                            synchronized (Main.siteMapSet) {
                                if (!Main.siteMapSet.contains(url)) {
                                    urls.add(url);
                                    Main.siteMapSet.add(url);
                                }
                            }
                        }
                    }
                }
            } catch (HttpStatusException e) {
                return Collections.EMPTY_SET;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }
}
