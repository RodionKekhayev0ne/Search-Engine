import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
class CartCreator extends RecursiveTask<List<String>> {



    private List<String> urls;

    private List<String> resList = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private String url;




    private  List<CartCreator> taskList = new ArrayList<>();
    private List<String> checkList  = new ArrayList<>();
    private  UrlRunner runner = new UrlRunner();



    CartCreator(String url){
        this.url = url;
    }


@Override
    protected List<String> compute() {

    try{
        Thread.sleep(150);
        if (runner.checkUrl(url)) {
            resList.add(url);
            System.out.println(url);

            for (String urls : runner.getAllUrls(url)) {
                if (urls == null) {
                    continue;
                } else {
                    url = urls;
                    System.out.println(urls);
                    CartCreator task = new CartCreator(urls);
                    task.fork();
                    taskList.add(task);
                }
            }
        }
            for (CartCreator task : taskList){
                task.join();
                }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return resList;
        }

}
