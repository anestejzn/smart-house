package com.ftn.security.smarthomebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.security.smarthomebackend.enums.DeviceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "device")
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

    @Column(name="photo_path")
    private String photoPath;

    @Column(name = "last_read", nullable = true)
    private LocalDateTime lastRead;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "real_estate_id", referencedColumnName = "id")
    protected RealEstate realEstate;

    public Device(DeviceType deviceType, String name, String filterRegex, int periodRead, RealEstate realEstate, String photoPath) {
        this.deviceType = deviceType;
        this.name = name;
        this.filterRegex = filterRegex;
        this.periodRead = periodRead;
        this.realEstate = realEstate;
        this.photoPath = photoPath;
        this.lastRead = null;
    }
}
