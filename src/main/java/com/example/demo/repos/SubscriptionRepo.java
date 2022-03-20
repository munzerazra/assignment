package com.example.demo.repos;

import com.example.demo.Entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    @Query("select count(s) " +
            "from Subscription s " +
            "where s.name =?1 ")
    int countByName(String name);

    @Query("select s " +
            "from Subscription s " +
            "where s.name =?1 ")
    Optional<Subscription> findByName(String name);

    @Query("select s " +
            "from Subscription s " +
            "join fetch s.airports a " +
            "where s.name =?1 " +
            "and a.airportCode LIKE %?2%" +
            "and a.status =?3")
    Optional<Subscription> findByAirportCodeAndStatusFiltered(String SubscriptionName, String filter, Long status);

    @Query("select s " +
            "from Subscription s " +
            "join fetch s.airports a " +
            "where s.name =?1 " +
            "and a.status =?2")
    Optional<Subscription> findByAirportCodeAndStatus(String SubscriptionName, Long status);

    @Query("select s " +
            "from Subscription s " +
            "join fetch s.airports a " +
            "where s.name =?1 " +
            "and a.airportCode LIKE %?2%")
    Optional<Subscription> findByAirportCodeAndFiltered(String SubscriptionName, String filter);

    @Query("select s " +
            "from Subscription s " +
            "where s.name in ?1 ")
    Set<Subscription> findByNameIn(Set<String> names);

}
