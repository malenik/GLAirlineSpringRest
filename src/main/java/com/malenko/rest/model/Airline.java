package com.malenko.rest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Airline extends BaseEntity<Long> {

  @NotNull
  private String name;

}
