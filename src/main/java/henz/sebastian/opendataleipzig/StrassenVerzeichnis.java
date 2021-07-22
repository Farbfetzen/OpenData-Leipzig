package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "STRASSENVERZEICHNIS")
public class StrassenVerzeichnis {

    @JsonProperty("STRASSE")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Strasse> strassen;
}
