package com.utcn.ds2022_30643_moldovan_andrei_1_backend.controller;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.EnergyDeviceDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.MeasurementDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.EnergyDeviceService;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/devices")
@RequiredArgsConstructor
public class EnergyDeviceController {
    private final EnergyDeviceService deviceService;

    private final LogInService logInService;

    @GetMapping()
    public List<EnergyDeviceDto> findDevices(){
        return deviceService.findAll();
    }

    @GetMapping(value = "/{id}")
    public EnergyDeviceDto findDeviceById(@PathVariable("id") Integer id){
        return deviceService.findById(id);
    }

    @PostMapping()
    public EnergyDeviceDto insert(@RequestBody EnergyDeviceDto device, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return deviceService.insert(device);
        return null;
    }

    @PutMapping()
    public EnergyDeviceDto update(@RequestBody EnergyDeviceDto device, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return deviceService.update(device);
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public boolean remove(@PathVariable("id") Integer id, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return false;
        if(log.get().getUser().getUserType()) {
            deviceService.remove(id);
            return true;
        }
        return false;
    }

    @PostMapping("/{id}/{consumption}")
    public MeasurementDto addMeasurement(@PathVariable("id") Integer id, @PathVariable("consumption") Double consumption, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return deviceService.addMeasurement(deviceService.findById(id), consumption);
        return null;
    }
}
