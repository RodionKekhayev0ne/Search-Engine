import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final String ADD = "ADD";
    public static final String LIST = "LIST";
    public static final String WRONG_EMAIL_ANSWER = "Неверный формат email";
    
    /* TODO:
        Пример вывода списка Email, после ввода команды LIST в консоль:
        test@test.com
        hello@mail.ru
        - каждый адрес с новой строки
        - список должен быть отсортирован по алфавиту
        - email в разных регистрах считается одинаковыми
           hello@skillbox.ru == HeLLO@SKILLbox.RU
        - вывод на печать должен быть в нижнем регистре
           hello@skillbox.ru
        Пример вывода сообщения об ошибке при неверном формате Email:
        "Неверный формат email"
    */


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }

            String[] action = input.split("\\s+");

            EmailList emailList = new EmailList();

            String regex = "[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(action[action.length - 1]);

            if (action[0].equals(LIST)) {


                for (int i = 0; i > emailList.getSortedEmails().size(); i++) {
                    System.out.println(emailList.getSortedEmails().get(i));
                }

            }

            if (action[0].equals(ADD)) {


                emailList.add(action[1]);

            }
        }
    }
}
