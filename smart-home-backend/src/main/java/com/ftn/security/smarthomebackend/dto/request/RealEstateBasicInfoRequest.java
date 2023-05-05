package com.ftn.security.smarthomebackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.ftn.security.smarthomebackend.util.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateBasicInfoRequest extends RealEstateRequest {

    @NotNull(message = MISSING_ID)
    @Positive(message = MISSING_ID)
    private Long id;

}
