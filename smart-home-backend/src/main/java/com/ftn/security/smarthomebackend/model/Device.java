package com.ftn.security.smarthomebackend.model;

import com.ftn.security.smarthomebackend.enums.DeviceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_type")
    private DeviceType deviceType;

    @Column(name = "name")
    private String name;

    @Column(name="filter_regex")
    private String filterRegex;

    @Column(name="period_read")
    private int periodRead;


    public Device(DeviceType deviceType, String name, String filterRegex, int periodRead) {
        this.deviceType = deviceType;
        this.name = name;
        this.filterRegex = filterRegex;
        this.periodRead = periodRead;
    }
}
