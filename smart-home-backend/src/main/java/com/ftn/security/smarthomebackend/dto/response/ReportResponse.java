package com.ftn.security.smarthomebackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private Long realEstateId;
    private String realEstateName;
    private int numOfAlarms;

}
