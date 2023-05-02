package com.ftn.security.smarthomebackend.dto.request;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.ftn.security.smarthomebackend.util.Constants.*;
import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.NULL_VALUE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewRealEstateRequest {

    @NotBlank(message = EMPTY_RE_NAME)
    @Pattern(message = EMPTY_RE_NAME, regexp = LEGIT_RE_NAME_REG)
    private String name;

    @Pattern(message = SQ_AREA_MESSAGE, regexp = LEGIT_RE_SQ_AREA_REG)
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

    @Positive(message = "Id must be greater than 0.")
    private Long ownerId;

    @NotNull(message = NULL_VALUE)
    private Long[] tenantsIds;

}
