public class GeometryCalculator {
    // метод должен использовать абсолютное значение radius
    public static double getCircleSquare(double radius) {

        return Math.PI * radius * radius;
    }

    // метод должен использовать абсолютное значение radius
    public static double getSphereVolume(double radius) {
        double c = 4.0 / 3.0;
        return Math.abs(c * Math.PI * radius * radius * radius);
    }

    public static boolean isTrianglePossible(double a, double b, double c) {
        if (a + b > c && b + c > a && c + a > b) {
            return true;
        } else if (a < 0 || b < 0 || c < 0) {
            return false;
        } else {
            return false;
        }
    }

    // перед расчетом площади рекомендуется проверить возможен ли такой треугольник
    // методом isTrianglePossible, если невозможен вернуть -1.0
    public static double getTriangleSquare(double a, double b, double c) {

        if (a + b > c && b + c > a && c + a > b) {
            double p = (a + b + c) / 2.0;
            double square = p * (p - a) * (p - b) * (p - c);
            return Math.sqrt(square);
        } else if (a <= 0 || b <= 0 || c <= 0) {
            return -1.0;
        } else {
            return -1.0;
        }


    }


}

