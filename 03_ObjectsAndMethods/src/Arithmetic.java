public class Arithmetic {

    private int firstNum;
    private int secondNum;


    public Arithmetic(int firstNum, int secondNum) {
        this.firstNum = firstNum;
        this.secondNum = secondNum;
    }

    public int sum() {
        return firstNum + secondNum;
    }

    public int raz() {
        return firstNum - secondNum;
    }

    public int multiply() {
        return firstNum * secondNum;
    }

    public double mid() {

        return (firstNum + secondNum) / 2.0;

    }

    public int max() {
        return firstNum > secondNum ? firstNum : secondNum;
    }

    public int min() {
        return firstNum < secondNum ? firstNum : secondNum;
    }
}




