package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @Embedded
    @NotNull
    @JsonProperty("STAMMDATEN")
    private Stammdaten stammdaten;

    @Embedded
    @JsonProperty("BENENNUNG")
    private Benennung benennung;

    @Embedded
    @JsonProperty("LAGE")
    private Lage lage;

    @Embedded
    @JsonProperty("CHAR")
    private Charakteristika charakteristika;

    @Embedded
    @JsonProperty("ERKLAERUNG")
    private Erklaerung erklaerung;
    
    @Embedded
    @JsonProperty("ERLAEUTERUNGSTAFEL")
    private Erlaeuterungstafel erlaeuterungstafel;

    @JsonProperty("MGLZZHG")
    private String mglzzhg;  // ?

    @Column(columnDefinition = "text")
    @JsonProperty("BEMERKUNG")
    private String bemerkung;  // Allgemeine Bemerkung
    
    @Embedded
    @JsonProperty("AUFHEBUNG")
    private Aufhebung aufhebung;

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
    static class Benennung {

        @JsonProperty("BNR")
        private String bnr;  // Nummer des Benennungsbeschlusses im Stadtrat

        @JsonProperty("BDAT")
        // String instead of LocalDate because for some streets its given as
        // the year without day or month.
        private String bdat;  // Datum des Benennungsbeschlusses im Stadtrat

        @JsonProperty("INKRAFTDAT")
        // String instead of LocalDate because for some streets its given as
        // the year without day or month.
        private String inkraftdat;  // Datum des Inkrafttretens der Benennung

        @JsonProperty("WIDMUNG")
        private String widmung;  // scheinbar ungenutzt
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Lage {

        @JsonProperty("ORTSTEILE")
        private String ortsteile;  // Ortsteile, durch die die Straße verläuft

        @JsonProperty("BEZIRK")
        private String bezirk;  // Stadtbezirke, durch die Straße verläuft
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
        private Integer firmen;  // Anzahl der IHK-Unternehmen, die in der Straße gemeldet sind

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
        private String realbezug;  // scheinbar ungenutzt
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Erklaerung {

        @Type(type = "text")
        @JsonProperty("ERLAEUTERUNG")
        private String erlaeuterung;  // Erläuterung des Straßennamens

        @Type(type = "text")
        @JsonProperty("ALTNAMEN")
        private String altnamen;  // Frühere Namen der Straße (bei umbenannten Straßen)
        
        @JsonProperty("PBEZUG")
        private String pbezug;  // Personenbezug (wird nicht gepflegt)
        
        @JsonProperty("RBEZUG")
        private String rbezug;  // Raumbezug (wird nicht gepflegt)
        
        @JsonProperty("NKOMPLEX")
        private String nkomplex;  // Namenskomplex
        
        @JsonProperty("MOTIVGRP")
        private String motivgrp;  // Motivgruppe (wird nicht gepflegt)
        
        @JsonProperty("QUELLEN")
        private String quellen;  // Quellen (wird nicht gepflegt)

        @Type(type = "text")
        @JsonProperty("LITERATUR")
        private String literatur;  // Literatur (wird nicht gepflegt)
    }
    
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Erlaeuterungstafel {
        
        @JsonProperty("ETTEXT")
        private String ettext;  // Text auf einer gegebenenfalls vorhandenen Erläuterungstafel
        
        @JsonProperty("ETSTANDORT")
        private String etstandort;  // Standort einer gegebenenfalls vorhandenen Erläuterungstafel
        
        @JsonProperty("ETDATUM")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate etdatum;  // Datum der Anbringung einer gegebenenfalls vorhandenen Erläuterungstafel
        
        @JsonProperty("ETANMERKUNG")
        private String etanmerkung;  // Anmerkung zu einer gegebenenfalls vorhandenen Erläuterungstafel
    }
    
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Aufhebung {
        
        @JsonProperty("AUFGEHOBEN")
        private String aufgehoben;  // Status der Straße
        
        @JsonProperty("AHBNR")
        private String ahbnr;  // Nummer des Beschlusses zur Aufhebung des Straßennamens im Stadtrat
        
        @JsonProperty("AHBDAT")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate ahbdat;  // Datum des Beschlusses zur Aufhebung des Straßennamens im Stadtrat
        
        @JsonProperty("AHBGRUND")
        private String ahbgrund;  // Begründung der Aufhebung des Straßennamens
        
        @JsonProperty("AHINKRAFTDAT")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate ahinkraftdat;  // Datum des Inkrafttretens der Aufhebung des Straßennamens
    }
}
