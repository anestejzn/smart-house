package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.User;

public interface IUserService {
    User getVerifiedUser(String email) throws EntityNotFoundException;
}
