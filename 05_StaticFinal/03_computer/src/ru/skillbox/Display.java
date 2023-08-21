package ru.skillbox;

public class Display {
    private final double diagonal;
    private DisplayType type;
    private final double weght;

    public Display(double diagonal, DisplayType type, double weght) {
        this.diagonal = diagonal;
        this.type = type;
        this.weght = weght;
    }

    public double getWeght() {
        return weght;
    }

    @Override
    public String toString() {
        return "Display " +
                "diagonal " + diagonal +
                ", type " + type +
                ", weght " + weght + "g";
    }

}
