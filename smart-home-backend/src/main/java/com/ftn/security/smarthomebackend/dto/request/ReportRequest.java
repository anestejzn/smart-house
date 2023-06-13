package com.ftn.security.smarthomebackend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.ftn.security.smarthomebackend.util.Constants.MISSING_ID;
import static com.ftn.security.smarthomebackend.util.Constants.WRONG_DATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {

    @NotNull(message = MISSING_ID)
    @Positive(message = MISSING_ID)
    private Long userId;

    @NotNull(message = WRONG_DATE)
    private LocalDateTime startTime;

    @NotNull(message = WRONG_DATE)
    private LocalDateTime endTime;
}
