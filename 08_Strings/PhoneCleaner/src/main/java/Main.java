import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            System.out.println(phoneCleanerer(input));
            //TODO:напишите ваш код тут, результат вывести в консоль.
        }
    }

    public static String phoneCleanerer(String number) {

        String regex = "[^0-9]";
        String regex2 = "[7,8][0-9]{10}";
        String startOfNum = "7";


        number = number.replaceAll(regex, "");


        for (int i = 0; i < number.length(); i++) {

            if (Character.isLetter(number.charAt(i))) {

                return "Неверный формат номера ";

            }
        }
        if (number.length() < 10) {

            return "Неверный формат номера ";

        } else if (number.length() > 11) {

            return "Неверный формат номера ";

        } else if (number.length() == 10) {

            number = "7" + number;
            return number;

        }

        if ('8' == number.charAt(0)) {

            number = number.substring(1);
            number = "7" + number;

            return number;

        }

        if (number.matches(regex2)) {
            return number;

        } else if (!number.matches(regex2)) {

            return "Неверный формат номера ";

        } else {

            return "Неверный формат номера ";

        }


    }

}
