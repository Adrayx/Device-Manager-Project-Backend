package com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.memory;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.LogInRepository;

import java.util.*;

public class InMemoryLogInRepository implements LogInRepository {
    private volatile int currentId = 1;
    private final Map<Integer, LogIn> data = new HashMap<>();

    @Override
    public List<LogIn> findAll(){
        return new ArrayList<>(data.values());
    }
    @Override
    public Optional<LogIn> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<LogIn> findByToken(String token) {
        return Optional.ofNullable(data.values().stream().filter(value -> value.getToken().equals(token)).toList().get(0));
    }

    @Override
    public void removeById(LogIn log) {
        data.remove(log.getId());
    }

    @Override
    public boolean removeByToken(String token) {
        Optional<LogIn> logs = findByToken(token);
        if(logs.isPresent()) {
            for (Map.Entry<Integer, LogIn> entry : data.entrySet()) {
                if (Objects.equals(token, entry.getValue().getToken())) {
                    data.remove(entry.getKey());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LogIn save(LogIn log) {
        if(log.getId() != null){
            data.put(log.getId(), log);
        } else {
            log.setId(currentId++);
            data.put(log.getId(), log);
        }

        return log;
    }
}
