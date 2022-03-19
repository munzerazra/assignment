package com.example.demo.Services;

import com.example.demo.Entities.Subscription;
import com.example.demo.repos.SubscriptionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    MetarServices metarServices;
    @Autowired
    SubscriptionRepo subscriptionRepo;

    public void scheduledMetarServiceRetrieveAndSave() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Set<String> subscriptionSetNames = new HashSet<>();

        Set<Subscription> subscriptionSet = new HashSet<>(subscriptionRepo.findAll());
        for (Subscription subscription : subscriptionSet) {
            subscriptionSetNames.add(subscription.getName());
        }
        if (subscriptionSetNames.size() > 0) {
            metarServices.externalRetrieveAndSaveMetarDataBySubscription(subscriptionSetNames);
        } else {
            logger.info("no subscription names provided");
        }
        Date now = new Date();
        String strDate = sdf.format(now);

        logger.info("last update for local Metar data at :: ".concat(strDate));
    }
}

