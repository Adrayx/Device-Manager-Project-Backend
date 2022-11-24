package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.jpa;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.LogInRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateLogInRepository implements LogInRepository {
    private final EntityManager entityManager;

    @Override
    public List<LogIn> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LogIn> query = builder.createQuery(LogIn.class);
        query.select(query.from(LogIn.class));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<LogIn> findById(int id) {
        return Optional.ofNullable(entityManager.find(LogIn.class, id));
    }

    @Override
    public Optional<LogIn> findByToken(String token) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LogIn> query = builder.createQuery(LogIn.class);
        query.select(query.from(LogIn.class));
        List<LogIn> logs =  entityManager.createQuery(query).getResultList();
        return logs.stream().filter(value -> value.getToken().equals(token)).findFirst();
    }

    @Override
    public boolean removeByToken(String token) {
        Optional<LogIn> logIn = findByToken(token);
        if(logIn.isPresent()) {
            entityManager.remove(logIn.get());
            return true;
        }
        return false;
    }

    @Override
    public void removeById(LogIn log){
        entityManager.remove(log);
    }

    @Override
    public LogIn save(LogIn log) {
        entityManager.persist(log);
        return log;
    }
}
