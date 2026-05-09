package com.example.emailsender.Services;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.emailsender.Models.EmailSend;

import jakarta.annotation.PostConstruct;

@Component
public class MailWorker {

    @Autowired
    private MailService eMailService;

    @Autowired
    private BlockingQueue<EmailSend> queue;

    @PostConstruct
    public void startWorker() {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    EmailSend request = queue.take();
                    eMailService.sendSimpleEmail(
                            request.getSendTo(),
                            "Test Email",
                            "Hello, your email is working successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        worker.start();
    }
}
