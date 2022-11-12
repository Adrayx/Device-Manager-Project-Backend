package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.memory;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.EnergyDeviceRepository;

import java.util.*;

public class InMemoryEnergyDeviceRepository implements EnergyDeviceRepository {
    private volatile int currentId = 1;
    private final Map<Integer, EnergyDevice> data = new HashMap<>();

    @Override
    public EnergyDevice save(EnergyDevice device) {
        if(device.getId() != null){
            data.put(device.getId(), device);
        } else {
            device.setId(currentId++);
            data.put(device.getId(), device);
        }

        return device;
    }

    @Override
    public Optional<EnergyDevice> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(EnergyDevice device) {
        data.remove(device.getId());
    }

    @Override
    public List<EnergyDevice> findAll() {
        return new ArrayList<>(data.values());
    }
}
