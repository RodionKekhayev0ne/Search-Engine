import java.util.Scanner;

public class Main {

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        for (; ; ) {
            try {
                System.out.println("Введите путь к папке: ");
                String path = scanner.nextLine();

                long size = FileUtils.calculateFolderSize(path);

                if (size / 1048576 > 0) {
                    long mbSize = size / 1048576;
                    if (mbSize / 1024 > 0) {
                        double gbSize = mbSize / 1024;
                        System.out.println("Размер папки " + path + " : " + gbSize + " Гигабайт");
                    } else {
                        System.out.println("Размер папки " + path + " : " + mbSize + " Мегабайт");
                    }
                } else {
                    System.out.println("Размер папки " + path + " : " + FileUtils.calculateFolderSize(path) + " байт");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
}
