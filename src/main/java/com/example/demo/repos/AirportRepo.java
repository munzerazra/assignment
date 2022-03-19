package com.example.demo.repos;

import com.example.demo.Entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AirportRepo extends JpaRepository<Airport, Long> {
    @Query("select a " +
            "from Airport a " +
            "where a.airportCode in ?1 ")
    Set<Airport> findAllByName(Set<String> names);

    Airport findByAirportCode(String airportCode);
}
