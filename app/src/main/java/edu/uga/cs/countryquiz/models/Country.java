package edu.uga.cs.countryquiz.models;

/**
 * Represents a country with an ID, name, and associated continent (POJO).
 */
public class Country {

    private int id;
    private String name;
    private String continent;

    /**
     * Constructs a Country object with the specified ID, name, and continent.
     *
     * @param id        The unique identifier of the country.
     * @param name      The name of the country.
     * @param continent The continent the country belongs to.
     */
    public Country(int id, String name, String continent) {
        this.id = id;
        this.name = name;
        this.continent = continent;
    } // Country

    /**
     * Gets the unique identifier of the country.
     *
     * @return The country ID.
     */
    public int getId() {
        return id;
    } // getId

    /**
     * Gets the name of the country.
     *
     * @return The country name.
     */
    public String getName() {
        return name;
    } // getName

    /**
     * Gets the continent associated with the country.
     *
     * @return The continent name.
     */
    public String getContinent() {
        return continent;
    } // getContinent

} // Country
