package com.malenko.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Aircraft extends BaseEntity<Long> {

    @NotNull
    private String name;

    @NotNull
    private Integer seatingCapacity;

    @NotNull
    private Integer carryingCapacity;

    @NotNull
    private Integer flightRange;

    @NotNull
    private Integer fuelConsumption;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AircraftType type;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    @JsonIgnore
    private Airline airline;

    public Aircraft(@NotNull String name, @NotNull Integer seatingCapacity, @NotNull Integer carryingCapacity,
                    @NotNull Integer flightRange, @NotNull Integer fuelConsumption,
                    @NotNull AircraftType type, Airline airline) {
        this.name = name;
        this.seatingCapacity = seatingCapacity;
        this.carryingCapacity = carryingCapacity;
        this.flightRange = flightRange;
        this.fuelConsumption = fuelConsumption;
        this.type = type;
        this.airline = airline;
    }

    public Aircraft() {
    }
}
