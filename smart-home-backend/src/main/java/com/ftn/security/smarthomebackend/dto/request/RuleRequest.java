package com.ftn.security.smarthomebackend.dto.request;

import com.ftn.security.smarthomebackend.enums.DeviceType;
import com.ftn.security.smarthomebackend.model.Device;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RuleRequest {

    @NotNull(message = "Rule have to have device type.")
    private DeviceType deviceType;
    @NotNull(message = "Rule have to have regex pattern.")
    private String regexPattern;

    public RuleRequest(DeviceType deviceType, String regexPattern) {
        this.deviceType = deviceType;
        this.regexPattern = regexPattern;
    }
}
