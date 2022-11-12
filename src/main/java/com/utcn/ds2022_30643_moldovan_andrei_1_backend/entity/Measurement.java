package com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

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

    @Column
    private Double consumption;

    @ManyToOne
    private EnergyDevice device;

    public Measurement(Date date, Double consumption, EnergyDevice device){
        this.date = date;
        this.consumption = consumption;
        this.device = device;
    }
}
