package com.malenko.rest.model;

import lombok.Data;

@Data
public class CapacityDto {
  private Integer totalSeatingCapacity;
  private Integer totalCarryingCapacity;

  public CapacityDto(Integer totalSeatingCapacity, Integer totalCarryingCapacity) {
    this.totalSeatingCapacity = totalSeatingCapacity;
    this.totalCarryingCapacity = totalCarryingCapacity;
  }
}
