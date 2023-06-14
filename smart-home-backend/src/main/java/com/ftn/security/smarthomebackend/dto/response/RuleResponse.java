package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.enums.DeviceType;
import com.ftn.security.smarthomebackend.model.Rule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RuleResponse {
    private String regexPattern;
    private DeviceType deviceType;

    public RuleResponse(String regexPattern, DeviceType deviceType) {
        this.regexPattern = regexPattern;
        this.deviceType = deviceType;
    }

    public RuleResponse(Rule rule) {
        this.regexPattern = rule.getRegexPattern();
        this.deviceType = rule.getDeviceType();
    }


}
