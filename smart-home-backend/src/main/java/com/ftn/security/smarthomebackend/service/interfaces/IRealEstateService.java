package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.dto.response.SortedAliasesResponse;

import java.util.List;

public interface IRealEstateService {
    List<RealEstateViewResponse> filterRealEstates(boolean ascending, String sqArea, Long owner);
}
