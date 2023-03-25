package com.ftn.security.smarthomebackend.dto.request;

import com.ftn.security.smarthomebackend.util.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.Constants.*;

@Getter
@Setter
public class UserRequest {

    @Email(message = WRONG_EMAIL)
    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 60, message = TOO_LONG_EMAIL)
    private final String email;

    @NotBlank(message = WRONG_NAME)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_NAME)
    private final String name;

    @NotBlank(message = WRONG_SURNAME)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_SURNAME)
    private final String surname;

    @NotBlank(message = WRONG_COUNTRY)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_COUNTRY)
    private final String country;

    @NotBlank(message = WRONG_CITY)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_CITY)
    private final String city;

    public UserRequest(
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
}
