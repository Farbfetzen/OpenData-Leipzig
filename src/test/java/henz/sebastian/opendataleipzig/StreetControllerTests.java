package henz.sebastian.opendataleipzig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StreetControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String path = "http://localhost:%d/streets%s";

    @Test
    void updateReturnsCorrectStatusAndBody() {
        final ResponseEntity<String> response = restTemplate.postForEntity(
            String.format(path, port, "/update"),
            null,
            String.class
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(201, response.getStatusCodeValue()),
            () -> Assertions.assertEquals("Updated database with 3054 records.", response.getBody())
        );
    }
}
