package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.RealEstateResponse;
import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.RealEstate;
import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.repository.RealEstateRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;
import com.ftn.security.smarthomebackend.service.interfaces.IRegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
    public RealEstate getRealEstateById(Long id) throws EntityNotFoundException {
        return realEstateRepository.getRealEstateById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.USER));
    }

    @Override
    public RealEstateResponse getRealEstate(Long id) throws EntityNotFoundException {

        return new RealEstateResponse(getRealEstateById(id));
    }

    @Override
    public boolean createRealEstate(String name,
                                    Integer sqMeters,
                                    String city,
                                    String street,
                                    String streetNum,
                                    Long ownerId,
                                    Long[] tenantsIds
    ) throws EntityNotFoundException {
        RegularUser owner = regularUserService.getRegularUserById(ownerId);
        List<RegularUser> tenants = new LinkedList<>();
        for (Long id : tenantsIds) {
            tenants.add(regularUserService.getRegularUserById(id));
        }

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

    private List<RealEstate> getRealEstatesList(boolean ascending, String sqArea, Long ownerId) {
        List<Integer> nums = extractSqMetersNumbers(sqArea);
        Integer bottomSqArea = nums.get(0);
        Integer topSqArea = nums.get(1);

        if (ownerId == FILTER_BY_ALL) {
            return ascending ? realEstateRepository.filterAllRealEstatesAsc(bottomSqArea, topSqArea)
                    : realEstateRepository.filterAllRealEstatesDesc(bottomSqArea, topSqArea);
        } else {
            return ascending ? realEstateRepository.filterRealEstatesByOwnerAsc(bottomSqArea, topSqArea, ownerId)
                    : realEstateRepository.filterRealEstatesByOwnerDesc(bottomSqArea, topSqArea, ownerId);
        }
    }
}
