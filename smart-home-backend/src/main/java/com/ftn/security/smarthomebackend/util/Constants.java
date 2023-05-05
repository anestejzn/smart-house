package com.ftn.security.smarthomebackend.util;

public class Constants {
    //PATHS
    public static final String TEMPLATE_FILE_PATH = "./src/main/resources/static/emailTemplates/";
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
    public static final String WRONG_SECURITY_CODE = "Security code is number greater than 0.";
    public static final String INVALID_CERTIFICATE_EXCEPTION = "This certificate is invalid";

    //CONSTANTS
    public static final int MIN_SECURITY_NUM = 1000;
    public static final int MAX_SECURITY_NUM = 9999;
    public static final int SALT_LENGTH = 4;
    public static final int ZERO_FAILED_ATTEMPTS = 0;
    public static final int MAX_NUM_VERIFY_TRIES = 3;

    //REGEX
    public static final String LEGIT_PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$";
    public static final String LEGIT_NAME_REG = "^[A-Za-z]{1,1}[a-z]{1,20}([ ]?[A-Za-z]?[a-z]{1,20}|[a-z]{1,20})$";
    public static final String LEGIT_COUNTRY_REG = "[a-zA-Z ]{2,40}";
    public static final String PIN_CODE_REG = "^\\d{4}$";
    public static final String VALID_PERIOD_REG = "^\\d{1,100}$";

    public static final String EMPTY_REASON = "Reason for cancellation certificate must exist.";
    public static final String INVALID_ALIAS_NAME = "Alias cannot be empty!";
    public static final String VALIDITY_PERIOD_MUST_BE_NUMBER = "Validity period must be a number!";

    // KEY STORE
    public static final String KEYSTORE_FILEPATH = "src/main/resources/keystore/keystore.jks";
    public static final String KEYSTORE_PASSWORD = "kspassword";
}