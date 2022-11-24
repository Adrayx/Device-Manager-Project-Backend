package com.utcn.ds2022_30643_moldovan_andrei_1_backend.service;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.LogInDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.UserDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.User;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.random;

@Service
@RequiredArgsConstructor
public class LogInService {
    private final RepositoryFactory factory;
    private final PasswordEncoder encoder;
    @Transactional
    public LogInDto logIn(String username, String password) throws NoSuchAlgorithmException {
        List<User> users = factory.createUserRepository().findAll().stream().filter(value -> value.getUsername().equals(username) && encoder.matches(password, value.getPassword())).toList();
        if(users.size() == 0){
            return null;
        }
        String baseToken = username + password;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                baseToken.getBytes(StandardCharsets.UTF_8));

        BigInteger numHash = new BigInteger(1, hash);
        StringBuilder hexHash = new StringBuilder(numHash.toString(16));
        while(hexHash.length() < 64)
        {
            hexHash.insert(0, "0");
        }

        String dotToken = hexHash.toString();
        dotToken = dotToken + random();

        byte[] hashDot = digest.digest(
                dotToken.getBytes(StandardCharsets.UTF_8));

        baseToken = hexHash.toString();

        numHash = new BigInteger(1, hashDot);
        hexHash = new StringBuilder(numHash.toString(16));
        while(hexHash.length() < 64)
        {
            hexHash.insert(0, "0");
        }

        dotToken = hexHash.toString();
        String token = baseToken + "." + dotToken;
        return LogInDto.logInDtoFromLogIn(factory.createLogInRepository().save(new LogIn(token, users.get(0))));
    }

    @Transactional
    public boolean logOut(String token){
        return factory.createLogInRepository().removeByToken(token);
    }

    @Transactional
    public Optional<LogIn> isLoggedIn(String token){
        return factory.createLogInRepository().findByToken(token);
    }
}
