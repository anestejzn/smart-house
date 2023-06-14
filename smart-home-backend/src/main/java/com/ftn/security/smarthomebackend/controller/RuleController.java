package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.RuleRequest;
import com.ftn.security.smarthomebackend.dto.response.RuleResponse;
import com.ftn.security.smarthomebackend.service.implementation.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rules")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority='SAVE_RULE'")
    public RuleResponse saveRule(@RequestBody RuleRequest ruleRequest){
        return ruleService.saveRule(ruleRequest.getRegexPattern(), ruleRequest.getDeviceType());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority='GET_RULES'")
    public List<RuleResponse> getAllRules(){
        return ruleService.getAllRules();
    }
}
