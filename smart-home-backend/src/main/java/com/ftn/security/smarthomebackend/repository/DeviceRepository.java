package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
