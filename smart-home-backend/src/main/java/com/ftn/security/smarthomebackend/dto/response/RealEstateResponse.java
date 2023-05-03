package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.model.RealEstate;
import com.ftn.security.smarthomebackend.model.RegularUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RealEstateResponse {

    private Long id;

    private String name;

    private Integer sqMeters;

    private String city;

    private String street;

    private String streetNum;

    private UserDTO owner;

    private List<UserDTO> tenants = new LinkedList<>();

    public RealEstateResponse(
            Long id,
            String name,
            Integer sqMeters,
            String city,
            String street,
            String streetNum,
            UserDTO owner,
            List<UserDTO> tenants
    ) {
        this.id = id;
        this.name = name;
        this.sqMeters = sqMeters;
        this.city = city;
        this.street = street;
        this.streetNum = streetNum;
        this.owner = owner;
        this.tenants = tenants;
    }

    public RealEstateResponse(RealEstate realEstate) {
        this.id = realEstate.getId();
        this.name = realEstate.getName();
        this.sqMeters = realEstate.getSqMeters();
        this.city = realEstate.getCity();
        this.street = realEstate.getStreet();
        this.streetNum = realEstate.getStreetNum();
        this.owner = new UserDTO(realEstate.getOwner());

        for (RegularUser regularUser : realEstate.getTenants()) {
            this.tenants.add(new UserDTO(regularUser));
        }
    }

}
