import javax.security.cert.CertificateParsingException;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        int day = 31;
        int month = 12;
        int year = 2020;

        System.out.println(collectBirthdays(year, month, day));


    }


    public static String collectBirthdays(int year, int month, int day) {
        int age = 0;
        //TODO реализуйте метод для построения строки в следующем виде
        //0 - 31.12.1990 - Mon
        //1 - 31.12.1991 - Tue
        DateTimeFormatter formattor = DateTimeFormatter.ofPattern("").localizedBy(new Locale("us"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" - dd.MM.yyyy - E").localizedBy(new Locale("us"));

        LocalDate birthday = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();
        String date = "";

        if (birthday.isAfter(today)) {
            return "";
        }


        for (; birthday.isBefore(today.plusMonths(5)); birthday = birthday.plusYears(++age)) {

            date += +age + formatter.format(birthday) + System.lineSeparator();

        }


        return date;
    }

}



