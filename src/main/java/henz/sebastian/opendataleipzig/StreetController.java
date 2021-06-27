package henz.sebastian.opendataleipzig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/streets")
public class StreetController {

    @Autowired
    private StreetRepository streetRepository;

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.OK)
    public void update() {}

    @GetMapping(path = "/getall")
    @ResponseBody
    public List<Street> getAllStreets() {
        return streetRepository.findAll();
    }

    @PostMapping(path = "/add")
    @ResponseBody
    public Street addStreet(@RequestParam final String name, @RequestParam final String key) {
        return streetRepository.save(new Street(name, key));
    }

}
