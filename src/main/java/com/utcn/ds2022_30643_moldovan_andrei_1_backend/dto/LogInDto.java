package com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;
import lombok.Data;

@Data
public class LogInDto {
    private Integer id;
    private String token;
    private Boolean role;

    public static LogInDto logInDtoFromLogIn(LogIn log){
        LogInDto dto = new LogInDto();
        dto.setId(log.getUser().getId());
        dto.setToken(log.getToken());
        dto.setRole(log.getUser().getUserType());

        return dto;
    }
}
