package com.ftn.security.smarthomebackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
@NoArgsConstructor
public class LogFilterRequest {

    private String regex;
    private String dateTime;
    private String logLevel;

    public LogFilterRequest(String regex, String dateTime, String logLevel) {
        this.regex = regex;
        this.dateTime = dateTime;
        this.logLevel = logLevel;
    }
}
