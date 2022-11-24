package com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.Measurement;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class MeasurementDto {
    private Integer id;
    private Date date;
    private Time time;
    private Double consumption;
    private EnergyDeviceDto device;

    public static MeasurementDto measurementDtoFromMeasurement(Measurement measurement){
        MeasurementDto dto = new MeasurementDto();
        dto.setId(measurement.getId());
        dto.setConsumption(measurement.getConsumption());
        dto.setDate(measurement.getDate());
        dto.setTime(measurement.getTime());
        dto.setDevice(EnergyDeviceDto.energyDeviceDtoFromEnergyDevice(measurement.getDevice()));

        return dto;
    }

    public static Measurement measurementFromMeasurementDto(MeasurementDto dto){
        return new Measurement(dto.getId(), dto.getDate(), dto.getTime(),  dto.getConsumption(), EnergyDeviceDto.energyDeviceFromEnergyDeviceDto(dto.getDevice()));
    }
}
