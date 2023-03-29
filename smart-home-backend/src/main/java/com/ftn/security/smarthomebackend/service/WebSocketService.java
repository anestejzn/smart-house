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

    public void sendMessageAboutCreateCertificate(String email){
        String message = "Admin have accepted your CSR. You have certificate.";
        this.simpMessagingTemplate.convertAndSendToUser(email, "/create-certificate", message);
    }

    public void sendMessageAboutCancelCertificate(String email, String reason){
        String message = String.format("Admin have cancelled your CSR. Reason: %s", reason);
        this.simpMessagingTemplate.convertAndSendToUser(email, "/cancel-certificate", message);
    }
}
