package com.example.airport.controllers;

import com.example.airport.exceptions.BadRequestException;
import com.example.airport.exceptions.NotFoundException;
import com.example.airport.models.Airport;
import com.example.airport.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }

    @GetMapping("/{id}")
    public Airport getOne(@PathVariable Long id) {
        return airportRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        // Throw a 404 when the entity does not exist.
        if(!airportRepository.existsById(id)) {
            throw new NotFoundException();
        }
        airportRepository.deleteById(id);
    }

    @PostMapping
    public void create(@Valid @RequestBody Airport airport) {
        if(airport.getId() != null) {
            throw new BadRequestException();
        }
        airportRepository.save(airport);
    }

}
