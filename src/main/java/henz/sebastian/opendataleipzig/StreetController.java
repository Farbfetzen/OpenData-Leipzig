package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/streets")
public class StreetController {

    @Autowired
    private StreetRepository streetRepository;

    @PostMapping(path = "/update")
    public @ResponseBody ResponseEntity<String> update() {
        final long numberOfRecords = updateStreetData();
        if (numberOfRecords > 0) {
            return new ResponseEntity<>(
                "Updated database with " + numberOfRecords + " records.",
                HttpStatus.CREATED
            );
        }
        return new ResponseEntity<>(
            "Failed to update database.",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping(path = "/getall")
    public @ResponseBody List<Street> getAllStreets() {
        return streetRepository.findAll();
    }

    @PostMapping(path = "/add")
    public @ResponseBody Street addStreet(
            @RequestParam final String name,
            @RequestParam final String key) {
        final Street street = new Street();
        street.setName(name);
        street.setKey(key);
        return streetRepository.save(street);
    }

    /** Delete all streets entries and fill the database with fresh data.
     *
     * @return The number of created records or 0 if it failed.
     */
    private long updateStreetData() {
        // Populate the database from an xml file. This is done
        // for development purposes. Later it will download the data
        // from the web.
        // The data is parsed sequentially. This is probably inefficient
        // and should be done with a batch method.
        // TODO: Learn how to do batch xml parsing.
        // Currently, the XML annotations in the Street class are unnecessary.

        streetRepository.deleteAllInBatch();

        final File file = new File("data/Strassenverzeichnis.xml");
        try {
            final XmlMapper xmlMapper = new XmlMapper();
            final JsonNode streetArrayNode = xmlMapper.readTree(file).get("STRASSE");
            for (final JsonNode streetNode : streetArrayNode) {
                final Street street = new Street();
                street.setName(streetNode.get("STAMMDATEN").get("NAME").asText());
                street.setKey(streetNode.get("STAMMDATEN").get("SCHLUESSEL").asText());
                street.setLength(streetNode.get("CHAR").get("LAENGE").asInt());
                street.setPopulation(streetNode.get("CHAR").get("EINWOHNER").asInt());
                streetRepository.save(street);
                // System.out.println(street);
            }
        } catch (final IOException e) {
            return 0L;
        }
        return streetRepository.count();
    }

}
