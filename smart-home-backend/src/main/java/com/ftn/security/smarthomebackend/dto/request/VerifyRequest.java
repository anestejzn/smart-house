package com.ftn.security.smarthomebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.ftn.security.smarthomebackend.util.Constants.WRONG_SECURITY_CODE;

@Getter
@Setter
public class VerifyRequest {

    @NotBlank(message = WRONG_SECURITY_CODE)
    private String verifyId;

    @NotNull(message = WRONG_SECURITY_CODE)
    @Positive(message = WRONG_SECURITY_CODE)
    private int securityCode;

    public VerifyRequest(String verifyId, int securityCode) {
        this.verifyId = verifyId;
        this.securityCode = securityCode;
    }
}
