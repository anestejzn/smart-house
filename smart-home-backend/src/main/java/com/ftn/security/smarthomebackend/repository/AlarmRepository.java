package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query("select distinct a from Alarm a where a.deviceId=?1 and a.dateTime >= ?2")
    List<Alarm> getFilteredAlarms(Long deviceId, LocalDateTime bottomDate);
}
