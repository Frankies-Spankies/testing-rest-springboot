package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.web.model.BeerPagedList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerIT {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void listBeers() {
        BeerPagedList beerPagedList = testRestTemplate.getForObject("/api/v1/beer", BeerPagedList.class);
        assertEquals(beerPagedList.getContent().size(),3);
    }
}
