package com.ftn.security.smarthomebackend.dto.response;


import com.ftn.security.smarthomebackend.model.Message;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private Long id;
    private String messageText;
    private LocalDateTime dateTime;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.messageText = message.getMessageText();
        this.dateTime = message.getDateTime();
    }

    public static List<MessageResponse> fromMessageToResponses(List<Message> messages) {
        List<MessageResponse> responses = new LinkedList<>();
        messages.forEach(message -> {
            responses.add(new MessageResponse(message));
        });

        return responses;
    }
}
