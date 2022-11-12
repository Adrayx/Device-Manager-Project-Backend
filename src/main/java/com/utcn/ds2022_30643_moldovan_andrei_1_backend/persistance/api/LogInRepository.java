package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;

import java.util.Optional;

public interface LogInRepository {
    Optional<LogIn> findById(int id);

    Optional<LogIn> findByToken(String token);

    boolean removeByToken(String token);

    LogIn save(LogIn log);
}
