package com.utcn.ds2022_30643_moldovan_andrei_1_backend.controller;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.MeasurementDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.LogIn;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.LogInService;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/measurements")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;
    private final LogInService logInService;

    @GetMapping()
    public List<MeasurementDto> findMeasurements(){
        return measurementService.findAll();
    }

    @GetMapping(value = "/{id}")
    public MeasurementDto findMeasurementById(@PathVariable("id") Integer id){
        return measurementService.findById(id);
    }

    @GetMapping(value = "/by_device/{id}")
    public List<MeasurementDto> findMeasurementByDeviceId(@PathVariable("id") Integer id){
        return measurementService.findByDeviceId(id);
    }

    @PostMapping()
    public MeasurementDto insert(@RequestBody MeasurementDto measurement, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return null;
        if(log.get().getUser().getUserType())
            return measurementService.insert(measurement);
        return null;
    }

    @PutMapping()
    public MeasurementDto update(@RequestBody MeasurementDto measurement){
        return measurementService.update(measurement);
    }

    @DeleteMapping(value = "/{id}")
    public boolean remove(@PathVariable("id") Integer id, @RequestHeader("token") String token){
        Optional<LogIn> log = logInService.isLoggedIn(token);
        if(log.isEmpty())
            return false;
        if(log.get().getUser().getUserType()) {
            measurementService.remove(id);
            return true;
        }
        return false;
    }
}
