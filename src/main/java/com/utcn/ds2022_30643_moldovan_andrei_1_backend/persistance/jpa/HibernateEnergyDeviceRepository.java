package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.jpa;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.EnergyDeviceRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateEnergyDeviceRepository implements EnergyDeviceRepository {
    private final EntityManager entityManager;

    @Override
    public EnergyDevice save(EnergyDevice device) {
        if(device.getId() != null)
            entityManager.merge(device);
        else
            entityManager.persist(device);

        return device;
    }

    @Override
    public Optional<EnergyDevice> findById(int id) {
        return Optional.ofNullable(entityManager.find(EnergyDevice.class, id));
    }

    @Override
    public void remove(EnergyDevice device) {
        entityManager.remove(device);
    }

    @Override
    public List<EnergyDevice> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EnergyDevice> query = builder.createQuery(EnergyDevice.class);
        query.select(query.from(EnergyDevice.class));
        return entityManager.createQuery(query).getResultList();
    }
}
