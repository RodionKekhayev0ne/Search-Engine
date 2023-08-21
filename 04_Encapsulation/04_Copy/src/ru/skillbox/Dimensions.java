package ru.skillbox;

public class Dimensions {
    private final int hight;
    private final int width;
    private final int lenth;

    public Dimensions(int hight, int width, int lenth) {
        this.hight = hight;
        this.width = width;
        this.lenth = lenth;
    }

    public int getVolume() {
        return hight * width * lenth;
    }

    public Dimensions setHight(int hight) {
        return new Dimensions(hight, width, lenth);
    }

    public Dimensions setWidth(int width) {
        return new Dimensions(hight, width, lenth);
    }

    public Dimensions setLenth(int lenth) {
        return new Dimensions(hight, width, lenth);
    }

    public String toString() {
        return hight + "" + "" + width + "" + "" + lenth;
    }

}
