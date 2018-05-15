package com.malenko.rest.controller.v1;

import com.malenko.rest.model.Aircraft;
import com.malenko.rest.model.CapacityDto;
import com.malenko.rest.model.Response;
import com.malenko.rest.repository.AircraftRepository;
import com.malenko.rest.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.malenko.rest.model.Response.NotFound;
import static com.malenko.rest.model.Response.Ok;

@RestController
@RequestMapping(path = "/api/v1")
public class AircraftController {

  @Autowired
  private AircraftRepository aircraftRepository;
  @Autowired
  private AirlineRepository airlineRepository;

  @PostMapping(path = "/airlines/{airlineId}/aircrafts")
  public Response<Aircraft> create(@PathVariable Long airlineId, @RequestBody Aircraft aircraft) {
    return airlineRepository.findById(airlineId)
      .map(airline -> {
        aircraft.setAirline(airline);
        return Ok(aircraftRepository.save(aircraft));
      })
      .orElse(NotFound("Airline with id " + airlineId + " not found."));
  }

  @GetMapping("/airlines/{airlineId}/aircrafts/capacity")
  public Response<CapacityDto> capacity(@PathVariable Long airlineId) {
    Integer totalSeatingCapacity = aircraftRepository.findByAirlineId(airlineId).stream()
      .mapToInt(Aircraft::getSeatingCapacity)
      .sum();
    Integer totalCarryingCapacity = aircraftRepository.findByAirlineId(airlineId).stream()
      .mapToInt(Aircraft::getCarryingCapacity)
      .sum();
    return Ok(new CapacityDto(totalSeatingCapacity, totalCarryingCapacity));
  }

  @GetMapping(path = "/airlines/{airlineId}/aircrafts")
  public Response<List<Aircraft>> listByAirlineSortedByFlightRange(@PathVariable Long airlineId) {
    List<Aircraft> list = aircraftRepository.findByAirlineId(airlineId)
      .stream()
      .sorted(Comparator.comparing(Aircraft::getFlightRange))
      .collect(Collectors.toList());
    return Ok(list);
  }

  @GetMapping("/aircrafts/fuel")
  public Response<List<Aircraft>> fuel(@RequestParam Integer from, @RequestParam Integer to) {
    List<Aircraft> list = aircraftRepository.findByFuelConsumptionGreaterThanEqualAndFuelConsumptionLessThan(from, to);
    return Ok(list);
  }

  @GetMapping("/aircrafts/range")
  public Response<List<Aircraft>> listByCapacityAndRange(@RequestParam Integer seatingCapacity, @RequestParam Integer flightRange) {
    List<Aircraft> list = aircraftRepository.findBySeatingCapacityGreaterThanEqualAndFlightRangeGreaterThanEqual(
      seatingCapacity,
      flightRange
    );
    return Ok(list);
  }
}
