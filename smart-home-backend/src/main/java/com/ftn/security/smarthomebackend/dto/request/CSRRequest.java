package com.ftn.security.smarthomebackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.ftn.security.smarthomebackend.util.Constants.*;
import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.TOO_LONG_EMAIL;

@Getter
@Setter
@NoArgsConstructor
public class CSRRequest {
    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 320, message = TOO_LONG_EMAIL)
    @Email(message = WRONG_EMAIL)
    private String email;
}
