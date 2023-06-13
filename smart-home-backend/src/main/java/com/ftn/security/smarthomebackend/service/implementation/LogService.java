package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.LogResponse;
import com.ftn.security.smarthomebackend.model.Alarm;
import com.ftn.security.smarthomebackend.model.Log;
import com.ftn.security.smarthomebackend.repository.mongo.LogRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IAlarmService;
import com.ftn.security.smarthomebackend.service.interfaces.ILogService;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService implements ILogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private IAlarmService alarmService;

    private final KieContainer kieContainer;
    private final KieSession kieSession;

    @Autowired
    public LogService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        this.kieSession = kieContainer.newKieSession("smartSession");
    }

    @Override
    public void saveLog(Log log) {
        logRepository.save(log);
    }

    @Override
    public void generateLog(String logMessage, LogLevel logLevel) {
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(LocalDateTime.now(), logLevel, loggerName, logMessage);
        logRepository.save(log);

        kieSession.getAgenda().getAgendaGroup("admin_alarms").setFocus();

        kieSession.insert(log);

        kieSession.fireAllRules();

        Collection<Alarm> results = (Collection<Alarm>) kieSession.getObjects(new ClassObjectFilter(Alarm.class));
        if(results.size() != 0){
            alarmService.saveAll(results.stream().toList());
        }
    }

    @Override
    public List<LogResponse> getAllLogs() {
        return logRepository.findAllByDateTimeNotNullOrderByDateTimeDesc()
                .stream().map(LogResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<LogResponse> getFilterLogs(String regex, String dateTime, String logLevel) {
        if(regex.equals("")){
            if(dateTime.equals("All") && logLevel.equals("All")){
                return this.getAllLogs();
            }
            else{
                return noRegexWithDateTimeAndLogLevel(dateTime, logLevel);
            }
        }
        else{
            byte[] decodedBytes = Base64.getDecoder().decode(regex);
            regex = new String(decodedBytes);
            return regexWithDateTimeAndLogLevel(dateTime, logLevel, regex);
        }
    }

    private List<LogResponse> noRegexWithDateTimeAndLogLevel(String dateTime, String logLevel){
        LocalDateTime second = getDateTime(dateTime);
        if(logLevel.equals("All")){ //all log levels, dateTime selected
           return logRepository
                   .findAllByDateTimeBetweenOrderByDateTimeDesc(second, LocalDateTime.now())
                   .stream().map(LogResponse::new).collect(Collectors.toList());
       }
       else if(dateTime.equals("All")){//all date time, log level selected
           return logRepository
                   .findAllByLogLevelOrderByDateTimeDesc(logLevel)
                   .stream().map(LogResponse::new).collect(Collectors.toList());
       }
       else{//selected date time, log level selected
           return logRepository
                   .findAllByDateTimeBetweenAndLogLevelOrderByDateTimeDesc(second, LocalDateTime.now(), logLevel)
                   .stream().map(LogResponse::new).collect(Collectors.toList());
       }
    }


    private List<LogResponse> regexWithDateTimeAndLogLevel(String dateTime, String logLevel, String regex){
        LocalDateTime second = getDateTime(dateTime);
        if(logLevel.equals("All")) {

            return logRepository
                    .findAllByDateTimeBetweenAndLogMessageRegexOrderByDateTimeDesc(second, LocalDateTime.now(), regex)
                    .stream().map(LogResponse::new).collect(Collectors.toList());
        }
        else if(dateTime.equals("All")){
            return logRepository
                    .findAllByLogLevelAndLogMessageRegexOrderByDateTimeDesc(logLevel, regex)
                    .stream().map(LogResponse::new).collect(Collectors.toList());
        }
        else{
            return logRepository
                    .findAllByDateTimeBetweenAndLogLevelAndLogMessageRegexOrderByDateTimeDesc(second, LocalDateTime.now(), logLevel, regex)
                    .stream().map(LogResponse::new).collect(Collectors.toList());
        }
    }

    private LocalDateTime getDateTime(String dateTime){
        return switch (dateTime) {
            case "This week" -> LocalDateTime.now().minusDays(7);
            case "Two weeks ago" -> LocalDateTime.now().minusDays(14);
            case "Today" -> LocalDateTime.now().with(LocalTime.MIN);
            default -> LocalDateTime.now().minusMonths(1);
        };
    }
}
