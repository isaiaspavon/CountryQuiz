package edu.uga.cs.countryquiz.models;

public class Country {

    private int id;
    private String name;
    private String continent;

    // Constructor
    public Country(int id, String name, String continent) {
        this.id = id;
        this.name = name;
        this.continent = continent;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    // Optionally, you can add setters if needed
}
