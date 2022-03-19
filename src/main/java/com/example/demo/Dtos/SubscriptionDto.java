package com.example.demo.Dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class SubscriptionDto {
    public Long id;
    @NonNull
    public String subscriptionName;

    private Set<AirportDto> AirportDtos = new LinkedHashSet<>();


}


