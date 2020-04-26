package com.na.backend.util;

import com.na.backend.entity.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class MailManager {

    private static JavaMailSender javaMailSender;

    public MailManager (JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static void sendResetMail(String email, User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage resetMail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(resetMail, true);
        helper.setTo(email);
        helper.setSubject("[너나알아] 비밀번호 변경 확인 메일");

        String resetMessage = new StringBuffer().append("<p>" + user.getName() + "님, 안녕하세요!</p>")
                .append("<p>비밀번호를 다시 설정하고 싶으신가요?</p>")
                .append("<p><b>아래 링크</b>를 눌러주세요!</p>")
                // TODO: 링크같은거 하드코딩으로 박지말고 외부파일로 빼기
                .append("<a href='nnaa://reset_password?id=")
                .append(user.getId())
                .append("&token=")
                .append(user.getToken())
                .append("' target='_blenk'>비밀번호 재설정하기</a>")
                .append("<p>감사합니다.</p>")
                .append("<p>너나알아 팀 드림</p>")
                .toString();
        helper.setText(resetMessage, true);
        helper.setFrom("jaeeee2020@gmail.com","NNAA");

        javaMailSender.send(resetMail);
    }
}
