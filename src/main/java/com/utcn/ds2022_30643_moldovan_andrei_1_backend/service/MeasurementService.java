package com.utcn.ds2022_30643_moldovan_andrei_1_backend.service;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.MeasurementDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.Measurement;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.exception.MeasurementNotFoundException;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final RepositoryFactory factory;

    @Transactional
    public MeasurementDto findById(Integer id){
        return MeasurementDto.measurementDtoFromMeasurement(factory.createMeasurementRepository().findById(id).orElseThrow(MeasurementNotFoundException::new));
    }

    @Transactional
    public List<MeasurementDto> findAll(){
        System.out.println(factory.createMeasurementRepository().findAll());
        return factory.createMeasurementRepository().findAll().stream().map(MeasurementDto::measurementDtoFromMeasurement).collect(Collectors.toList());
    }

    @Transactional
    public List<MeasurementDto> findByDeviceId(Integer id){
        System.out.println(factory.createMeasurementRepository().findAll());
        return factory.createMeasurementRepository().findByDevice(factory.createEnergyDeviceRepository().findById(id).orElseThrow()).stream().map(MeasurementDto::measurementDtoFromMeasurement).collect(Collectors.toList());
    }

    @Transactional
    public MeasurementDto insert(MeasurementDto dto){
        Measurement measurement = MeasurementDto.measurementFromMeasurementDto(dto);
        return MeasurementDto.measurementDtoFromMeasurement(factory.createMeasurementRepository().save(measurement));
    }

    @Transactional
    public MeasurementDto update(MeasurementDto dto){
        Measurement measurement = MeasurementDto.measurementFromMeasurementDto(dto);
        return MeasurementDto.measurementDtoFromMeasurement(factory.createMeasurementRepository().save(measurement));
    }

    @Transactional
    public void remove(Integer id){
        Optional<Measurement> measurement = factory.createMeasurementRepository().findById(id);
        measurement.ifPresent(value -> factory.createMeasurementRepository().remove(value));
    }
}
