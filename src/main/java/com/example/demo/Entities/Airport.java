package com.example.demo.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "airportCode", nullable = false, unique = true)
    private String airportCode;
    @Column(name = "status")
    private Long status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription", nullable = false)
    private Subscription subscription;

}
