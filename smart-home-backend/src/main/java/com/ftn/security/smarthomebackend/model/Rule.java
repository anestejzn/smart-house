package com.ftn.security.smarthomebackend.model;

import com.ftn.security.smarthomebackend.enums.DeviceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="regex_pattern")
    private String regexPattern;

    @Column(name="device_type")
    private DeviceType deviceType;

    public Rule(String regexPattern, DeviceType deviceType) {
        this.regexPattern = regexPattern;
        this.deviceType = deviceType;
    }
}
