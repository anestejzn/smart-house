package com.ftn.security.smarthomebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessageAboutRejectCsr(String email, String message){
        this.simpMessagingTemplate.convertAndSendToUser(email, "/reject-csr", message);
    }
}
