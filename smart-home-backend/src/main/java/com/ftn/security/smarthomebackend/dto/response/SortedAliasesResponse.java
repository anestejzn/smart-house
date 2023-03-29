package com.ftn.security.smarthomebackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
public class SortedAliasesResponse {

    private String alias;

    private boolean valid;

    public SortedAliasesResponse(String alias, boolean valid) {
        this.alias = alias;
        this.valid = valid;
    }
}
