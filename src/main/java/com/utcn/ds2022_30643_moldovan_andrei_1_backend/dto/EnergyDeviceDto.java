package com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.Measurement;
import lombok.Data;

import java.util.List;

@Data
public class EnergyDeviceDto {
    private Integer id;
    private String description;
    private String address;
    private Double threshold;

    public static EnergyDeviceDto energyDeviceDtoFromEnergyDevice(EnergyDevice device){
        EnergyDeviceDto dto = new EnergyDeviceDto();
        dto.setId(device.getId());
        dto.setDescription(device.getDescription());
        dto.setAddress(device.getAddress());
        dto.setThreshold(device.getThreshold());

        return dto;
    }

    public static EnergyDevice energyDeviceFromEnergyDeviceDto(EnergyDeviceDto dto){
        return new EnergyDevice(dto.getId(), dto.getDescription(), dto.getAddress(), dto.getThreshold());
    }
}
