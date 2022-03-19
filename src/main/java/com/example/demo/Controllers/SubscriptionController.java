package com.example.demo.Controllers;

import com.example.demo.Dtos.SubscriptionDto;
import com.example.demo.Services.SubscriptionServices;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    SubscriptionServices subscriptionServices;

    /**
     * Method add a subscription
     *
     * @param dto - The subscription dto of type {SubscriptionDto}
     * @return {SubscriptionDto}
     */
    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionDto addSubscription(@RequestBody SubscriptionDto dto) {
        Preconditions.checkNotNull(dto);
        return subscriptionServices.addSubscription(dto);
    }

    /**
     * Method to view subscription details
     *
     * @param name - the subscription name
     * @return {SubscriptionDto}
     */
    @GetMapping(value = "/{name}")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionDto view(@PathVariable("name") String name) {
        return subscriptionServices.viewSubscription(name);
    }

    /**
     * Method to fetch all subscriptions
     *
     * @return {ArrayList<SubscriptionDto>}
     */
    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<SubscriptionDto> view() {
        return subscriptionServices.viewAllSubscription();
    }

    /**
     * Method to delete a subscription
     *
     * @param dto - the dto of type {SubscriptionDto}
     * @return {Long}
     */
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public Long delete(@RequestBody SubscriptionDto dto) {
        return subscriptionServices.deleteSubscription(dto);
    }

    /**
     * Method to delete all subscription related to an airport
     *
     * @param dto - the dto of type {SubscriptionDto}
     * @return {Long}
     */
    @PutMapping(value = "/deleteAirports")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionDto deleteAirportsSubscription(@RequestBody SubscriptionDto dto) {
        return subscriptionServices.deleteAirportsSubscription(dto);
    }

}