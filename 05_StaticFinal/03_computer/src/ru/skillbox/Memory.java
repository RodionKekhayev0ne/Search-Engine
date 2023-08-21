package ru.skillbox;

public class Memory {
    private MemoryType type;
    private final int memoryQuantities;
    private final double weight;


    public Memory(MemoryType type, int memoryQuantities, double weight) {
        this.type = type;
        this.memoryQuantities = memoryQuantities;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Memory " +
                "type " + type +
                ", memoryQuantities " + memoryQuantities + " Gb " +
                ", weight " + weight + "g";
    }
}
