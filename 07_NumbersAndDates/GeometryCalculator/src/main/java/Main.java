public class Main {

    public static void main(String[] args) {
        GeometryCalculator geometryCalculator = new GeometryCalculator();
        System.out.println(geometryCalculator.getCircleSquare(20));
        System.out.println(geometryCalculator.getCircleSquare(20.2));
        System.out.println(geometryCalculator.getCircleSquare(-20));
        System.out.println(geometryCalculator.getSphereVolume(20));
        System.out.println(geometryCalculator.getSphereVolume(20.2));
        System.out.println(geometryCalculator.getSphereVolume(-20));
        System.out.println(geometryCalculator.isTrianglePossible(-6, 5, 7));
        System.out.println(geometryCalculator.isTrianglePossible(6, 5, 7));
        System.out.println(geometryCalculator.getTriangleSquare(1.5, 2, 3));
        System.out.println(geometryCalculator.getTriangleSquare(3, 0, 5));
        System.out.println(geometryCalculator.getTriangleSquare(1, 20, 40));
        System.out.println(geometryCalculator.getTriangleSquare(0, 0, 0));
        System.out.println(geometryCalculator.getTriangleSquare(-3, 4, 5));
        System.out.println(geometryCalculator.getTriangleSquare(1.5, 2.6, 3.7));
        System.out.println(geometryCalculator.getTriangleSquare(3, 4, 5));
        System.out.println(geometryCalculator.getTriangleSquare(-1, -20, -40));


    }

}