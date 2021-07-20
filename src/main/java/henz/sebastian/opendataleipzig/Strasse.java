package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Strasse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @Embedded
    @JacksonXmlProperty(localName = "STAMMDATEN")
    private Stammdaten stammdaten;

    @Embedded
    @JacksonXmlProperty(localName = "CHAR")
    private Charakteristika charakteristika;

    @JacksonXmlProperty(localName = "MGLZZHG")
    private String mglzzhg;  // ?

    @Column(columnDefinition = "text")
    @JacksonXmlProperty(localName = "BEMERKUNG")
    private String bemerkung;  // Allgemeine Bemerkung
}


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class Stammdaten {

    @JacksonXmlProperty(localName = "NAME")
    private String name;  // Straßenname

    @Column(length = 5)
    @JacksonXmlProperty(localName = "SCHLUESSEL")
    private String schluessel;  // Straßenschlüssel
}


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Charakteristika {

    @JacksonXmlProperty(localName = "LAENGE")
    private Integer laenge;  // Länge der Straße in Metern

    @JacksonXmlProperty(localName = "ADR")
    private Integer adr;  // Anzahl der amtlich vergebenen Adressen in der Straße

    @JacksonXmlProperty(localName = "ADRSTAND")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate adrstand; // Zeitbezug zur Anzahl der Adressen

    @JacksonXmlProperty(localName = "EINWOHNER")
    private Integer einwohner;  // Anzahl der Einwohner, die in der Straße wohnhaft gemeldet sind

    @JacksonXmlProperty(localName = "EINWOHNERSTAND")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate einwohnerstand;  // Zeitbezug zur Anzahl der Einwohner

    @JacksonXmlProperty(localName = "FIRMEN")
    private Integer firmen;  // Anhzahl der IHK-Unternehmen, die in der Straße gemeldet sind

    @JacksonXmlProperty(localName = "FIRMENSTAND")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate firmenstand;  // Zeitbezug zur Anzahl der Unternehmen

    @JacksonXmlProperty(localName = "GEWERBE")
    private Integer gewerbe;  // Anzahl der Handwerksbetriebe, die in der Straße gemeldet sind

    @JacksonXmlProperty(localName = "GEWERBESTAND")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate gewerbestand;  // Zeitbezug zur Anzahl der Handwerksbetriebe

    @JacksonXmlProperty(localName = "VERWEISE")
    private String verweise;  // ?

    @JacksonXmlProperty(localName = "CHARACTER")
    private String character;  // Beschaffenheit, z.B. "Gemeindestraße - Anliegerstraße; Breite 5 m"

    @JacksonXmlProperty(localName = "REALBEZUG")
    private String realbezug;  // scheinbar ungenutzt?
}
