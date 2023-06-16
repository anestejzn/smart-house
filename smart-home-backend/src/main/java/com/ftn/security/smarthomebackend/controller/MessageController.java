package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.MessageRequest;
import com.ftn.security.smarthomebackend.dto.response.MessageResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.interfaces.IMessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.ftn.security.smarthomebackend.util.Constants.MISSING_ID;

@RestController
@RequestMapping("messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @GetMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ_DEVICE_MESSAGES')")
    public List<MessageResponse> getMessagesForDevice(@Valid @NotNull(message = MISSING_ID) @Positive(message = MISSING_ID) @PathVariable Long deviceId) throws EntityNotFoundException {

        return messageService.getMessagesForDevice(deviceId);
    }

    @PostMapping(value="/saveDeviceMessages")
    @ResponseStatus(HttpStatus.OK)
    public void saveMessages(@RequestBody List<MessageRequest> deviceMessages) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        messageService.saveAllMessages(deviceMessages);
    }

}
