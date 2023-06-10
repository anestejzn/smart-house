package com.ftn.security.smarthomebackend.dto.request;

import com.ftn.security.smarthomebackend.enums.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.NULL_VALUE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequest {

    @NotNull(message = "Device type cannot be empty.")
    private DeviceType deviceType;

    @Pattern(regexp = "[a-zA-Z0-9 ]{1,30}", message = "Device name cannot be empty.")
    private String name;

    @NotBlank(message = "Filter regex cannot be empty.")
    private String filterRegex;

    @NotNull(message = NULL_VALUE)
    @PositiveOrZero(message = "Period read must be positive number.")
    private int periodRead;

    private Long realEstateId;      //ovo moze biti null zavisno od situacije
}
