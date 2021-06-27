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
    @XmlElement(name = "NAME")
    private String name;
    @Id
    @Column(unique = true, length = 5)
    @XmlElement(name = "SCHLUESSEL")
    private String key;

    public Street() {}

    public Street(final String name, final String key) {
        this.name = name;
        this.key = key;
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

    @Override
    public String toString() {
        return String.format("Straße [Name=%s, Schlüssel=%s]", name, key);
    }
}
