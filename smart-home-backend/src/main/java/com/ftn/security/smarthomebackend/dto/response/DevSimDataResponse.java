package com.ftn.security.smarthomebackend.dto.response;


import com.ftn.security.smarthomebackend.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DevSimDataResponse {
    protected Long id;
    protected DeviceType type;
}
