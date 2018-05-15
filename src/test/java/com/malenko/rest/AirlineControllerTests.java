package com.malenko.rest;

import com.malenko.rest.model.Airline;
import com.malenko.rest.repository.AircraftRepository;
import com.malenko.rest.repository.AirlineRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AirlineControllerTests {
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private MockMvc mockMvc;

    private Long airlineId;

    @Before
    public void prepare() {
        aircraftRepository.deleteAll();
        airlineRepository.deleteAll();

        Airline airline = new Airline();
        airline.setName("Happy Sky");
        airline = airlineRepository.save(airline);
        airlineId = airline.getId();
    }

    @Test
    public void shouldRead() throws Exception {
        mockMvc.perform(get("/api/v1/airlines/" + airlineId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.content.name", is("Happy Sky")));
    }
    @Test
    public void shouldRead2() throws Exception {
        mockMvc.perform(get("/api/v1/airlines/" + 777))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(404)));
    }
    @Test
    public void shouldDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/airlines/" + airlineId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.content.name", is("Happy Sky")));
        mockMvc.perform(delete("/api/v1/airlines/" + airlineId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(404)));
    }
}
