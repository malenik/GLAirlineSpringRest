package com.malenko.rest.controller.v1;

import com.malenko.rest.model.BaseEntity;
import com.malenko.rest.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import static com.malenko.rest.model.Response.*;

public interface CrudController<T extends BaseEntity<ID>, ID> {
  
  JpaRepository<T, ID> repo();

  @GetMapping
  default Response<Iterable<T>> list() {
    return Ok(repo().findAll());
  }

  @GetMapping(path = "/{id}")
  default Response<T> read(@PathVariable ID id) {
    return repo().findById(id)
      .map(Response::Ok)
      .orElse(NotFound("Entity with id " + id + " not found."));
  }

  @PostMapping
  default Response<T> create(@RequestBody T t) {
    return Created(repo().save(t));
  }

  @PutMapping(path = "/{id}")
  default Response<T> update(@PathVariable ID id, @RequestBody T body) {
    return repo().findById(id)
      .map(persistedAirline -> {
        body.setId(persistedAirline.getId());
        repo().save(body);
        return Ok(persistedAirline);
      })
      .orElse(NotFound("Entity with id " + id + " not found."));
  }

  @DeleteMapping(path = "/{id}")
  default Response<T> delete(@PathVariable ID id) {
    return repo().findById(id)
      .map(aircraft -> {
        repo().delete(aircraft);
        return Ok(aircraft);
      })
      .orElse(NotFound("Entity with id " + id + " not found."));
  }
}
