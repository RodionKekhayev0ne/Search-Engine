import java.util.Scanner;

public class Main {

    static final int BOX_IN_CONTAINER = 27;
    static final int CONTAINER_IN_TRUCK = 12;
    static final int BOX_IN_TRUCK = BOX_IN_CONTAINER * CONTAINER_IN_TRUCK;

    static final String EXIT_CODE = "exit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String boxCountInput = scanner.nextLine();

            if (boxCountInput.equals(EXIT_CODE)) {
                return;
            }

            int boxCount = Integer.parseInt(boxCountInput);

            int containerCount = 0;
            int truckCount = 0;

            int i = 0;
            while (i < boxCount) {
                if (i % BOX_IN_TRUCK == 0) {
                    System.out.println("Грузовик: " + ++truckCount);
                }
                if (i % BOX_IN_CONTAINER == 0) {
                    System.out.println("\tКонтейнер: " + ++containerCount);
                }
                System.out.println("\t\tЯщик: " + ++i);
            }

            System.out.printf("Необходимо:%nгрузовиков - %d шт.%nконтейнеров - %d шт.%n",
                    truckCount, containerCount);
        }

        // TODO: вывести в консоль коробки разложенные по грузовикам и контейнерам
        // пример вывода при вводе 2
        // для отступа используйте табуляцию - \t

        /*
        Грузовик: 1
            Контейнер: 1
                Ящик: 1
                Ящик: 2
        Необходимо:
        грузовиков - 1 шт.
        контейнеров - 1 шт.
        */
    }

}
