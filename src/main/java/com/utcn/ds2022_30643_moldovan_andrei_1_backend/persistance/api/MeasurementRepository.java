package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.Measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository {
    Measurement save(Measurement measurement);

    Optional<Measurement> findById(int id);

    List<Measurement> findByDevice(EnergyDevice device);

    void remove(Measurement measurement);

    List<Measurement> findAll();
}
