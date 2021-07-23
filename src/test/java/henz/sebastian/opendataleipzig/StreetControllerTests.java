package henz.sebastian.opendataleipzig;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StreetControllerTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url;

    StreetControllerTests(@Value("${server.port}") final int port) {
        url = "http://localhost:" + port + "/streets";
    }

    @Test
    void updateReturnsCorrectStatusAndBody() {
        final ResponseEntity<String> response = testRestTemplate.postForEntity(
            url + "/update",
            null,
            String.class
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(201, response.getStatusCodeValue()),
            () -> Assertions.assertEquals("Updated database with 3054 records.", response.getBody())
        );
    }

    @Test
    void getAllReturnsCorrectNumberOfStreets() throws Exception {
        final Strasse[] streets = testRestTemplate.getForObject(url + "/all", Strasse[].class);
        Assertions.assertEquals(3054, streets.length);
    }
}
