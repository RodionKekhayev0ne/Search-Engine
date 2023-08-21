package ru.skillbox;

public class CPU {

    private final double frequency;
    private final int coreQuantities;
    private final String vendor;
    private final double weight;


    public CPU(double frequency, int coreQuantities, String vendor, double weight) {
        this.frequency = frequency;
        this.coreQuantities = coreQuantities;
        this.vendor = vendor;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }


    @Override
    public String toString() {
        return "CPU " +
                "frequency  " + frequency + " Gz" +
                ", coreQuantities " + coreQuantities +
                ", vendor  " + vendor +
                ", weight " + weight + "g"
                ;
    }


}
