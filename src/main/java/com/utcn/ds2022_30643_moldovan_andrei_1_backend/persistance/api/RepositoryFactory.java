package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api;

public interface RepositoryFactory {
    UserRepository createUserRepository();
    EnergyDeviceRepository createEnergyDeviceRepository();
    MeasurementRepository createMeasurementRepository();
    LogInRepository createLogInRepository();
}
