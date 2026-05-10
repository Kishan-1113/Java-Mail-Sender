package com.example.emailsender.Controllers;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.emailsender.Models.EmailSend;
import com.example.emailsender.Models.Email;
import com.example.emailsender.Publisher.RabbitJsonProducer;
import com.example.emailsender.Publisher.RabbitProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private BlockingQueue<EmailSend> queue;

    @Autowired
    private RabbitProducer rabbitProducer;

    @Autowired
    private RabbitJsonProducer rabbitJsonProducer;

    @GetMapping("/address{to}")
    public ResponseEntity<String> getMethodName(@RequestParam String to) {
        rabbitProducer.sendMessage(to);
        return ResponseEntity.ok("Message sent to RabbitMQ server... ");
    }

    @PostMapping("/send")
    public ResponseEntity<String> getMethodName(@RequestBody Email user) {
        rabbitJsonProducer.sendMessage(user);
        return ResponseEntity.ok("Json message sent to RabbitMQ server...");
    }

    @GetMapping("/health")
    public String health() {
        return new String("Hello, how are you? I am fine !");
    }

    @PostMapping("/queue/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailSend emailSend) {
        queue.offer(emailSend);
        return ResponseEntity.ok("Queued !");
    }
}