package com.ftn.security.smarthomebackend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.Constants.MISSING_ID;
import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.NULL_VALUE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateTenantsRequest {

    @NotNull(message = MISSING_ID)
    @Positive(message = MISSING_ID)
    private Long id;

    @NotNull(message = NULL_VALUE)
    private Long[] tenantsIds;

}