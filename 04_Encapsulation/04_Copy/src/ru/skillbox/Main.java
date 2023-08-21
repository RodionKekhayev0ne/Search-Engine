package ru.skillbox;

public class Main {

    public static void main(String[] args) {

        Dimensions dimensions = new Dimensions(4, 5, 6);
        Cargo cargo = new Cargo(222, "ul Pushkina", "Yes", "xxxxxxx", "Yes", dimensions);

        cargo.changeAdress("yyyyyy");
        dimensions.setLenth(4444);
        System.out.println(cargo);
    }
}
