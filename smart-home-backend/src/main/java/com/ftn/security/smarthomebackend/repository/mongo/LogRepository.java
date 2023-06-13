package com.ftn.security.smarthomebackend.repository.mongo;

import com.ftn.security.smarthomebackend.model.Log;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends MongoRepository<Log, Long>{

    List<Log> findAllByDateTimeNotNullOrderByDateTimeDesc();

    List<Log> findAllByDateTimeBetweenAndLogLevelOrderByDateTimeDesc(LocalDateTime startDateTime, LocalDateTime endDateTime, String logLevel);

    List<Log> findAllByLogLevelOrderByDateTimeDesc(String logLevel);

    List<Log> findAllByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Log> findAllByDateTimeBetweenAndLogLevelAndLogMessageRegexOrderByDateTimeDesc(LocalDateTime startDateTime, LocalDateTime endDateTime, String logLevel, String regex);

    List<Log> findAllByLogLevelAndLogMessageRegexOrderByDateTimeDesc(String logLevel, String regex);


    List<Log> findAllByDateTimeBetweenAndLogMessageRegexOrderByDateTimeDesc(LocalDateTime startDateTime, LocalDateTime endDateTime, String regex);

}
