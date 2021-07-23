package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @Embedded
    @NotNull
    @JsonProperty("STAMMDATEN")
    private Stammdaten stammdaten;

    @Embedded
    @JsonProperty("CHAR")
    private Charakteristika charakteristika;

    @JsonProperty("MGLZZHG")
    private String mglzzhg;  // Keine Ahnung, was das sein soll.

    @Column(columnDefinition = "text")
    @JsonProperty("BEMERKUNG")
    private String bemerkung;  // Allgemeine Bemerkung

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Stammdaten {

        @NotNull
        @NotBlank
        @JsonProperty("NAME")
        private String name;  // Straßenname

        @NotNull
        @NotBlank
        @Column(length = 5, unique = true)
        @JsonProperty("SCHLUESSEL")
        private String schluessel;  // Straßenschlüssel
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Charakteristika {

        @JsonProperty("LAENGE")
        private Integer laenge;  // Länge der Straße in Metern

        @JsonProperty("ADR")
        private Integer adr;  // Anzahl der amtlich vergebenen Adressen in der Straße

        @JsonProperty("ADRSTAND")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate adrstand; // Zeitbezug zur Anzahl der Adressen

        @JsonProperty("EINWOHNER")
        private Integer einwohner;  // Anzahl der Einwohner, die in der Straße wohnhaft gemeldet sind

        @JsonProperty("EINWOHNERSTAND")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate einwohnerstand;  // Zeitbezug zur Anzahl der Einwohner

        @JsonProperty("FIRMEN")
        private Integer firmen;  // Anhzahl der IHK-Unternehmen, die in der Straße gemeldet sind

        @JsonProperty("FIRMENSTAND")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate firmenstand;  // Zeitbezug zur Anzahl der Unternehmen

        @JsonProperty("GEWERBE")
        private Integer gewerbe;  // Anzahl der Handwerksbetriebe, die in der Straße gemeldet sind

        @JsonProperty("GEWERBESTAND")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate gewerbestand;  // Zeitbezug zur Anzahl der Handwerksbetriebe

        @JsonProperty("VERWEISE")
        private String verweise;  // ?

        @JsonProperty("CHARACTER")
        private String character;  // Beschaffenheit, z.B. "Gemeindestraße - Anliegerstraße; Breite 5 m"

        @JsonProperty("REALBEZUG")
        private String realbezug;  // scheinbar ungenutzt?
    }
}
