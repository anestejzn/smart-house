package com.ftn.security.smarthomebackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import static com.ftn.security.smarthomebackend.util.Constants.*;
import static com.ftn.security.smarthomebackend.util.Constants.POSITIVE_WHOLE_NUMBER_REG;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateRequest {

    @NotBlank(message = EMPTY_RE_NAME)
    @Pattern(message = EMPTY_RE_NAME, regexp = LEGIT_RE_NAME_REG)
    private String name;

    @Range(message = SQ_AREA_MESSAGE, min = 10, max = 600)
    protected Integer sqMeters;

    @NotBlank(message = CITY_ERROR_MESSAGE)
    @Pattern(message = CITY_ERROR_MESSAGE, regexp = LEGIT_RE_CITY_AND_STREET_REG)
    protected String city;

    @NotBlank(message = STREET_ERROR_MESSAGE)
    @Pattern(message = STREET_ERROR_MESSAGE, regexp = LEGIT_RE_CITY_AND_STREET_REG)
    protected String street;

    @NotBlank(message = "Number must be positive")
    @Pattern(regexp = POSITIVE_WHOLE_NUMBER_REG, message = "Number must be positive")
    protected String streetNum;

}
