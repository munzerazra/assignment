package com.example.demo.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class SubscriptionDto {
    public Long id;
    @NonNull
    public String subscriptionName;

    public String filter;
    public Long status;

    private Set<AirportDto> AirportDtos = new LinkedHashSet<>();


}


