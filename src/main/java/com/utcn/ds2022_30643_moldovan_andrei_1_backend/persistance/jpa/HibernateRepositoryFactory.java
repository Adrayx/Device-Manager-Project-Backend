package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.jpa;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "repository-type", havingValue = "JPA")
public class HibernateRepositoryFactory implements RepositoryFactory {
    private final EntityManager entityManager;

    @Override
    public UserRepository createUserRepository() {
        return new HibernateUserRepository(entityManager);
    }

    @Override
    public EnergyDeviceRepository createEnergyDeviceRepository() {
        return new HibernateEnergyDeviceRepository(entityManager);
    }

    @Override
    public MeasurementRepository createMeasurementRepository() {
        return new HibernateMeasurementRepository(entityManager);
    }

    @Override
    public LogInRepository createLogInRepository(){
        return new HibernateLogInRepository(entityManager);
    }
}
