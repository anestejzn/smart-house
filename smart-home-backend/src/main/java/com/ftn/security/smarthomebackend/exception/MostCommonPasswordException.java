package com.ftn.security.smarthomebackend.exception;

import static com.ftn.security.smarthomebackend.util.Constants.MOST_COMMON_PASSWORD_MESSAGE;

public class MostCommonPasswordException extends AppException{

    public MostCommonPasswordException(){
        super(MOST_COMMON_PASSWORD_MESSAGE);
    }

    public MostCommonPasswordException(String message) {
        super(message);
    }
}
