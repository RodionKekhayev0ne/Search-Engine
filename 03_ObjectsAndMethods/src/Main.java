public class Main {

    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Milk", 40);
        basket.print("Milk");
        Basket basket2 = new Basket();
        basket2.add("Cola", 70);
        System.out.println(Basket.getTotalPrice());
        System.out.println(Basket.getCount());
        System.out.println(Basket.midBasketPrice());
        System.out.println(Basket.midItemsPrice());

        //   Каждый метод класса Arithmetic можно вызвать по отдельности или воспользоваться универсальным методом printAll

        Arithmetic arithmetic = new Arithmetic(1, 2);
        arithmetic.mid();

        Printer printer = new Printer();

        printer.append("I love anime");
        printer.append("I love games", "games");
        printer.append("I love catgirls", "catgirls", 123);
        printer.append("I love eating", "food", 223, 5);

        //   Второй метод print для проверки очистки в конце выполнения метода

        printer.print();
        printer.print();

        // задание 05_Static 02_enum  проверка выполнения
        ArithmeticCalculator r = new ArithmeticCalculator(3, 4);
        System.out.println(r.Calculate(Operation.ADD));
        System.out.println(r.Calculate(Operation.SUBTRACT));
        System.out.println(r.Calculate(Operation.MULTIPLY));
    }
}
