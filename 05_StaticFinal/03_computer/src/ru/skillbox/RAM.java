package ru.skillbox;

public class RAM {

    private RAMtype type;
    private final int RAMQuantities;
    private final double weight;

    public RAM(RAMtype type, int RANQuantities, double weight) {
        this.type = type;
        this.RAMQuantities = RANQuantities;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "RAM " +
                "type " + type +
                ", RAM quantities " + RAMQuantities + " Gb " +
                ", weight " + weight + "g";
    }
}
