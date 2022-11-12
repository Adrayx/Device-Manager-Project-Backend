package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.memory;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.User;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.UserRepository;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private volatile int currentId = 1;
    private final Map<Integer, User> data = new HashMap<>();

    @Override
    public User save(User user) {
        if(user.getId() != null)
            data.put(user.getId(), user);
        else {
            user.setId(currentId++);
            data.put(user.getId(), user);
        }

        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(User user) {
        data.remove(user.getId());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }
}
