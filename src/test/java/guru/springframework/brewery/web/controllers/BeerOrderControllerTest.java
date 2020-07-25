package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.services.BeerOrderService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext
@WebMvcTest(BeerOrderController.class)
class BeerOrderControllerTest {

    @MockBean
    BeerOrderService beerOrderService;

    @Autowired
    MockMvc mockMvc;

    BeerDto validBeer;
    BeerOrderDto beerOrderDto;
    BeerOrderPagedList beerOrderPagedList;
    List<BeerOrderDto> beerOrderDtoList;


    public final String REQUEST_PATH= "/api/v1/customers/"+UUID.randomUUID()+"/";


    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("XXLagger")
                .upc(222222222222L)
                .price(new BigDecimal("9.80"))
                .build();

        beerOrderDto = BeerOrderDto.builder()
                .id(UUID.randomUUID())
                .customerRef("uno")
                .build();

        beerOrderDtoList = List.of(beerOrderDto);

        beerOrderPagedList = new BeerOrderPagedList(beerOrderDtoList, PageRequest.of(1,1),1l );


    }

    @Test
    void listOrders() throws Exception {
        given(beerOrderService.listOrders(any(),any())).willReturn(beerOrderPagedList);
        MvcResult mvcResult = mockMvc.perform(get(REQUEST_PATH + "orders")
                .param("pageNumber", "0")
                .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getOrder() throws Exception {
        given(beerOrderService.getOrderById(any(),any())).willReturn(beerOrderDto);
        mockMvc.perform(get(REQUEST_PATH+"/orders/"+UUID.randomUUID()))
                .andExpect(status().isOk());

    }

    @Test
    void pickupOrder() throws Exception {
        mockMvc.perform(put(REQUEST_PATH+"/orders/"+UUID.randomUUID()+"/pickup"))
                .andExpect(status().is2xxSuccessful());
    }
}