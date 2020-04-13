package com.na.backend.util;

import com.na.backend.entity.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailManager {

    private static JavaMailSender javaMailSender;
    private static SpringTemplateEngine templateEngine;

    public MailManager (JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static void sendResetMail(String email, User user) throws MessagingException {
        MimeMessage resetMail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(resetMail, true);

        helper.setSubject("비밀번호 변경 확인 메일");
        helper.setTo(email);

        Context context = new Context();
        context.setVariable("userId", user.getId());
        context.setVariable("userName", user.getName());
        System.out.println("여기? ");

        String contentHTML = templateEngine.process("reset-template",context);
        helper.setText(contentHTML, true);

        javaMailSender.send(resetMail);
    }
}
