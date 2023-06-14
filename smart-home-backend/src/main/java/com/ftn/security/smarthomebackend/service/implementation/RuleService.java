package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.RuleResponse;
import com.ftn.security.smarthomebackend.enums.DeviceType;
import com.ftn.security.smarthomebackend.model.Rule;
import com.ftn.security.smarthomebackend.repository.RuleRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleService implements IRuleService {
    @Autowired
    private RuleRepository ruleRepository;
    @Override
    public List<RuleResponse> getAllRules() {

        return ruleRepository.findAll().stream().map(RuleResponse::new).collect(Collectors.toList());
    }
    @Override
    public RuleResponse saveRule(String regex, DeviceType deviceType){
        Rule saved = ruleRepository.save(new Rule(regex, deviceType));

        return new RuleResponse(saved);
    }
}
