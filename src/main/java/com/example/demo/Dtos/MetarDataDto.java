package com.example.demo.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.HashMap;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class MetarDataDto {
    @NonNull
    String data;
    String dataAsString;
    String timestamp;
    Long windStrength;
    Long temperature;
    String overallVisibility;
}
