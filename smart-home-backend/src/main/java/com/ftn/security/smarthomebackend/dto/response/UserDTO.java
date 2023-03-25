package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.model.RegularUser;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    private final String email;

    private final String name;

    private final String surname;

    private final String country;

    private final String city;

    public UserDTO(
            String email,
            String name,
            String surname,
            String country,
            String city
    ) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
    }

    public UserDTO(RegularUser regularUser) {
        this.id = regularUser.getId();
        this.email = regularUser.getEmail();
        this.name = regularUser.getName();
        this.surname = regularUser.getSurname();
        this.country = regularUser.getCountry();
        this.city = regularUser.getCity();
    }
}
