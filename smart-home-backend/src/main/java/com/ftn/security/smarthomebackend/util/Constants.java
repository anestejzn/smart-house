package com.ftn.security.smarthomebackend.util;

public class Constants {

    //MESSAGES
    public static final String WRONG_PASSWORD =
            "Password/confirm password must contain at least 8 characters. " +
                    "At least one number and one special character.";
    public static final String WRONG_EMAIL = "Email is not in the right format.";
    public static final String EMPTY_EMAIL = "Email cannot be empty.";
    public static final String TOO_LONG_EMAIL = "Email length is too long. Email cannot contain more than 60 characters.";
    public static final String WRONG_NAME = "Name must contain only letters and cannot be too long.";
    public static final String WRONG_SURNAME = "Surname must contain only letters and cannot be too long.";
    public static final String WRONG_CITY = "City must contain only letters and cannot be too long.";
    public static final String WRONG_COUNTRY = "Country must contain only letters and cannot be too long.";
    public static final String WRONG_ROLE = "Role must be selected.";
    public static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Passwords don't match. Try again.";

    //CONSTANTS
    public static final int MIN_SECURITY_NUM = 1000;
    public static final int MAX_SECURITY_NUM = 9999;
    public static final int SALT_LENGTH = 4;
    public static final int ZERO_FAILED_ATTEMPTS = 0;

    //REGEX
    public static final String LEGIT_PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$";
    public static final String LEGIT_NAME_REG = "^[A-Za-z]{1,1}[a-z]{1,20}([ ]?[A-Za-z]?[a-z]{1,20}|[a-z]{1,20})$";
    public static final String LEGIT_COUNTRY_REG = "[a-zA-Z ]{2,40}";
}
