import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            //TODO:напишите ваш код тут, результат вывести в консоль.
            //При невалидном ФИО вывести в консоль: Введенная строка не является ФИО
            System.out.println(nameFormatter(input));

        }
    }

    public static String nameFormatter(String fio) {
        String regex = "[^А-Яа-я]";
        fio.replaceAll(regex, " ");
        String[] info = fio.split("\\s");

        if (info.length != 3) {
            return "Введенная строка не является ФИО";
        }

        String fullName = "Фамилия: " + info[0] + System.lineSeparator() + "Имя: " + info[1] + System.lineSeparator() + "Отчество: " + info[2];


        if (info[0] == info[1] || info[1] == info[2] || info[0] == info[2]) {
            return "Введенная строка не является ФИО";
        }

        for (int i = 0; i < fio.length(); i++) {
            if (Character.isDigit(fio.charAt(i))) {

                return "Введенная строка не является ФИО";
            }
        }


        return fullName;
    }
}