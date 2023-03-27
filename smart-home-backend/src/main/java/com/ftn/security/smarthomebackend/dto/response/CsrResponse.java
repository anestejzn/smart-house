package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.enums.CSRStatus;
import com.ftn.security.smarthomebackend.model.CSR;
import com.ftn.security.smarthomebackend.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CsrResponse {
    private Long id;
    private User user;
    private String commonName;
    private String organizationUnit;
    private String organization;
    private String city;
    private String state;
    private String country;
    private CSRStatus status;


    public CsrResponse(CSR csr){
        this.id = csr.getId();
        this.commonName = csr.getCommonName();
        this.user = csr.getUser();
        this.organization = csr.getOrganization();
        this.organizationUnit = csr.getOrganizationUnit();
        this.city = csr.getCity();
        this.state = csr.getState();
        this.country = csr.getCountry();
        this.status = csr.getStatus();
    }

    public static List<CsrResponse> transformToResponse(List<CSR> csrs){
        List<CsrResponse> csrResponses = new LinkedList<>();
        csrs.forEach(csr ->
                csrResponses.add(new CsrResponse(csr))
        );

        return csrResponses;
    }


}
