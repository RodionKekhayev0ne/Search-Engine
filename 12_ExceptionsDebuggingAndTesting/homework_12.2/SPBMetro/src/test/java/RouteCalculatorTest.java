import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


public class RouteCalculatorTest extends TestCase {

    private List<Station> route = new ArrayList<>();
    private List<Station> conections = new ArrayList();
    private StationIndex stationIndex = new StationIndex();
    private RouteCalculator calculator = new RouteCalculator(stationIndex);

    @Override
    protected void setUp() throws Exception {

        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");


        route.add(new Station("Площадь Восстания", line1));
        route.add(new Station("Владимирская", line1));
        route.add(new Station("Достоевская", line2));
        route.add(new Station("Пушкинская", line2));

        line1.addStation(route.get(0));
        line1.addStation(route.get(1));
        line2.addStation(route.get(2));


        conections.add(route.get(1));
        conections.add(route.get(2));
        conections.add(route.get(3));
        stationIndex.addConnection(conections);
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addStation(route.get(0));
        stationIndex.addStation(route.get(1));
        stationIndex.addStation(route.get(2));


    }

    public void testShortestRoute() {

        List<Station> actual = calculator.getShortestRoute(route.get(0), route.get(2));
        List<Station> excepted = new ArrayList<>();
        excepted.add(route.get(0));
        excepted.add(route.get(1));
        excepted.add(route.get(2));
        assertEquals(excepted, actual);

    }

    public void testCalculateDuration() {

        double actual = RouteCalculator.calculateDuration(route);
        double exepted = 8.5;
        assertEquals(exepted, actual);

    }

    public void testRouteOnTheLine() {

        List<Station> actual = calculator.getShortestRoute(route.get(0), route.get(1));
        List<Station> excepted = new ArrayList<>();
        excepted.add(route.get(0));
        excepted.add(route.get(1));
        assertEquals(excepted, actual);

    }

    public void testRouteWithOneConnection() {

        List<Station> actual = calculator.getShortestRoute(route.get(0), route.get(2));
        List<Station> excepted = new ArrayList<>();
        excepted.add(route.get(0));
        excepted.add(route.get(1));
        excepted.add(route.get(2));
        assertEquals(excepted, actual);

    }

    public void testRouteWithTwoConnections() {

        List<Station> excepted = new ArrayList<>();
        excepted.add(route.get(1));
        excepted.add(route.get(2));
        List<Station> actual = calculator.getShortestRoute(route.get(1), route.get(3));
        assertEquals(excepted, actual);

    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
//[Площадь Восстания, Владимирская, Достоевская]