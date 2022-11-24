package com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time time;

    @Column
    private Double consumption;

    @ManyToOne
    private EnergyDevice device;

    public Measurement(Date date, Time time, Double consumption, EnergyDevice device){
        this.date = date;
        this.time = time;
        this.consumption = consumption;
        this.device = device;
    }
}
