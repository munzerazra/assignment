package com.example.demo.Controllers;

import com.example.demo.Dtos.MetarDataDto;
import com.example.demo.Services.MetarServices;
import com.example.demo.Services.Scheduler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/metar")
public class MetarContoller {

    private static final Logger logger = LoggerFactory.getLogger(MetarContoller.class);

    @Autowired
    MetarServices metarServices;
    @Autowired
    Scheduler scheduler;


    /**
     * Method to fetch all METAR data by airport code
     *
     * @param name - airport code value
     * @return MetarDataDto
     */
    @GetMapping(value = "{name}")
    @ResponseStatus(HttpStatus.OK)
    public MetarDataDto retrieveAllMetarDataByAirportCode(@PathVariable("name") String name) {
        return metarServices.retrieveMetarDataByAirportCode(name);
    }

    /**
     * Method used to fetch METAR data by airport code
     *
     * @param name - airport code value
     * @return MetarDataDto
     */
    @GetMapping(value = "/airport/data/{name}")
    @ResponseStatus(HttpStatus.OK)
    public MetarDataDto getMetarDataByAirportCode(@PathVariable("name") String name) {
        return metarServices.retrieveMetarDataByAirportCode(name);
    }

    /**
     * Method used to update METAR data by airport code
     *
     * @param metarDataDto - METAR dto requested
     * @param name         - the airport code value
     */
    @PostMapping(value = "/airport/data/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMetarDataByAirportCode(@RequestBody @NotNull MetarDataDto metarDataDto, @PathVariable("name") String name) {
        String metarData = metarDataDto.getData();
        metarServices.saveOrUpdateMetarDataByAirport(metarData, name);
    }

    /**
     * Method used to update METAR data
     *
     * @throws IOException - exception handling
     */
    @GetMapping(value = "/data/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateMetarData() throws IOException {
        scheduler.scheduledMetarServiceRetrieveAndSave();
    }

}
