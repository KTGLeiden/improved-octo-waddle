package com.example.airport.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Airplane {

    @Id
    @GeneratedValue
    private Long id;

    private int tonsOfFuel = 5;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    private Airport airport;

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTonsOfFuel() {
        return tonsOfFuel;
    }

    public void setTonsOfFuel(int tonsOfFuel) {
        this.tonsOfFuel = tonsOfFuel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
