package com.utcn.ds2022_30643_moldovan_andrei_1_backend.controller;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.EnergyDeviceDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.UserDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.LogInService;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LogInService logInService;

    @GetMapping()
    public List<UserDto> findUsers(@RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if (log.get().getUser().getUserType())
            return userService.findAll();
        return null;
    }

    @GetMapping(value = "/{id}")
    public UserDto findUserById(@PathVariable("id") Integer id, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getId().equals(id) || log.get().getUser().getUserType())
            return userService.findById(id);
        return null;
    }

    @PostMapping()
    public UserDto insert(@RequestBody UserDto user, @RequestHeader("token") String token){
        return userService.insert(user);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto user, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getId().equals(user.getId()) || log.get().getUser().getUserType())
            return userService.update(user);
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public boolean remove(@PathVariable("id") Integer id, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return false;
        if(log.get().getUser().getId().equals(id) || log.get().getUser().getUserType()) {
            userService.remove(id);
            return true;
        }
        return false;
    }

    @PutMapping(value = "/{id}/add")
    public UserDto addDevice(@PathVariable("id") Integer id, @RequestBody EnergyDeviceDto device, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return userService.addEnergyDevice(id, device);
        return null;
    }

    @PutMapping(value = "/{id}/remove")
    public UserDto removeDevice(@PathVariable("id") Integer id, @RequestBody EnergyDeviceDto device, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return userService.removeEnergyDevice(id, device);
        return null;
    }
}
