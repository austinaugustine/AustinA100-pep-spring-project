package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private AccountRepository accountRepo;

    public Optional<Message> createMessage(Message msg) {
        if (msg.getMessageText() == null || msg.getMessageText().isBlank() || msg.getMessageText().length() > 255) {
            return Optional.empty();
        }
        if (!accountRepo.existsById(msg.getPostedBy())) {
            return Optional.empty();
        }
        return Optional.of(messageRepo.save(msg));
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Optional<Message> getMessageById(Integer id) {
        return messageRepo.findById(id);
    }

    public boolean deleteMessageById(Integer id) {
        if (messageRepo.existsById(id)) {
            messageRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateMessageText(Integer id, String newText) {
        Optional<Message> optMsg = messageRepo.findById(id);
        if (optMsg.isPresent() && newText != null && !newText.isBlank() && newText.length() <= 255) {
            Message msg = optMsg.get();
            msg.setMessageText(newText);
            messageRepo.save(msg);
            return true;
        }
        return false;
    }

    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepo.findByPostedBy(accountId);
    }
}
