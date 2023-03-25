package com.ftn.security.smarthomebackend.transfer_objects.requests;

import com.ftn.security.smarthomebackend.enums.AccountType;
import com.ftn.security.smarthomebackend.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.ftn.security.smarthomebackend.util.Constants.WRONG_PASSWORD;
import static com.ftn.security.smarthomebackend.util.Constants.WRONG_ROLE;

@Getter
@Setter
public class RegularUserRegistrationRequest extends UserRequest {

    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = Constants.LEGIT_PASSWORD_REG,
            message = WRONG_PASSWORD)
    private final String password;

    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = Constants.LEGIT_PASSWORD_REG,
            message = WRONG_PASSWORD)
    private final String confirmPassword;

    @NotNull(message = WRONG_ROLE)
    private final AccountType role;

    public RegularUserRegistrationRequest(
            String email,
            String name,
            String surname,
            String country,
            String city,
            String password,
            String confirmPassword,
            AccountType role
    ) {
        super(email, name, surname, country, city);
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }
}
