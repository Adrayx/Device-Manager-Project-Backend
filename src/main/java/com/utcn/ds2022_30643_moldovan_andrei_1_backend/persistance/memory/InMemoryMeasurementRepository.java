package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.memory;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.Measurement;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.MeasurementRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMeasurementRepository implements MeasurementRepository {
    private volatile int currentId = 1;
    private final Map<Integer, Measurement> data = new HashMap<>();

    @Override
    public Measurement save(Measurement measurement) {
        if(measurement.getId() != null){
            data.put(measurement.getId(), measurement);
        } else {
            measurement.setId(currentId++);
            data.put(measurement.getId(), measurement);
        }

        return measurement;
    }

    @Override
    public Optional<Measurement> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Measurement> findByDevice(EnergyDevice device) {
        return data.values().stream().filter(value -> Objects.equals(value.getDevice().getId(), device.getId())).collect(Collectors.toList());
    }

    @Override
    public void remove(Measurement measurement) {
        data.remove(measurement.getId());
    }

    @Override
    public List<Measurement> findAll() {
        return new ArrayList<>(data.values());
    }
}
