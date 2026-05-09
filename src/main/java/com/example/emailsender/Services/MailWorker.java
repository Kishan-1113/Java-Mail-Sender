package com.example.emailsender.Services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    @PostConstruct
    public void startWorkers() {

        for (int i = 1; i <= 3; i++) {

            int workerId = i;

            executor.submit(() -> {

                while (true) {

                    try {

                        EmailSend request = queue.take();

                        System.out.println(
                                "Worker-" + workerId +
                                        " processing " +
                                        request.getSendTo());

                        eMailService.sendSimpleEmail(
                                request.getSendTo(),
                                "Test Email",
                                "Hello, your email is working successfully!");

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
