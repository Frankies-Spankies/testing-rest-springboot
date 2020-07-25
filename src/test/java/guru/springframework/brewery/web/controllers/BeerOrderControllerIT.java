package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.CustomerRepository;
import guru.springframework.brewery.services.BeerOrderService;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerOrderControllerIT {

        @Autowired
        private TestRestTemplate testRestTemplate;

        @Autowired
        CustomerRepository customerRepository;

        Customer customer;

        @BeforeEach
        void setUp() {
            customer = customerRepository.findAll().get(0);
        }

        @Test
        void testListOrders() {
            String url = "/api/v1/customers/" + customer.getId().toString() + " /orders";

            BeerOrderPagedList pagedList = testRestTemplate.getForObject(url, BeerOrderPagedList.class);

            assertThat(pagedList.getContent()).hasSize(1);
        }
}