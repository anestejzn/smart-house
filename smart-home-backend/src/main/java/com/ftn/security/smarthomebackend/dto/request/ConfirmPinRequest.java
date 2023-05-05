package com.ftn.security.smarthomebackend.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmPinRequest {
    @NotNull(message = EMPTY_EMAIL)
    @Size(max = 320, message = TOO_LONG_EMAIL)
    @Email(message = WRONG_EMAIL)
    private String email;

    @NotNull(message = WRONG_SECURITY_CODE)
    @Pattern(regexp = PIN_CODE_REG, message = WRONG_SECURITY_CODE)
    private String pin;
}
