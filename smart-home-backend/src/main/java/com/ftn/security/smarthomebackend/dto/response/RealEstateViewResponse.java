package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.model.RealEstate;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class RealEstateViewResponse {

    private Long id;

    private String ownerFullName;

    private String name;

    private Integer sqArea;

    public RealEstateViewResponse(Long id,
                                  String ownerFullName,
                                  String name,
                                  Integer sqArea)
    {
        this.id = id;
        this.ownerFullName = ownerFullName;
        this.name = name;
        this.sqArea = sqArea;
    }

    public RealEstateViewResponse(RealEstate realEstate)
    {
        this.id = realEstate.getId();
        this.ownerFullName = realEstate.getOwner().getName() + " " + realEstate.getOwner().getSurname();
        this.name = realEstate.getName();
        this.sqArea = realEstate.getSqMeters();
    }

    public static List<RealEstateViewResponse> fromListToResponse(List<RealEstate> realEstates)
    {
        List<RealEstateViewResponse> response = new LinkedList<>();
        realEstates.forEach(re ->
                response.add(new RealEstateViewResponse(re))
        );

        return response;
    }
}
