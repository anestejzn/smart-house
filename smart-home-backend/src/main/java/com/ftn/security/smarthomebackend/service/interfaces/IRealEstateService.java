package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.RealEstateResponse;
import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.OwnerAndTenantOverlapException;
import com.ftn.security.smarthomebackend.model.RealEstate;

import java.util.List;

public interface IRealEstateService {
    List<RealEstateViewResponse> filterRealEstates(boolean ascending, String sqArea, Long owner);

    boolean createRealEstate(String name, Integer sqMeters, String city, String street, String streetNum, Long ownerId, Long[] tenantsIds) throws EntityNotFoundException, OwnerAndTenantOverlapException;

    RealEstate getRealEstateById(Long id) throws EntityNotFoundException;

    RealEstateResponse getRealEstate(Long id) throws EntityNotFoundException;

    RealEstateResponse editBasicInfo(Long id, String name, Integer sqMeters, String city, String street, String streetNum) throws EntityNotFoundException;

    RealEstateResponse editOwnership(Long id, Long ownerId, Long[] tenantsIds) throws EntityNotFoundException, OwnerAndTenantOverlapException;

    boolean delete(Long id) throws EntityNotFoundException;

    RealEstateResponse editTenants(Long id, Long[] tenantsIds) throws EntityNotFoundException, OwnerAndTenantOverlapException;

    List<RealEstateViewResponse> filterRealEstatesTenant(boolean ascending, String sqArea, Long tenantId);

    boolean block(Long id) throws EntityNotFoundException, CannotPerformActionException;

    boolean unblock(Long id) throws EntityNotFoundException;

    List<RealEstate> getRealEstatesForOwner(Long userId) throws EntityNotFoundException;
}
