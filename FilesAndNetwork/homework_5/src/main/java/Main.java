import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//https://javascopes.com/reading-and-writing-json-in-java-815dcb5c/
//js-toggle-depend s-depend-control-single

public class Main {


    private static String url = "https://skillbox-java.github.io/"; //LINK
    private static String path = "C:\\Users\\Admin\\Desktop\\java_basics\\FilesAndNetwork\\homework_5\\data\\check.json";


    public static void main(String[] args) {


        parseFileFromSite(url, path);

        System.out.println(showInfoAboutMetro(path));



    }

    public static void parseFileFromSite(String url, String path) {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(0).get();

            Elements lines = doc.select("span[data-line]");
            Elements stations = doc.select("div[data-depend-set]");

            JSONObject mainObject = new JSONObject();
            JSONObject object = new JSONObject();


            for (int i = 1; i <= lines.size(); i++) {
                JSONArray array = new JSONArray();

                if (i == 13) {
                    stations.select("div[data-depend-set=\"lines-11A\"]")
                            .select("p.single-station")
                            .stream()
                            .map(element -> element.text())
                            .distinct()
                            .forEach(array::add);
                    object.put("11A", array);
                } else if (i == 16) {
                    stations.select("div[data-depend-set=\"lines-D1\"]")
                            .select("p.single-station")
                            .stream()
                            .map(element -> element.text())
                            .distinct()
                            .forEach(array::add);
                    object.put("D1", array);
                } else if (i == 17) {
                    stations.select("div[data-depend-set=\"lines-D2\"]")
                            .select("p.single-station")
                            .stream()
                            .map(element -> element.text())
                            .distinct()
                            .forEach(array::add);
                    object.put("D2", array);
                } else {
                    stations.select("div[data-depend-set=\"lines-" + i + "\"]")
                            .select("p.single-station")
                            .stream()
                            .map(element -> element.text())
                            .distinct()
                            .forEach(array::add);
                    object.put(i, array);
                }
            }

            mainObject.put("stations", object);


            JSONArray linesArray = new JSONArray();

            for (int i = 1; i <= lines.size(); i++) {
                JSONObject line = new JSONObject();
                String num, name;

                if (i == 13) {
                    num = lines.select("span[data-line=11A]").attr("data-line");
                    line.put("number", num);
                    name = lines.select("span[data-line=11A]").text();
                    line.put("name", name);
                    linesArray.add(line);
                } else if (i == 16) {
                    num = lines.select("span[data-line=D1]").attr("data-line");
                    line.put("number", num);
                    name = lines.select("span[data-line=D1]").text();
                    line.put("name", name);
                    linesArray.add(line);
                } else if (i == 17) {
                    num = lines.select("span[data-line=D2]").attr("data-line");
                    line.put("number", num);
                    name = lines.select("span[data-line=D2]").text();
                    line.put("name", name);
                    linesArray.add(line);
                } else {
                    num = lines.select("span[data-line=" + i + "]").attr("data-line");
                    line.put("number", num);
                    name = lines.select("span[data-line=" + i + "]").text();
                    line.put("name", name);

                    linesArray.add(line);
                }
            }
            mainObject.put("lines", linesArray);

            Files.write(Paths.get(path), mainObject.toJSONString().getBytes());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    static public String showInfoAboutMetro(String path) {

        String jsonFile = getJsonFile(path);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ИНФОРМАЦИЯ О ЛИНИЯХ И СТАНЦИЯХ МОСКОВСКОГО МЕТРО" + "\n");

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonFile);

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            JSONObject stationsObject = (JSONObject) jsonData.get("stations");


            for (int i = 0; i < linesArray.size(); i++) {
                String[] line = linesArray.get(i).toString().split("\"");
                //    3 7
                JSONArray stationArray = (JSONArray) stationsObject.get(line[3]);

                stringBuilder.append("Название линии: "
                        + line[7]
                        + "\n"
                        + "\t\t"
                        + "Кол-во станций: "
                        + stationArray.size()
                        + "\n\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return stringBuilder.toString();
    }


    private static String getJsonFile(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
