package ru.skillbox;

public class Keybord {
    private final KeyCapType type;
    private KeyLights keyLight;
    private final double weight;


    public Keybord(KeyCapType type, KeyLights keyLight, double weight) {
        this.type = type;
        this.keyLight = keyLight;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Keybord " +
                "type " + type +
                ", keyLight " + keyLight +
                ", weight " + weight + "g";
    }
}
