package com.utcn.ds2022_30643_moldovan_andrei_1_backend.service;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.EnergyDeviceDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.UserDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.User;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.exception.UserNotFoundException;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RepositoryFactory factory;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto findById(Integer id){
        return UserDto.userDtoFromUser(factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public List<UserDto> findAll(){
        return factory.createUserRepository().findAll().stream().map(UserDto::userDtoFromUser).collect(Collectors.toList());
    }

    @Transactional
    public UserDto insert(UserDto dto){
        User user = UserDto.userFromUserDto(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserDto.userDtoFromUser(factory.createUserRepository().save(user));
    }

    @Transactional
    public UserDto update(UserDto dto) throws UserNotFoundException{
        User user = UserDto.userFromUserDto(dto);
        User userDB = factory.createUserRepository().findById(dto.getId()).orElseThrow(UserNotFoundException::new);
        user.setDevices(userDB.getDevices());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserDto.userDtoFromUser(factory.createUserRepository().save(user));
    }

    @Transactional
    public void remove(Integer id){
        Optional<User> user = factory.createUserRepository().findById(id);
        user.ifPresent(value -> factory.createUserRepository().remove(value));
    }

    @Transactional
    public UserDto addEnergyDevice(Integer id, EnergyDeviceDto deviceDto) throws UserNotFoundException{
        User user = factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new);
        EnergyDevice device = EnergyDeviceDto.energyDeviceFromEnergyDeviceDto(deviceDto);
        user.addDevice(device);
        return UserDto.userDtoFromUser(factory.createUserRepository().save(user));
    }

    @Transactional
    public UserDto removeEnergyDevice(Integer id, EnergyDeviceDto deviceDto) throws UserNotFoundException{
        User user = factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new);
        EnergyDevice device = EnergyDeviceDto.energyDeviceFromEnergyDeviceDto(deviceDto);
        user.removeDevice(device);
        return UserDto.userDtoFromUser(factory.createUserRepository().save(user));
    }
}
