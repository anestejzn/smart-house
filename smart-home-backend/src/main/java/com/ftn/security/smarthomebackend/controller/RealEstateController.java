package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("real-estates")
public class RealEstateController {

    @Autowired
    private IRealEstateService realEstateService;

    @GetMapping(value = "/{ascending}/{sqArea}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<RealEstateViewResponse> filterRealEstates(@PathVariable @Valid boolean ascending,
                                                          @PathVariable @Valid @NotBlank String sqArea,
                                                          @PathVariable @Valid @NotNull Long ownerId)
    {

        return realEstateService.filterRealEstates(
                ascending,
                sqArea,
                ownerId
        );
    }

}
