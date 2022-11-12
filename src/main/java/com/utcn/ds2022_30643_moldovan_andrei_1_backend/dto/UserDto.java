package com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.User;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private Boolean userType;
    private List<EnergyDeviceDto> devices;

    public static UserDto userDtoFromUser(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setUserType(user.getUserType());
        if(!CollectionUtils.isEmpty(user.getDevices()))
            dto.setDevices(user.getDevices().stream().map(EnergyDeviceDto::energyDeviceDtoFromEnergyDevice).collect(Collectors.toList()));
        else
            dto.setDevices(new ArrayList<>());

        return dto;
    }

    public static User userFromUserDto(UserDto dto){
        return new User(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getUserType(), new ArrayList<>());
    }
}
