package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
