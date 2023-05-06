package com.ftn.security.smarthomebackend.dto.request;

import com.ftn.security.smarthomebackend.util.Constants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.Constants.*;

@Getter
@Setter
public class UserRequest {
    @NotNull
    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 320, message = TOO_LONG_EMAIL)
    private final String email;

    @NotNull
    @NotBlank(message = WRONG_NAME)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_NAME)
    private final String name;

    @NotNull
    @NotBlank(message = WRONG_SURNAME)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_SURNAME)
    private final String surname;

//    @NotNull
//    @NotBlank
//    @Pattern(regexp = LEGIT_COUNTRY_REG, message = WRONG_COUNTRY)
//    private final String country;
//
//    @NotNull
//    @NotBlank
//    @Pattern(regexp = LEGIT_COUNTRY_REG, message = WRONG_CITY)
//    private final String city;

    public UserRequest(
            String email,
            String name,
            String surname
//            String country,
//            String city
    ) {
        this.email = email;
        this.name = name;
        this.surname = surname;
//        this.country = country;
//        this.city = city;
    }
}
