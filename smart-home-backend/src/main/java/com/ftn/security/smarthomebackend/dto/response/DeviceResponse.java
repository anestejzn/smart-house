package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.enums.DeviceType;
import com.ftn.security.smarthomebackend.model.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

import static com.ftn.security.smarthomebackend.util.PictureHandler.convertPictureToBase64ByName;

@Getter
@Setter
@NoArgsConstructor
public class DeviceResponse {

    private Long id;

    private DeviceType deviceType;

    private String name;

    private String filterRegex;

    private int periodRead;

    private String base64Photo;

    public DeviceResponse(Long id,
                          DeviceType deviceType,
                          String name,
                          String filterRegex,
                          int periodRead,
                          String photoName
    ) {
        this.id = id;
        this.deviceType = deviceType;
        this.name = name;
        this.filterRegex = filterRegex;
        this.periodRead = periodRead;
        this.base64Photo = convertPictureToBase64ByName(photoName);
    }

    public DeviceResponse(Device device) {
        this.id = device.getId();
        this.deviceType = device.getDeviceType();
        this.name = device.getName();
        this.filterRegex = device.getFilterRegex();
        this.periodRead = device.getPeriodRead();
        this.base64Photo = convertPictureToBase64ByName(device.getPhotoPath());
    }

    public static List<DeviceResponse> fromDevicesToResponses(List<Device> devices) {
        List<DeviceResponse> responses = new LinkedList<>();
        devices.forEach(device -> {
            responses.add(new DeviceResponse(device));
        });

        return responses;
    }

}
