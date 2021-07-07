package henz.sebastian.opendataleipzig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Street {

    @Column(unique = true)
    private String name;
    @Id
    @Column(unique = true, length = 5)
    private String key;
    private int length;
    private int population;

    public Street() {}

    public Street(final String name, final String key) {
        this.name = name;
        this.key = key;
    }

    public Street(final String name,
                  final String key,
                  final int length,
                  final int population) {
        this.name = name;
        this.key = key;
        this.length = length;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(final int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        // return String.format("Straße [Name=%s, Schlüssel=%s]", name, key);
        return "Straße [Name=" + name +
            ", Schlüssel=" + key +
            ", Länge=" + length +
            ", Einwohner=" + population +
            "]";
    }
}
