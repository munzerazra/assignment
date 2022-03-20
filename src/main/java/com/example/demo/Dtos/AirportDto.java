package com.example.demo.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirportDto {
    public Long id;
    @NonNull
    public String airportCode;

    private String status;

}
