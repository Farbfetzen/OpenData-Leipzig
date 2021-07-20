package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/streets")
public class StreetController {

    @Autowired
    private StreetRepository streetRepository;

    @PostMapping(path = "/update")
    public ResponseEntity<String> update() {
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
    public List<Street> getAllStreets() {
        return streetRepository.findAll();
    }

    @PostMapping(path = "/add")
    public Street addStreet(@RequestBody @Valid final Street street) {
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

        streetRepository.deleteAllInBatch();

        final List<Street> streets = new ArrayList<>();
        final File file = new File("data/Strassenverzeichnis.xml");
        try {
            final XmlMapper xmlMapper = new XmlMapper();
            final JsonNode streetArrayNode = xmlMapper.readTree(file).get("STRASSE");
            for (final JsonNode streetNode : streetArrayNode) {
                final JsonNode stammdatenNode = streetNode.get("STAMMDATEN");
                final JsonNode charNode = streetNode.get("CHAR");
                streets.add(new Street(
                    stammdatenNode.get("NAME").asText(),
                    stammdatenNode.get("SCHLUESSEL").asText(),
                    charNode.get("LAENGE").asInt(),
                    charNode.get("EINWOHNER").asInt()
                ));
            }
        } catch (final IOException e) {
            return 0L;
        }

        streetRepository.saveAll(streets);
        return streetRepository.count();
    }

}
