package com.example.demo.Entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class MetarData {
    @Column(name = "airportCode", nullable = false, unique = true)
    String airportCode;
    @Column(name = "data", nullable = false)
    String data;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
