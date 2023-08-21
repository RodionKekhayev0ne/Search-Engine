import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {


        Airport airport = Airport.getInstance();

        findPlanesLeavingInTheNextTwoHours(airport).stream().forEach(System.out::println);

    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        //TODO Метод должден вернуть список рейсов вылетающих в ближайшие два часа.

        List<Terminal> terminals = airport.getTerminals();

        int index = 1;
        LocalDateTime nowTime = LocalDateTime.now();
        int year = nowTime.getYear();
        int mounth = nowTime.getMonthValue();
        int day = nowTime.getDayOfMonth();
        int hour = nowTime.getHour();

        String dateString = "0" + day + "/0" + mounth + "/" + year + " " + hour + ":59:00";
        String dateAfterString = "0" + day + "/0" + mounth + "/" + year + " " + (hour + 3) + ":00:0";

        SimpleDateFormat timeForm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        ParsePosition parsePosition = new ParsePosition(index);
        ParsePosition parsePosition2 = new ParsePosition(index);

        Date compareDate = timeForm.parse(dateString, parsePosition);
        Date compareAfterDate = timeForm.parse(dateAfterString, parsePosition2);

        return terminals.stream().map(terminal -> terminal.getFlights())
                .flatMap(flights -> flights.stream())
                .filter(flight -> flight.getDate().after(compareDate))
                .filter(flight -> flight.getDate().before(compareAfterDate))
                .filter(flight -> flight.getType().equals(Flight.Type.DEPARTURE))
                .collect(Collectors.toList());

    }

}