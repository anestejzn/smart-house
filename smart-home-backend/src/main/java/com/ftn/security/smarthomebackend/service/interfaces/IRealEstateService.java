package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.RealEstateResponse;
import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.RealEstate;

import java.util.List;

public interface IRealEstateService {
    List<RealEstateViewResponse> filterRealEstates(boolean ascending, String sqArea, Long owner);

    boolean createRealEstate(String name, Integer sqMeters, String city, String street, String streetNum, Long ownerId, Long[] tenantsIds) throws EntityNotFoundException;

    RealEstate getRealEstateById(Long id) throws EntityNotFoundException;

    RealEstateResponse getRealEstate(Long id) throws EntityNotFoundException;
}
