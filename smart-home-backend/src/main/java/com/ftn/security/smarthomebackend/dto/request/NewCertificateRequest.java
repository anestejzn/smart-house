package com.ftn.security.smarthomebackend.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.Constants.VALIDITY_PERIOD_MUST_BE_NUMBER;
import static com.ftn.security.smarthomebackend.util.Constants.VALID_PERIOD_REG;
import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.EMPTY_STRING;
import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.NULL_VALUE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCertificateRequest {
    @NotNull(message = NULL_VALUE)
    @PositiveOrZero
    private Long csrId;

    @NotNull(message = NULL_VALUE)
    @NotBlank(message = EMPTY_STRING)
    private String intermediateAlias;

    @NotNull(message = NULL_VALUE)
    @NotBlank(message = EMPTY_STRING)
    @Pattern(regexp=VALID_PERIOD_REG, message = VALIDITY_PERIOD_MUST_BE_NUMBER)
    private String validityPeriod;

    @NotNull(message = NULL_VALUE)
    private String[] keyUsages;

    @NotNull(message = NULL_VALUE)
    private String[] extendedKeyUsages;
}
