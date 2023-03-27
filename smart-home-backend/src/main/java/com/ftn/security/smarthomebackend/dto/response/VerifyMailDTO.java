package com.ftn.security.smarthomebackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerifyMailDTO {

    private int securityCode;

    private String hashId;

    public VerifyMailDTO(int securityCode, String hashId) {
        this.securityCode = securityCode;
        this.hashId = hashId;
    }
}
