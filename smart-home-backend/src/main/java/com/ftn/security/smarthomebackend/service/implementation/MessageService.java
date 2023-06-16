package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.request.MessageRequest;
import com.ftn.security.smarthomebackend.dto.response.RuleResponse;
import com.ftn.security.smarthomebackend.model.Alarm;
import com.ftn.security.smarthomebackend.model.Message;
import com.ftn.security.smarthomebackend.model.Rule;
import com.ftn.security.smarthomebackend.repository.MessageRepository;
import com.ftn.security.smarthomebackend.repository.RuleRepository;
import com.ftn.security.smarthomebackend.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private ISymmetricAlgorithmService symmetricalAlgorithmService;
    @Autowired
    private IRuleService ruleService;
    @Autowired
    private IAlarmService alarmService;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveAllMessages(List<MessageRequest> messages) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<Message> decryptedMessages = decryptAllMessages(messages);
        List<Alarm> alarms = createAlarmsFromMessage(decryptedMessages);
        alarmService.saveAll(alarms);
        messageRepository.saveAll(decryptedMessages); //nzm da li da cuvamo poruke
    }

    private List<Message> decryptAllMessages(List<MessageRequest> messages) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<Message> decryptedMessages = new LinkedList<>();
        for(MessageRequest m : messages){
            String decrypted = symmetricalAlgorithmService.decryptMessage(m.getMessageText());
            decryptedMessages.add(new Message(decrypted, m.getDateTime(), m.getDeviceId(), m.getDeviceType()));
        }

        return decryptedMessages;
    }

    private List<Alarm> createAlarmsFromMessage(List<Message> messages){
        List<RuleResponse> rules = ruleService.getAllRules();
        List<Alarm> alarms = new LinkedList<>();
        for(RuleResponse rule : rules){
            for(int ind = 0; ind < messages.size(); ind++) {
                Message message = messages.get(ind);
                if(message.getMessageText().contains(rule.getRegexPattern()) && message.getDeviceType().equals(rule.getDeviceType())){
                    alarms.add(new Alarm(message.getMessageText(), message.getDeviceId(), message.getDateTime(), false));
                    messages.remove(message);
                    ind--;
                }
            }
        }

        return alarms;
    }
}
