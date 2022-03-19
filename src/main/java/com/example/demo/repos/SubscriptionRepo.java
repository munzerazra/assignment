package com.example.demo.repos;

import com.example.demo.Entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
            "where s.name in ?1 ")
    Set<Subscription> findByNameIn(Set<String> names);

}
