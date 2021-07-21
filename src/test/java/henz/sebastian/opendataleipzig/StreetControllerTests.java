package henz.sebastian.opendataleipzig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StreetControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String path;

    StreetControllerTests(@Value("${server.port}") final int port) {
        path = "http://localhost:" + port + "/streets";
    }

    @Test
    void updateReturnsCorrectStatusAndBody() {
        final ResponseEntity<String> response = restTemplate.postForEntity(path + "/update", null, String.class);
        Assertions.assertAll(
            () -> Assertions.assertEquals(201, response.getStatusCodeValue()),
            () -> Assertions.assertEquals("Updated database with 3054 records.", response.getBody())
        );
    }
}
