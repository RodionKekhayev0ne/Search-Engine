import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class UrlRunner {



    private List<String> urlsAlreadyUsed = new ArrayList<>();




    public  List<String> getAllUrls(String url){

        List<String> resList = new ArrayList<>();


                if (checkUrl(url) && !urlsAlreadyUsed.contains(url)) {
                    urlsAlreadyUsed.add(url);

                    try {


                        Document doc = Jsoup.connect(url).get();

                        Elements links = doc.select("a");


                        for (Element element : links) {
                            if(checkUrl(element.attr("href"))) {
                                resList.add("\t" + element.attr("href"));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }else {
                    resList.add(null);
                }



        return resList;
    }


    public boolean checkUrl(String url){
        try {
            String[] domenCheck = url.split("/");
            String[] check2 = url.split("\\.");

            for (int i = 0;i < check2.length;i++) {
                if ((check2[i].equals("pdf"))) {
                    return false;
                }
            }

                    for (int j = 0;j < domenCheck.length;j++){
                        if((domenCheck[j].equals("lenta.ru"))){
                            return true;
                        }
                    }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }




}
