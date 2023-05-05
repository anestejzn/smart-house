package com.ftn.security.smarthomebackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.Constants.EMPTY_REASON;
import static com.ftn.security.smarthomebackend.util.Constants.INVALID_ALIAS_NAME;

@Getter
@Setter
@NoArgsConstructor
public class CancelCertificateRequest {
    @NotNull
    @NotBlank(message = INVALID_ALIAS_NAME)
    private String alias;

    @NotNull
    @NotBlank(message = EMPTY_REASON)
    private String cancelReason;
}
