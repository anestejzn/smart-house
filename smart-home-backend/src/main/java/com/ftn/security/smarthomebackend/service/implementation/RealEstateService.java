package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.RealEstateResponse;
import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.OwnerAndTenantOverlapException;
import com.ftn.security.smarthomebackend.model.RealEstate;
import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.repository.RealEstateRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;
import com.ftn.security.smarthomebackend.service.interfaces.IRegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse.fromListToResponse;
import static com.ftn.security.smarthomebackend.util.Constants.FILTER_BY_ALL;
import static com.ftn.security.smarthomebackend.util.Helper.extractSqMetersNumbers;

@Service
public class RealEstateService implements IRealEstateService {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Autowired
    private IRegularUserService regularUserService;

    public List<RealEstateViewResponse> filterRealEstates(boolean ascending, String sqArea, Long ownerId) {

        return fromListToResponse(getRealEstatesList(ascending, sqArea, ownerId));
    }

    @Override
    public List<RealEstateViewResponse> filterRealEstatesTenant(boolean ascending, String sqArea, Long tenantId) {
        List<RealEstate> realEstates = getRealEstatesList(ascending, sqArea, FILTER_BY_ALL);
        List<RealEstate> filteredByTenants = new LinkedList<>();

        for (RealEstate realEstate : realEstates) {
            for (RegularUser tenant : realEstate.getTenants()) {
                if (Objects.equals(tenant.getId(), tenantId)) {
                    filteredByTenants.add(realEstate);
                }
            }
        }

        return fromListToResponse(filteredByTenants);
    }

    @Override
    public RealEstate getRealEstateById(Long id) throws EntityNotFoundException {
        return realEstateRepository.getRealEstateById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.USER));
    }

    @Override
    public RealEstateResponse getRealEstate(Long id) throws EntityNotFoundException {

        return new RealEstateResponse(getRealEstateById(id));
    }

    @Override
    public RealEstateResponse editBasicInfo(Long id,
                                 String name,
                                 Integer sqMeters,
                                 String city,
                                 String street,
                                 String streetNum
    ) throws EntityNotFoundException {
        RealEstate realEstate = getRealEstateById(id);
        realEstate.setName(name);
        realEstate.setSqMeters(sqMeters);
        realEstate.setCity(city);
        realEstate.setStreet(street);
        realEstate.setStreetNum(streetNum);

        return new RealEstateResponse(realEstateRepository.save(realEstate));
    }

    @Override
    public RealEstateResponse editOwnership(Long id, Long ownerId, Long[] tenantsIds)
            throws EntityNotFoundException, OwnerAndTenantOverlapException {
        RegularUser owner = regularUserService.getRegularUserById(ownerId);
        List<RegularUser> tenants = extractTenants(tenantsIds);
        checkOwnerAndTenantOverlap(owner, tenants);

        RealEstate realEstate = getRealEstateById(id);
        realEstate.setOwner(owner);
        realEstate.setTenants(tenants);

        return new RealEstateResponse(realEstateRepository.save(realEstate));
    }

    @Override
    public boolean delete(Long id) throws EntityNotFoundException {
        RealEstate realEstate =  getRealEstateById(id);
        realEstateRepository.delete(realEstate);

        return true;
    }

    @Override
    public RealEstateResponse editTenants(Long id, Long[] tenantsIds)
            throws EntityNotFoundException, OwnerAndTenantOverlapException
    {
        List<RegularUser> tenants = extractTenants(tenantsIds);
        RealEstate realEstate = getRealEstateById(id);
        checkOwnerAndTenantOverlap(realEstate.getOwner(), tenants);

        realEstate.setTenants(tenants);

        return new RealEstateResponse(realEstateRepository.save(realEstate));
    }

    @Override
    public boolean createRealEstate(String name,
                                    Integer sqMeters,
                                    String city,
                                    String street,
                                    String streetNum,
                                    Long ownerId,
                                    Long[] tenantsIds
    ) throws EntityNotFoundException, OwnerAndTenantOverlapException {
        RegularUser owner = regularUserService.getRegularUserById(ownerId);
        List<RegularUser> tenants = extractTenants(tenantsIds);

        checkOwnerAndTenantOverlap(owner, tenants);
        realEstateRepository.save(
                new RealEstate(
                        name,
                        sqMeters,
                        city,
                        street,
                        streetNum,
                        owner,
                        tenants
                ));

        return true;
    }

    private List<RegularUser> extractTenants(Long[] tenantsIds) throws EntityNotFoundException {
        List<RegularUser> tenants = new LinkedList<>();
        for (Long id : tenantsIds) {
            tenants.add(regularUserService.getRegularUserById(id));
        }

        return tenants;
    }

    private void checkOwnerAndTenantOverlap(RegularUser owner, List<RegularUser> tenants) throws OwnerAndTenantOverlapException {
        for (RegularUser tenant : tenants) {
            if (Objects.equals(tenant.getId(), owner.getId())) {
                throw new OwnerAndTenantOverlapException();
            }
        }
    }

    private List<RealEstate> getRealEstatesList(boolean ascending, String sqArea, Long ownerId) {
        List<Integer> nums = extractSqMetersNumbers(sqArea);
        Integer bottomSqArea = nums.get(0);
        Integer topSqArea = nums.get(1);

        if (Objects.equals(ownerId, FILTER_BY_ALL)) {
            return ascending ? realEstateRepository.filterAllRealEstatesAsc(bottomSqArea, topSqArea)
                    : realEstateRepository.filterAllRealEstatesDesc(bottomSqArea, topSqArea);
        } else {
            return ascending ? realEstateRepository.filterRealEstatesByOwnerAsc(bottomSqArea, topSqArea, ownerId)
                    : realEstateRepository.filterRealEstatesByOwnerDesc(bottomSqArea, topSqArea, ownerId);
        }
    }
}