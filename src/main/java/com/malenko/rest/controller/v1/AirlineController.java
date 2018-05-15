package com.malenko.rest.controller.v1;

        import com.malenko.rest.model.Airline;
        import com.malenko.rest.repository.AirlineRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/airlines")
public class AirlineController implements CrudController<Airline, Long> {

  @Autowired
  private AirlineRepository airlineRepository;

  @Override
  public JpaRepository<Airline, Long> repo() {
    return airlineRepository;
  }
}
