package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
     @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

@PostMapping("/register")
public ResponseEntity<Account> register(@RequestBody Account acc) {
    Optional<Account> registered = accountService.register(acc);
    if (registered.isEmpty()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
    }
    return ResponseEntity.ok(registered.get()); // 200
}


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account acc) {
        Optional<Account> loggedIn = accountService.login(acc);
        return loggedIn.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message msg) {
        Optional<Message> created = messageService.createMessage(msg);
        return created.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Integer id) {
        return messageService.getMessageById(id).map(ResponseEntity::ok).orElse(ResponseEntity.ok().build());
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer id) {
        boolean deleted = messageService.deleteMessageById(id);
        return deleted ? ResponseEntity.ok(1) : ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer id, @RequestBody Message msg) {
        boolean updated = messageService.updateMessageText(id, msg.getMessageText());
        return updated ? ResponseEntity.ok(1) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessagesByUser(@PathVariable Integer accountId) {
        return messageService.getMessagesByUser(accountId);
    }
}
