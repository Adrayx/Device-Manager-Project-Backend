package com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnergyDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1000)
    private String description;

    @Column(length = 500, nullable = false)
    private String address;

    private Double threshold;

    public EnergyDevice(String description, String address){
        this.description = description;
        this.address = address;
    }

    public EnergyDevice(String description, String address, Double threshold){
        this.description = description;
        this.address = address;
        this.threshold = threshold;
    }

    public Measurement createMeasurement(Double hourlyConsumption){
        long millis=System.currentTimeMillis();
        return new Measurement(new Date(millis), hourlyConsumption, this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EnergyDevice device = (EnergyDevice) o;
        return id != null && Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
