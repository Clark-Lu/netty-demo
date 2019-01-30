package com.luchang.nettydemo.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * created by LuChang
 * 2019/1/30 14:26
 */
@Service
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;

    public void sendMessage(String mailAddress, String title, String content){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(mailAddress);
            mimeMessageHelper.setSubject(title);
            mimeMessage.setText(content);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
