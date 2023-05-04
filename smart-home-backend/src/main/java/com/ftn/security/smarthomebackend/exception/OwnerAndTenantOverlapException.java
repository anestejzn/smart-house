package com.ftn.security.smarthomebackend.exception;

import static com.ftn.security.smarthomebackend.util.ErrorMessageConstants.OWNER_AND_TENANT_OVERLAP;

public class OwnerAndTenantOverlapException extends AppException {

    public OwnerAndTenantOverlapException() {
        super(OWNER_AND_TENANT_OVERLAP);
    }

}
