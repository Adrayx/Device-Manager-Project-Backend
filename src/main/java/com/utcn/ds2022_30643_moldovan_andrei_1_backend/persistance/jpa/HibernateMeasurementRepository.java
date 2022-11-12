package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.jpa;


import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.Measurement;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HibernateMeasurementRepository implements MeasurementRepository {
    private final EntityManager entityManager;

    @Override
    public Measurement save(Measurement measurement) {
        if(measurement.getId() != null)
            entityManager.merge(measurement);
        else
            entityManager.persist(measurement);

        return measurement;
    }

    @Override
    public Optional<Measurement> findById(int id) {
        return Optional.ofNullable(entityManager.find(Measurement.class, id));
    }

    @Override
    public List<Measurement> findByDevice(EnergyDevice device) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Measurement> query = builder.createQuery(Measurement.class);
        query.select(query.from(Measurement.class));
        return entityManager.createQuery(query).getResultList().stream().filter(value -> value.getDevice().equals(device)).toList();
    }

    @Override
    public void remove(Measurement measurement) {
        entityManager.remove(measurement);
    }

    @Override
    public List<Measurement> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Measurement> query = builder.createQuery(Measurement.class);
        query.select(query.from(Measurement.class));
        return entityManager.createQuery(query).getResultList();
    }
}
