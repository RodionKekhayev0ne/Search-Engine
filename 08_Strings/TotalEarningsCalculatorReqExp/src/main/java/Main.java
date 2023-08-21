import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String text = "Коля заработал 84444  рублей, Федя - 34254 рубля, а Саша - 43232 рублей";
        System.out.println(calculateSalarySum(text));
    }

    public static int calculateSalarySum(String text) {
        //TODO: реализуйте методSystem.out.println(sub);

        String regex = "[0-9]{3,4}[^\\s]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int salary = 0;

        while (matcher.find()) {

            int start = matcher.start();
            int end = matcher.end();
            String money = text.substring(start, end);
            salary += Integer.parseInt(money);

        }

        return salary;
    }

}