package com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.Measurement;
import lombok.Data;

import java.sql.Date;

@Data
public class MeasurementDto {
    private Integer id;
    private Date date;
    private Double consumption;
    private EnergyDeviceDto device;

    public static MeasurementDto measurementDtoFromMeasurement(Measurement measurement){
        MeasurementDto dto = new MeasurementDto();
        dto.setId(measurement.getId());
        dto.setConsumption(measurement.getConsumption());
        dto.setDate(measurement.getDate());
        dto.setDevice(EnergyDeviceDto.energyDeviceDtoFromEnergyDevice(measurement.getDevice()));

        return dto;
    }

    public static Measurement measurementFromMeasurementDto(MeasurementDto dto){
        return new Measurement(dto.getId(), dto.getDate(),  dto.getConsumption(), EnergyDeviceDto.energyDeviceFromEnergyDeviceDto(dto.getDevice()));
    }
}
