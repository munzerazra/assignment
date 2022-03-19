package com.example.demo.Dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class AirportDto {
    public Long id;
    @NonNull
    public String airportCode;

}
