package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.NewRealEstateRequest;
import com.ftn.security.smarthomebackend.dto.request.RealEstateBasicInfoRequest;
import com.ftn.security.smarthomebackend.dto.request.RealEstateOwnershipRequest;
import com.ftn.security.smarthomebackend.dto.response.RealEstateResponse;
import com.ftn.security.smarthomebackend.dto.response.RealEstateViewResponse;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
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
    @PreAuthorize("hasAuthority('GET_REAL_ESTATE')")
    public RealEstateResponse getRealEstate(@PathVariable @Valid @NotBlank(message = MISSING_ID) Long id) throws EntityNotFoundException {

        return realEstateService.getRealEstate(id);
    }

    @GetMapping(value = "owner-real-estates/{ascending}/{sqArea}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('FILTER_OWNER_REAL_ESTATES')")
    public List<RealEstateViewResponse> filterOwnerRealEstates(@PathVariable @Valid boolean ascending,
                                                          @PathVariable @Valid @NotBlank String sqArea,
                                                          @PathVariable @Valid @NotNull Long ownerId)
    {

        return realEstateService.filterRealEstates(
                ascending,
                sqArea,
                ownerId
        );
    }

    @GetMapping(value = "tenant-real-estates/{ascending}/{sqArea}/{tenantId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('FILTER_TENANT_REAL_ESTATES')")
    public List<RealEstateViewResponse> filterTenantRealEstates(@PathVariable @Valid boolean ascending,
                                                          @PathVariable @Valid @NotBlank String sqArea,
                                                          @PathVariable @Valid @NotNull Long tenantId)
    {

        return realEstateService.filterRealEstatesTenant(
                ascending,
                sqArea,
                tenantId
        );
    }

    @PostMapping("/create/real-estate")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_REAL_ESTATE')")
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
    @PreAuthorize("hasAuthority('EDIT_REAL_ESTATE')")
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
    @PreAuthorize("hasAuthority('EDIT_OWNERSHIP_REAL_ESTATE')")
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
    @PreAuthorize("hasAuthority('EDIT_TENANTS_REAL_ESTATE')") //REGULAR
    public RealEstateResponse editTenantsRealEstate(@RequestBody @Valid RealEstateOwnershipRequest request)
            throws EntityNotFoundException, OwnerAndTenantOverlapException {

        return realEstateService.editTenants(
                request.getId(),
                request.getTenantsIds()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('DELETE_REAL_ESTATE')")
    public boolean removeRealEstate(@PathVariable @Valid @NotBlank(message = MISSING_ID) Long id)
            throws EntityNotFoundException {

        return realEstateService.delete(id);
    }

    @PutMapping("/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('BLOCK_USER')")
    public boolean block(@Valid @PathVariable @NotNull(message = MISSING_ID) Long id)
            throws EntityNotFoundException, CannotPerformActionException {

        return realEstateService.block(id);
    }

    @PutMapping("/unblock/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('UNBLOCK_USER')")
    public boolean unblock(@Valid @PathVariable @NotNull(message = MISSING_ID) Long id)
            throws EntityNotFoundException, CannotPerformActionException {

        return realEstateService.unblock(id);
    }

}
