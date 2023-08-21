import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        List<String> urls = new ArrayList<>();

        try {

            Document site = getDocument("https://lenta.ru/");
            Elements links = site.select("[src]");

            for (Element src : links) {
                if (src.normalName().equals("img")) {
                    urls.add(src.attr("abs:src"));
                }
            }

            for (String url : urls) {
                downloadImage(url,
                        new File("C:\\Users\\Admin\\Desktop\\java_basics\\FilesAndNetwork\\homework_4\\images")
                                .getAbsolutePath());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public static Document getDocument(String url) throws IOException {
        Document site = Jsoup.connect(url).get();
        return site;
    }


    public static void downloadImage(String sourceUrl, String targetDirectory)
            throws MalformedURLException, IOException, FileNotFoundException {
        URL imageUrl = new URL(sourceUrl);
        try (InputStream imageReader = new BufferedInputStream(
                imageUrl.openStream());
             OutputStream imageWriter = new BufferedOutputStream(
                     new FileOutputStream(targetDirectory + File.separator
                             + FilenameUtils.getName(sourceUrl)));) {
            int readByte;

            while ((readByte = imageReader.read()) != -1) {
                imageWriter.write(readByte);
            }
        }
    }
}
