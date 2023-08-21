import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static String mainUrl = "https://lenta.ru/";
    private static List<String> urls = new ArrayList<>();

    public static void main(String[] args) {

        try{

       ForkJoinPool forkJoinPool = new ForkJoinPool().commonPool();

//       List<String> builder = forkJoinPool.invoke(new CartCreator(mainUrl));
////
////
//        builder.stream().forEach(System.out::println);
            Thread.sleep(150);

            System.out.println(mainUrl);
            for(String url : new UrlRunner().getAllUrls(mainUrl)){
                System.out.println(url);
                for (String urlC : new UrlRunner().getAllUrls(url)){
                    System.out.println("\t" + urlC);
                }
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }


    }


}

