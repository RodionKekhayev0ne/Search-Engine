import javax.swing.text.DateFormatter;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main {

  public static void main(String[] args) {

    LocalDate birthdayOracle = LocalDate.of(1977,6,16);
    System.out.println( getPeriodFromBirthday(birthdayOracle));

  }



  private static String getPeriodFromBirthday(LocalDate birthday) {


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" y 'years' M 'months'  d 'days'");
    LocalDate now = LocalDate.now();
    Long period =   birthday.toEpochDay() - now.toEpochDay() ;

    LocalDate periodDate = LocalDate.ofEpochDay(period);

    return formatter.format(periodDate);
  }

}
