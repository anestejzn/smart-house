package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> getDeviceById(Long id);

    @Query("select distinct d from Device d where d.realEstate.id=?1")
    List<Device> getDevicesPerRealEstate(Long id);
}
