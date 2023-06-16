package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.request.MessageRequest;
import com.ftn.security.smarthomebackend.dto.response.MessageResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface IMessageService {
    void saveAllMessages(List<MessageRequest> messages) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    List<MessageResponse> getMessagesForDevice(Long deviceId) throws EntityNotFoundException;
}
