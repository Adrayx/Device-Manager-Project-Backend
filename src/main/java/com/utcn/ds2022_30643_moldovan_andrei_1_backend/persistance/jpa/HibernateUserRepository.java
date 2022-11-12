package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.jpa;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.User;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public User save(User user){
        if(user.getId() != null){
            entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }

        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user);
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.select(query.from(User.class));
        return entityManager.createQuery(query).getResultList();
    }
}
