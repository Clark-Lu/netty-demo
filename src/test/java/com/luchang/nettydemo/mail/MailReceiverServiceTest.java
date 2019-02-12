package com.luchang.nettydemo.mail;

import com.luchang.nettydemo.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

/**
 * created by LuChang
 * 2019/2/12 15:05
 */
public class MailReceiverServiceTest extends BaseTest {

    @Resource
    private MailReceiverService mailReceiverService;

    @Test
    public void testReceiveMessage() throws MessagingException, IOException {
        Message[] messages = mailReceiverService.receiveMail();
        for (Message message : messages){
            System.out.println("====================================================");
            System.out.println(message.getSubject());
            System.out.println(message.getFrom()[0].toString());
            Object content = message.getContent();
            if (content instanceof String){
                System.out.println(content);
            }else if (content instanceof MimeMultipart){
                MimeMultipart mimeMultipart = (MimeMultipart) content;
                int count = mimeMultipart.getCount();
                for (int i = 0; i < count; i++) {
                    System.out.println(mimeMultipart.getBodyPart(i).getContent());
                }
            }

        }
    }

}
