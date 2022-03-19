package com.example.demo.Dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class MetarDataDto {
    @NonNull
    String data;
}
