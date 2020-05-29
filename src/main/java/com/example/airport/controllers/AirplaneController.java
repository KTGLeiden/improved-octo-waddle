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

    @PostMapping
    public void save(@RequestBody Airplane airplane) {
        airplaneRepository.save(airplane);
    }

    @PutMapping("/refuel/{id}")
    public void refuel(@PathVariable Long id) {
        Airplane airplane = airplaneRepository.findById(id).orElseThrow(NotFoundException::new);
        airplane.setTonsOfFuel(5);
        airplaneRepository.save(airplane);
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
