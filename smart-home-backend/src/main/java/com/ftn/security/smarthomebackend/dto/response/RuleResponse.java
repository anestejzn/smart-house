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
    private String regex;
    private DeviceType deviceType;

    public RuleResponse(String regex, DeviceType deviceType) {
        this.regex = regex;
        this.deviceType = deviceType;
    }

    public RuleResponse(Rule rule) {
        this.regex = rule.getRegexPattern();
        this.deviceType = rule.getDeviceType();
    }


}
