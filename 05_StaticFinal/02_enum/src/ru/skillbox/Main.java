package ru.skillbox;

public class Main {

    public static void main(String[] args) {


        ArithmeticCalculator r = new ArithmeticCalculator(3, 4);
        System.out.println(r.Calculate(Operation.ADD));
        System.out.println(r.Calculate(Operation.SUBTRACT));
        System.out.println(r.Calculate(Operation.MULTIPLY));

    }
}
