package com.elkys.matchcarreira.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmailRecuperacao(String para, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(para);
        message.setSubject("Recuperação de Senha - MatchCarreira");
        message.setText("Olá! Use o token abaixo para redefinir sua senha:\n\n" +
                token + "\n\n" +
                "Este token expira em 15 minutos.");

        mailSender.send(message);
    }
}