package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.RealEstateResponse;
import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.OwnerAndTenantOverlapException;
import com.ftn.security.smarthomebackend.model.RealEstate;
import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.repository.RealEstateRepository;
import com.ftn.security.smarthomebackend.service.interfaces.ILogService;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;
import com.ftn.security.smarthomebackend.service.interfaces.IRegularUserService;
import com.ftn.security.smarthomebackend.util.LogGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Lazy;
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

    @Lazy
    @Autowired
    private IRegularUserService regularUserService;

    @Lazy
    @Autowired
    private ILogService logService;

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
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.REAL_ESTATE));
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

        RealEstate edited = realEstateRepository.save(realEstate);
        logService.generateLog(LogGenerator.editedRealEstate(edited.getId()), LogLevel.INFO);

        return new RealEstateResponse(edited);
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

        logService.generateLog(LogGenerator.editedOwnerRealEstate(realEstate.getId()), LogLevel.INFO);
        return new RealEstateResponse(realEstateRepository.save(realEstate));
    }

    @Override
    public boolean delete(Long id) throws EntityNotFoundException {
        RealEstate realEstate =  getRealEstateById(id);
        realEstateRepository.delete(realEstate);
        logService.generateLog(LogGenerator.deletedRealEstate(id), LogLevel.INFO);

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
        logService.generateLog(LogGenerator.editedTenantsRealEstate(id), LogLevel.INFO);

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

        logService.generateLog(LogGenerator.createdRealEstate(), LogLevel.INFO);

        return true;
    }
    
    @Override
    public boolean block(Long userId) throws EntityNotFoundException, CannotPerformActionException {
        RegularUser regularUser = regularUserService.getRegularUserById(userId);
        if (regularUser.getRole().getRoleName().equalsIgnoreCase("ROLE_OWNER")) {
            this.checkIfOwnerHasRealEstates(userId);
        } else {
            this.checkIfTenantIsInRealEstate(userId);
        }

        return regularUserService.block(regularUser);
    }

    @Override
    public boolean unblock(Long userId) throws EntityNotFoundException {

        return regularUserService.unblock(userId);
    }

    @Override
    public List<RealEstate> getRealEstatesForOwner(Long userId) throws EntityNotFoundException {
        RegularUser regularUser = regularUserService.getRegularUserById(userId);

        return realEstateRepository.getRealEstatesForOwner(userId);
    }

    private void checkIfOwnerHasRealEstates(Long userId) throws CannotPerformActionException {
        List<RealEstate> realEstates = realEstateRepository.getAll();

        for (RealEstate realEstate : realEstates) {
            if (Objects.equals(realEstate.getOwner().getId(), userId)) {
                throw new CannotPerformActionException("Owner cannot be blocked while having active real estate.");
            }
        }
    }

    private void checkIfTenantIsInRealEstate(Long userId) throws CannotPerformActionException {
        List<RealEstate> realEstates = realEstateRepository.getAll();

        for (RealEstate realEstate : realEstates) {
            for (RegularUser user : realEstate.getTenants()) {
                if (Objects.equals(user.getId(), userId))
                    throw new CannotPerformActionException("Tenant cannot be blocked while being active tenant in real estate.");
            }
        }
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
