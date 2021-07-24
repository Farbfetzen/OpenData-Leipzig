package henz.sebastian.opendataleipzig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/streets")
public class StreetController {

    @Autowired
    private StreetRepository streetRepository;

    @Autowired
    private StreetService streetService;

    @PostMapping(path = "/update")
    public ResponseEntity<String> update() {
        final long numberOfRecords = streetService.updateStreetData();
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

    @GetMapping(path = "/all")
    public List<Street> getAllStreets() {
        return streetRepository.findAll();
    }

    @GetMapping(path = "/sortby")
    Page<Street> getStreets(@RequestParam final String param,
                            @RequestParam(defaultValue = "0") final int page,
                            @RequestParam(defaultValue = "true") final boolean desc) {
        final Pageable pageable = PageRequest.of(
            page,
            10,
            desc ? Sort.Direction.DESC : Sort.Direction.ASC,
            param
        );
        return streetRepository.findAll(pageable);
    }
}
