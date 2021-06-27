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

    public Street() {}

    public Street(final String name, final String key) {
        this.name = name;
        this.key = key;
    }

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

    @Override
    public String toString() {
        return String.format("Straße [Name=%s, Schlüssel=%s]", name, key);
    }
}
