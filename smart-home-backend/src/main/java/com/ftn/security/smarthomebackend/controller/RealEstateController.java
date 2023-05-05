package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.NewRealEstateRequest;
import com.ftn.security.smarthomebackend.dto.request.RealEstateBasicInfoRequest;
import com.ftn.security.smarthomebackend.dto.request.RealEstateOwnershipRequest;
import com.ftn.security.smarthomebackend.dto.response.RealEstateResponse;
import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;

import com.ftn.security.smarthomebackend.exception.OwnerAndTenantOverlapException;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ftn.security.smarthomebackend.util.Constants.MISSING_ID;

@RestController
@RequestMapping("real-estates")
public class RealEstateController {

    @Autowired
    private IRealEstateService realEstateService;


    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR_USER')")
    public RealEstateResponse getRealEstate(@PathVariable @Valid @NotBlank(message = MISSING_ID) Long id) throws EntityNotFoundException {

        return realEstateService.getRealEstate(id);
    }

    @GetMapping(value = "/{ascending}/{sqArea}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR_USER')")
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

    @PostMapping("/create/real-estate")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public boolean createRealEstate(@RequestBody @Valid NewRealEstateRequest request)
            throws EntityNotFoundException, OwnerAndTenantOverlapException {

        return realEstateService.createRealEstate(
            request.getName(),
            request.getSqMeters(),
            request.getCity(),
            request.getStreet(),
            request.getStreetNum(),
            request.getOwnerId(),
            request.getTenantsIds()
        );
    }

    @PutMapping("/edit-basic-info/real-estate")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RealEstateResponse editBasicInfoRealEstate(@RequestBody @Valid RealEstateBasicInfoRequest request)
            throws EntityNotFoundException
    {

        return realEstateService.editBasicInfo(
                request.getId(),
                request.getName(),
                request.getSqMeters(),
                request.getCity(),
                request.getStreet(),
                request.getStreetNum()
        );
    }

    @PutMapping("/edit-ownership/real-estate")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RealEstateResponse editOwnershipRealEstate(@RequestBody @Valid RealEstateOwnershipRequest request)
            throws EntityNotFoundException, OwnerAndTenantOverlapException {

        return realEstateService.editOwnership(
                request.getId(),
                request.getOwnerId(),
                request.getTenantsIds()
        );
    }

    @PutMapping("/edit-tenants-regular/real-estate")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_REGULAR_USER')")
    public RealEstateResponse editTenantsRealEstate(@RequestBody @Valid RealEstateOwnershipRequest request)
            throws EntityNotFoundException, OwnerAndTenantOverlapException {

        return realEstateService.editTenants(
                request.getId(),
                request.getTenantsIds()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public boolean editOwnershipRealEstate(@PathVariable @Valid @NotBlank(message = MISSING_ID) Long id)
            throws EntityNotFoundException {

        return realEstateService.delete(id);
    }

}
