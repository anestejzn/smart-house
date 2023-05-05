package com.ftn.security.smarthomebackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.ftn.security.smarthomebackend.util.Constants.WRONG_SECURITY_CODE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyRequest {
    @NotBlank(message = WRONG_SECURITY_CODE)
    @NotNull(message = WRONG_SECURITY_CODE)
    private String verifyId;

    @NotNull(message = WRONG_SECURITY_CODE)
    @Positive(message = WRONG_SECURITY_CODE)
    private int securityCode;
}
