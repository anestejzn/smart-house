package com.ftn.security.smarthomebackend.util;

public class Constants {
    //PATHS
    public static final String TEMPLATE_FILE_PATH = "./src/main/resources/static/emailTemplates/";
    public static final String PASSWORDS_FILE_PATH = "./src/main/resources/most_common_passwords.txt";
    //MESSAGES
    public static final String MISSING_ID = "Id is missing";
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
    public static final String MOST_COMMON_PASSWORD_MESSAGE = "Password is in most common passwords. Try again with another password.";
    public static final String WRONG_SECURITY_CODE = "Security code is number greater than 0.";
    public static final String INVALID_CERTIFICATE_EXCEPTION = "This certificate is invalid";
    public static final String EMPTY_RE_NAME = "Real estate name must contain 20 characters.";
    public static final String SQ_AREA_MESSAGE = "Square area must be between 10m2 and 600m2.";
    public static final String CITY_ERROR_MESSAGE = "City must contain between 1 and 20 characters.";
    public static final String STREET_ERROR_MESSAGE = "Street must contain between 1 and 20 characters.";

    //CONSTANTS
    public static final int MIN_SECURITY_NUM = 1000;
    public static final int MAX_SECURITY_NUM = 9999;
    public static final int SALT_LENGTH = 4;
    public static final int ZERO_FAILED_ATTEMPTS = 0;
    public static final int MAX_NUM_VERIFY_TRIES = 3;
    public static final int MIN_SQ_AREA = 10;
    public static final int BOTTOM_MARGIN_FILTERING_SQ_AREA = 50;
    public static final int MAX_SQ_AREA = 600;
    public static final int FILTER_BY_ALL = -1;

    //REGEX
    public static final String LEGIT_PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$";
    public static final String LEGIT_NAME_REG = "^[A-Za-z]{1,1}[a-z]{1,20}([ ]?[A-Za-z]?[a-z]{1,20}|[a-z]{1,20})$";
    public static final String LEGIT_COUNTRY_REG = "[a-zA-Z ]{2,40}";
    public static final String PIN_CODE_REG = "^\\d{4}$";
    public static final String VALID_PERIOD_REG = "^\\d{1,100}$";
//    public static final String LEGIT_COUNTRY_REG = "[a-zA-Z ]{2,40}";
    public static final String LEGIT_RE_NAME_REG = "[a-zA-Z1-9 ]{1,20}";
    public static final String LEGIT_RE_SQ_AREA_REG = "(?:[1-9][0-9]|[1-5][0-9]{2}|600)";
    public static final String LEGIT_RE_CITY_AND_STREET_REG = "[a-zA-Z ]{1,20}";
    public static final String POSITIVE_WHOLE_NUMBER_REG = "[1-9][0-9]*";

    public static final String EMPTY_REASON = "Reason for cancellation certificate must exist.";
    public static final String INVALID_ALIAS_NAME = "Alias cannot be empty!";
    public static final String VALIDITY_PERIOD_MUST_BE_NUMBER = "Validity period must be a number!";

    // KEY STORE
    public static final String KEYSTORE_FILEPATH = "src/main/resources/keystore/keystore.jks";
    public static final String KEYSTORE_PASSWORD = "kspassword";
}