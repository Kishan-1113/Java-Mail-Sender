package com.example.emailsender.Services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.emailsender.Models.EmailSend;

@Configuration
public class QueueConfig {

    @Bean
    public BlockingQueue<EmailSend> emailQueue() {
        return new LinkedBlockingQueue<>();
    }
}
