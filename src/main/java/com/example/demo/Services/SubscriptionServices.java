package com.example.demo.Services;

import com.example.demo.Dtos.AirportDto;
import com.example.demo.Dtos.SubscriptionDto;
import com.example.demo.Entities.Airport;
import com.example.demo.Entities.Subscription;
import com.example.demo.repos.AirportRepo;
import com.example.demo.repos.SubscriptionRepo;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.demo.Constants.Constants.CoreError.MISSING_AIRPORT_CODE;
import static com.example.demo.Constants.Constants.CoreError.NO_AIRPORT_FOUND;

@Service
public class SubscriptionServices {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServices.class);

    @Autowired
    SubscriptionRepo subscriptionRepo;
    @Autowired
    AirportRepo airportRepo;

    public SubscriptionDto addSubscription(@org.jetbrains.annotations.NotNull SubscriptionDto subscriptionDto) {
        if (!isUnique(subscriptionDto.getSubscriptionName())) {
            throw new RuntimeException();
        }
        Subscription subscription = new Subscription();
        subscription.setName(subscriptionDto.getSubscriptionName());
        Subscription savedSubscription = subscriptionRepo.save(subscription);

        for (AirportDto airportDto : subscriptionDto.getAirportDtos()) {
            Airport airport = new Airport();
            airport.setAirportCode(airportDto.airportCode);
            airport.setSubscription(savedSubscription);
            airport = airportRepo.save(airport);
            savedSubscription.getAirports().add(airport);
            savedSubscription = subscriptionRepo.save(savedSubscription);
        }
        SubscriptionDto subscriptionDtoSaved = new SubscriptionDto();
        Set<AirportDto> airportDtos = new HashSet<>();
        for (Airport airport : savedSubscription.getAirports()) {
            AirportDto airportDto = new AirportDto();
            airportDto.airportCode = airport.getAirportCode();
            airportDto.id = airport.getId();
            airportDtos.add(airportDto);
        }
        subscriptionDtoSaved.setSubscriptionName(savedSubscription
                .getName()).setAirportDtos(airportDtos).setId(savedSubscription.getId());
        return subscriptionDtoSaved;
    }

    public SubscriptionDto viewSubscription(String subscriptionName) {
        if (subscriptionName == null || subscriptionName.isEmpty()) {
            throw new NoSuchElementException();
        }
        Subscription subscription = subscriptionRepo.findByName(subscriptionName).orElseThrow(NoSuchElementException::new);
        if (subscription == null) {
            throw new NoSuchElementException();
        }

        SubscriptionDto subscriptionDtoView = new SubscriptionDto();
        for (Airport airport : subscription.getAirports()) {
            AirportDto airportDto = new AirportDto();
            airportDto.setAirportCode(airport.getAirportCode());
            subscriptionDtoView.getAirportDtos().add(airportDto);
        }

        subscriptionDtoView.setSubscriptionName(subscription
                .getName()).setId(subscription.getId());
        return subscriptionDtoView;
    }

    public ArrayList<SubscriptionDto> viewAllSubscription() {
        ArrayList<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        List<Subscription> subscriptions;
        subscriptions = subscriptionRepo.findAll();
        for (Subscription subscription : subscriptions) {
            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setId(subscription.getId()).setSubscriptionName(subscription.getName());
            Set<AirportDto> airportDtos = new HashSet<>();
            for (Airport airport : subscription.getAirports()) {
                AirportDto airportDto = new AirportDto();
                airportDto.setAirportCode(airport.getAirportCode()).setId(airport.getId());
                airportDtos.add(airportDto);
                subscriptionDto.setAirportDtos(airportDtos);
            }
            subscriptionDtos.add(subscriptionDto);
        }
        return subscriptionDtos;

    }

    public SubscriptionDto deleteAirportsSubscription(@org.jetbrains.annotations.NotNull SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionRepo.findByName(subscriptionDto.getSubscriptionName()).orElseThrow(NoSuchElementException::new);
        if (subscription == null) {
            throw new NoSuchElementException();
        }
        if (subscription.getAirports().isEmpty()) {
            throw new NoSuchElementException();
        }
        Set<Airport> airports;
        Set<String> airportsCodes = new HashSet<>();
        for (AirportDto airportDto : subscriptionDto.getAirportDtos()) {
            if (airportRepo.findByAirportCode(airportDto.airportCode) == null)
                throw new RuntimeException(MISSING_AIRPORT_CODE.name());
            airportsCodes.add(airportDto.getAirportCode());
        }
        airports = airportRepo.findAllByName(airportsCodes);
        if (airports.isEmpty()) throw  new RuntimeException(NO_AIRPORT_FOUND.name());
        subscription.getAirports().removeAll(airports);
        subscription=subscriptionRepo.save(subscription);
        airportRepo.deleteAll(airports);

        return subscriptionDto;
    }

    public Long deleteSubscription(@org.jetbrains.annotations.NotNull SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionRepo.findByName(subscriptionDto.getSubscriptionName()).orElseThrow(NoSuchElementException::new);
        if (subscription == null)
            throw new NoSuchElementException();
        subscriptionRepo.deleteById(subscription.getId());
        return subscription.getId();
    }

    @NotNull
    private @org.jetbrains.annotations.NotNull Boolean isUnique(String name) {
        int countKey = subscriptionRepo.countByName(name);
        return (countKey == 0);
    }


}
