package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.LogResponse;
import com.ftn.security.smarthomebackend.model.Log;
import org.springframework.boot.logging.LogLevel;

import java.util.List;

public interface ILogService {

    void saveLog(Log log);
    void generateLog(String logMessage, LogLevel logLevel);

    List<LogResponse> getAllLogs();

    List<LogResponse> getFilterLogs(String regex, String dateTime, String logLevel);
}
