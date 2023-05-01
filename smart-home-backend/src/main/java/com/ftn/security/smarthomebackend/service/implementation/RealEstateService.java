package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.model.RealEstate;
import com.ftn.security.smarthomebackend.repository.RealEstateRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse.fromListToResponse;
import static com.ftn.security.smarthomebackend.util.Constants.FILTER_BY_ALL;
import static com.ftn.security.smarthomebackend.util.Helper.extractSqMetersNumbers;

@Service
public class RealEstateService implements IRealEstateService {

    @Autowired
    private RealEstateRepository realEstateRepository;

    public List<RealEstateViewResponse> filterRealEstates(boolean ascending, String sqArea, Long ownerId) {

        return fromListToResponse(getRealEstatesList(ascending, sqArea, ownerId));
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
