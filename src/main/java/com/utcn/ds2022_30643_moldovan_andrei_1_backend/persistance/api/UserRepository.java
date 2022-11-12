package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(int id);

    void remove(User user);

    List<User> findAll();
}
