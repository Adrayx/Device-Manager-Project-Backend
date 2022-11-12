package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.memory;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
    private final InMemoryMeasurementRepository inMemoryMeasurementRepository = new InMemoryMeasurementRepository();
    private final InMemoryEnergyDeviceRepository inMemoryEnergyDeviceRepository = new InMemoryEnergyDeviceRepository();

    private final InMemoryLogInRepository inMemoryLogInRepository = new InMemoryLogInRepository();

    @Override
    public UserRepository createUserRepository() {
        return inMemoryUserRepository;
    }

    @Override
    public EnergyDeviceRepository createEnergyDeviceRepository() {
        return inMemoryEnergyDeviceRepository;
    }

    @Override
    public MeasurementRepository createMeasurementRepository() {
        return inMemoryMeasurementRepository;
    }

    @Override
    public LogInRepository createLogInRepository() {
        return inMemoryLogInRepository;
    }
}
