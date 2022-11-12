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
        String baseToken = username + password + random() * 1000000000;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                baseToken.getBytes(StandardCharsets.UTF_16));
        String dotToken = new String(hash);
        byte[] hashDot = digest.digest(
                dotToken.getBytes(StandardCharsets.UTF_16));
        baseToken = new String(hash, StandardCharsets.UTF_16);
        dotToken = new String(hashDot, StandardCharsets.UTF_16);

        ByteBuffer bb = StandardCharsets.UTF_16.encode(CharBuffer.wrap(baseToken + "." + dotToken));
        CharBuffer ascii = StandardCharsets.US_ASCII.decode(bb);

        String tok = new String(ascii.array());
        System.out.println(tok);

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
