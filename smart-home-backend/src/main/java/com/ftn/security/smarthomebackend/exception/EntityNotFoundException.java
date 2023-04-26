package com.ftn.security.smarthomebackend.exception;

import com.ftn.security.smarthomebackend.enums.EntityType;

public class EntityNotFoundException extends AppException {

    public EntityNotFoundException(String id, EntityType entityType) {
        super(id, entityType);
    }

    public EntityNotFoundException(Long id, EntityType entityType) {
        super(id.toString(), entityType);
    }
}
