package com.malenko.rest.repository;

import com.malenko.rest.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

  List<Aircraft> findByAirlineId(Long airlineId);

  List<Aircraft> findByFuelConsumptionGreaterThanEqualAndFuelConsumptionLessThan(
    Integer from,
    Integer to
  );

  List<Aircraft> findBySeatingCapacityGreaterThanEqualAndFlightRangeGreaterThanEqual(
    Integer seatingCapacity,
    Integer flightRange
  );

}
