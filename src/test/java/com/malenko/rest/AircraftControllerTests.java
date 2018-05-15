package com.malenko.rest;

import com.malenko.rest.model.Aircraft;
import com.malenko.rest.model.AircraftType;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AircraftControllerTests {
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

        aircraftRepository.save(new Aircraft("Plane 1", 100, 200, 1000, 1000, AircraftType.PLAIN, airline));
        aircraftRepository.save(new Aircraft("Plane 2", 50, 100, 500, 300, AircraftType.PLAIN, airline));
        aircraftRepository.save(new Aircraft("Helicopter 1", 6, 50, 300, 100, AircraftType.HELICOPTER, airline));
    }

    @Test
    public void shouldCalculateTotals() throws Exception {
        mockMvc.perform(get("/api/v1/airlines/" + airlineId + "/aircrafts/capacity"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.totalSeatingCapacity", is(156)))
                .andExpect(jsonPath("$.content.totalCarryingCapacity", is(350)));
    }
    @Test
    public void shouldListAircrafts() throws Exception {
        mockMvc.perform(get("/api/v1/airlines/" + airlineId + "/aircrafts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].name", is("Helicopter 1")))
                .andExpect(jsonPath("$.content[1].name", is("Plane 2")))
                .andExpect(jsonPath("$.content[2].name", is("Plane 1")));
    }
    @Test
    public void shouldFilterByFuel() throws Exception {
        mockMvc.perform(get("/api/v1/aircrafts/fuel?from=50&to=400"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Plane 2")))
                .andExpect(jsonPath("$.content[1].name", is("Helicopter 1")));
    }
    @Test
    public void shouldFilterByCapacityAndRange() throws Exception {
        mockMvc.perform(get("/api/v1/aircrafts/range?seatingCapacity=10&flightRange=600"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Plane 1")));
    }
}
