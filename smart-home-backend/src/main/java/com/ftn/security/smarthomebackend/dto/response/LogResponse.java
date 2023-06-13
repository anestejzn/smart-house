package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.model.Log;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LogResponse {

    private LocalDateTime dateTime;

    private LogLevel logLevel;

    private String loggerName;

    private String logMessage;

    public LogResponse(Log log){
        this.dateTime = log.getDateTime();
        this.logLevel = log.getLogLevel();
        this.loggerName = log.getLoggerName();
        this.logMessage = log.getLogMessage();
    }
}
