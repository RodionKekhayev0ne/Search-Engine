package ru.skillbox;

public class Cargo {
    private final int weight;
    private final String adessName;
    private final String canTurn;
    private final String registrationNum;
    private final String isFragile;
    private final Dimensions dimensions;

    public Cargo(int weight, String adessName, String canTurn, String registrationNum, String isFragile, Dimensions dimensions) {
        this.weight = weight;
        this.adessName = adessName;
        this.canTurn = canTurn;
        this.registrationNum = registrationNum;
        this.isFragile = isFragile;
        this.dimensions = dimensions;
    }

    public Cargo changeAdress(String adressName) {
        return new Cargo(weight, adressName, canTurn, registrationNum, isFragile, dimensions);

    }

    public Cargo changeWeight(int weight) {
        return new Cargo(weight, adessName, canTurn, registrationNum, isFragile, dimensions);
    }


    public String toString() {
        return
                weight + "" + "" + adessName + "" + "" + canTurn + "" + "" + registrationNum + "" + "" + isFragile + "" + "" + dimensions;
    }

}


