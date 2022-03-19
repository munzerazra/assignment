package com.example.demo.Services;

import com.example.demo.Dtos.MetarDataDto;
import com.example.demo.Entities.Airport;
import com.example.demo.Entities.MetarData;
import com.example.demo.Entities.Subscription;
import com.example.demo.repos.MetarDataRepo;
import com.example.demo.repos.SubscriptionRepo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static com.example.demo.Constants.Constants.CoreError.*;
import static com.example.demo.Constants.Constants.METAR_EXTERNAL_SERVICE_ENDPOINT;
import static com.example.demo.Constants.Constants.TEXT_EXTENSION;

@Service
public class MetarServices {

    private static final Logger logger = LoggerFactory.getLogger(MetarServices.class);

    @Autowired
    SubscriptionRepo subscriptionRepo;

    @Autowired
    MetarDataRepo metarDataRepo;

    public void saveOrUpdateMetarDataByAirport(String metar, String airportCode) {
        MetarData metarData = new MetarData();
        if (airportCode == null || airportCode.isEmpty() || metar.isEmpty()) {
            throw new RuntimeException(MISSING_AIRPORT_CODE_OR_METAR_DATA.name());
        }

        boolean found = metarDataRepo.findByAirportCode(airportCode).isPresent();
        if (found) {
            metarData = metarDataRepo.findByAirportCode(airportCode).get();
            metarData.setData(metar);
            metarDataRepo.save(metarData);
        } else {
            metarData.setAirportCode(airportCode).setData(metar);
            metarDataRepo.save(metarData);
        }
    }

    public MetarDataDto getMetarDataByAirport(String airportCode) {
        MetarDataDto metarDataDto = new MetarDataDto();
        if (airportCode == null || airportCode.isEmpty()) {
            throw new RuntimeException(MISSING_AIRPORT_CODE.name());
        }

        MetarData metarData;
        metarData = metarDataRepo.findByAirportCode(airportCode).orElseThrow();
        metarDataDto.setData(metarData.getData());
        return metarDataDto;
    }

    public String getMetarDataByAirportCode(String airportName) {
        String url = METAR_EXTERNAL_SERVICE_ENDPOINT.concat(airportName).concat(TEXT_EXTENSION);
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(url, String.class);
        assert res != null;
        String Result = res.replace("\n", "");

        String newRes = res.substring(16, Result.length());

        return newRes.replace("\n", "");

    }

    public void externalRetrieveAndSaveMetarDataBySubscription(@NotNull Set<String> subscriptionNames) {
        if (subscriptionNames.isEmpty()) {
            throw new RuntimeException(MISSING_SUBSCRIPTION_NAME.name());
        }
        Set<Subscription> subscriptions;
        subscriptions = subscriptionRepo.findByNameIn(subscriptionNames);

        for (Subscription subscription : subscriptions) {
            for (Airport airport : subscription.getAirports()) {
                String airportMetarData = getMetarDataByAirportCode(airport.getAirportCode());
                saveOrUpdateMetarDataByAirport(airportMetarData, airport.getAirportCode());
            }
        }
    }

    public MetarDataDto retrieveMetarDataByAirportCode(String airportCode) {
        MetarDataDto metarDataDto = new MetarDataDto();
        if (airportCode == null || airportCode.isEmpty()) {
            throw new RuntimeException(MISSING_AIRPORT_CODE.name());
        }

        MetarData metarData = metarDataRepo.findByAirportCode(airportCode).orElseThrow();
        return metarDataDto.setData(metarData.getData());
    }
}


