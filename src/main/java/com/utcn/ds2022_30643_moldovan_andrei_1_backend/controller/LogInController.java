package com.utcn.ds2022_30643_moldovan_andrei_1_backend.controller;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.LogInDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping()
@RequiredArgsConstructor
public class LogInController {
    private final LogInService logInService;

    @RequestMapping("/log_in")
    @PostMapping()
    public LogInDto logIn(@RequestHeader("username") String username, @RequestHeader("password") String password) throws NoSuchAlgorithmException {
        return logInService.logIn(username, password);
    }

    @RequestMapping("/log_out")
    @PostMapping
    public boolean logOut(@RequestHeader("token") String token){
        return logInService.logOut(token);
    }
}
