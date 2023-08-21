package ru.skillbox;


public class ArithmeticCalculator{

    private final int firstNum;
    private final int secondNum;
    private Operation type;

    public ArithmeticCalculator(int firstNum, int secondNum) {
        this.firstNum = firstNum;
        this.secondNum = secondNum;
    }

    public int Calculate(Operation type) {
        if (type == Operation.ADD) {
            return firstNum + secondNum;
        } else if (type == Operation.SUBTRACT) {
            return firstNum - secondNum;
        } else {
            return firstNum * secondNum;
        }
    }
}