package henz.sebastian.opendataleipzig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "STRASSE")
public class Street {

    @Column(unique = true)
    private String name;
    @Id
    @Column(unique = true, length = 5)
    private String key;
    private int length;
    private int population;

    public String getName() {
        return name;
    }

    @XmlElement(name = "NAME")
    public void setName(final String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    @XmlElement(name = "SCHLUESSEL")
    public void setKey(final String key) {
        this.key = key;
    }

    public int getLength() {
        return length;
    }

    @XmlElement(name = "LAENGE")
    public void setLength(final int length) {
        this.length = length;
    }

    public int getPopulation() {
        return population;
    }

    @XmlElement(name = "EINWOHNER")
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
