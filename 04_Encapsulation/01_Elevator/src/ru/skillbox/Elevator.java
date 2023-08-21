package ru.skillbox;

public class Elevator {
    private int currentFloor = 1;
    private int maxFloor;
    private int minFloor;

    Elevator(int minFloor, int maxFloor) {
        this.maxFloor = maxFloor;
        this.minFloor = minFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void moveUp() {
        if (currentFloor >= maxFloor) {
            System.out.println("ERROR!!!: Превышено максимальное значение этажа ");
            return;
        } else {
            currentFloor++;
            System.out.println(currentFloor);
        }
    }

    public void moveDown() {
        if (currentFloor <= minFloor) {
            System.out.println("ERROR!!!: Этаж меньше минимального значения");
        } else {
            currentFloor--;
            System.out.println(currentFloor);
        }
    }

    public void move(int floor) {
        if (floor >= minFloor && floor <= maxFloor) {
            for (int i = currentFloor; i <= floor; i++) {
                System.out.println(i);
            }
        } else {
            System.out.println("ERROR!!!: Недопустимое значение");
        }
    }

}
