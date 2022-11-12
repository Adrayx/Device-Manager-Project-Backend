package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;

import java.util.List;
import java.util.Optional;

public interface EnergyDeviceRepository {
    EnergyDevice save(EnergyDevice device);

    Optional<EnergyDevice> findById(int id);

    void remove(EnergyDevice device);

    List<EnergyDevice> findAll();
}
