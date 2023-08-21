package com.company;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {

    //как хранилище будем использовать статическую мапу
    static TreeSet<String> siteMapSet = new TreeSet<>();
    static StringBuilder builder = new StringBuilder();

    public static <AdaptiveCoding> void main(String[] args) throws InterruptedException {
        new ForkJoinPool().invoke(new RecursiveWebLinkParser(LinkParser.parsePage(LinkParser.DEFAULT_URL)));

        try {
            builder.append("https://skillbox.ru/");
            for (String url : siteMapSet) {
                if (url.split("/").length > 4) {
                    builder.append( "\n" + "\t" + "\t" + url);
                } else {
                    builder.append("\n" + "\t" + url);
                }
            }

            File file = new File("C:\\Users\\Admin\\Desktop\\java_basics\\Multithreading\\concurrencyLinkParser\\src\\main\\resources\\cart.txt");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));

                writer.write(builder.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (writer != null) writer.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("work is done!!!");
    }
}
