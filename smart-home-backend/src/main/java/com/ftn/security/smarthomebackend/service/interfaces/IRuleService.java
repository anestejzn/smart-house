package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.RuleResponse;
import com.ftn.security.smarthomebackend.enums.DeviceType;
import com.ftn.security.smarthomebackend.exception.RuleExistException;

import java.util.List;

public interface IRuleService {
    List<RuleResponse> getAllRules();

    RuleResponse saveRule(String regex, DeviceType deviceType) throws RuleExistException;
}
