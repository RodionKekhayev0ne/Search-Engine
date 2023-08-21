package ru.skillbox;

public class Country {
    private String countryName = "";
    private String capitalCityName = "";
    private String seaExit = "";
    private int population;
    private int square;


    public Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCapitalCityName() {
        return capitalCityName;
    }

    public void setCapitalCityName(String capitalCityName) {
        this.capitalCityName = capitalCityName;
    }

    public String getSeaExit() {
        return seaExit;
    }

    public void setSeaExit(String seaExit) {
        this.seaExit = seaExit;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }









}
