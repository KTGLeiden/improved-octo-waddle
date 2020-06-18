package com.example.airport.controllers;

import com.example.airport.exceptions.BadRequestException;
import com.example.airport.exceptions.NotFoundException;
import com.example.airport.models.Airport;
import com.example.airport.repositories.AirplaneRepository;
import com.example.airport.models.Airplane;
import com.example.airport.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/airplanes")
public class AirplaneController {

    @Autowired
    private AirplaneRepository airplaneRepository;
    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<Airplane> index() {
        return airplaneRepository.findAll();
    }

    @GetMapping("/{id}")
    public Airplane index(@PathVariable Long id) {
        return airplaneRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Airplane save(@RequestBody Airplane airplane) {
        if(airplane.getId() != null) {
            throw new BadRequestException("You cannot save an airplane which already has an id!");
        }
        return airplaneRepository.save(airplane);
    }

    @PutMapping
    public Airplane update(@RequestBody Airplane airplane) {
        if(airplane.getId() == null) {
            throw new BadRequestException();
        }
        return airplaneRepository.save(airplane);
    }

    @PutMapping("/refuel/{id}")
    public Airplane refuel(@PathVariable Long id) {
        Airplane airplane = airplaneRepository.findById(id).orElseThrow(NotFoundException::new);
        airplane.setTonsOfFuel(5);
        return airplaneRepository.save(airplane);
    }

    @PutMapping("/move/{id}/{destinationId}")
    public void move(@PathVariable Long id, @PathVariable Long destinationId)  throws Exception {
        Airplane airplane = airplaneRepository.getOne(id);
        if(airplane.getTonsOfFuel() < 2) {
            throw new Exception("Not enough fuel!");
        }
        if(airplane.getAirport().getId().equals(destinationId)) {
            throw new BadRequestException("Plane is already there!");
        }
        Airport airport = airportRepository.findById(destinationId).orElseThrow(NotFoundException::new);
        airplane.setAirport(airport);
        airplane.setTonsOfFuel(airplane.getTonsOfFuel() - 2);
        airplaneRepository.save(airplane);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        airplaneRepository.deleteById(id);
    }
}
