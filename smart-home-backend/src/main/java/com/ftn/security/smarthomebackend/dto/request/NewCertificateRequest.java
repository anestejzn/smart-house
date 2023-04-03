package com.ftn.security.smarthomebackend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCertificateRequest {
    @NotNull(message = NULL_VALUE)
    private Long csrId;

    @NotNull(message = NULL_VALUE)
    @NotBlank(message = EMPTY_STRING)
    private String intermediateAlias;

    @NotNull(message = NULL_VALUE)
    @NotBlank(message = EMPTY_STRING)
    private String validityPeriod;

    @NotNull(message = NULL_VALUE)
    private String[] keyUsages;

    @NotNull(message = NULL_VALUE)
    private String[] extendedKeyUsages;
}
