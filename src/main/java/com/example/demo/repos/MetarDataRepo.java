package com.example.demo.repos;

import com.example.demo.Entities.MetarData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetarDataRepo extends JpaRepository<MetarData, Long> {
    @Query("select m " +
            "from MetarData m " +
            "where m.airportCode =?1 ")
    Optional<MetarData> findByAirportCode(String airportCode);
}
