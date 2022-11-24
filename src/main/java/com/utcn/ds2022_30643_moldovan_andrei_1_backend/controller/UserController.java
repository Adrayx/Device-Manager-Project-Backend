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
@CrossOrigin(origins = {"*"})
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
    public UserDto insert(@RequestBody UserDto user){
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
        System.out.println("We are in remove user\n");
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty()) {
            System.out.println("User is not logged in or it isn't existent\n");
            return false;
        }
        if(log.get().getUser().getId().equals(id) || log.get().getUser().getUserType()) {
            System.out.println("User with the " + id + " will be removed\n");
            userService.remove(id);
            return true;
        }
        System.out.println("User is not an admin or it isn't the own account\n");
        return false;
    }

    @PutMapping(value = "/{id}/add/{deviceId}")
    public UserDto addDevice(@PathVariable("id") Integer id, @PathVariable("deviceId") Integer deviceId, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return userService.addEnergyDevice(id, deviceId);
        return null;
    }

    @PutMapping(value = "/{id}/remove/{deviceId}")
    public UserDto removeDevice(@PathVariable("id") Integer id, @PathVariable("deviceId") Integer deviceId, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return userService.removeEnergyDevice(id, deviceId);
        return null;
    }
}
