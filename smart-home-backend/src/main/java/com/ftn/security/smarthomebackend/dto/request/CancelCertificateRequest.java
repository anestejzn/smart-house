package com.ftn.security.smarthomebackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.ftn.security.smarthomebackend.util.Constants.*;

@Getter
@Setter
@NoArgsConstructor
public class CancelCertificateRequest {
    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 1024, message = TOO_LONG_EMAIL)
    @Email(message = WRONG_EMAIL)
    private String alias;
    @NotBlank(message = EMPTY_REASON)
    private String cancelReason;
}
