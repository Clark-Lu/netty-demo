package com.luchang.nettydemo.mail;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.Properties;

/**
 * created by LuChang
 * 2019/2/12 14:54
 */
@Service
@ConfigurationProperties(prefix = "spring.mail")
public class MailReceiverService{

    private String host;

    private String username;

    private String password;

    private Folder folder;


    public Message[] receiveMail() throws MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.debug","false");
        prop.setProperty("mail.store.protocol","pop3");
        prop.setProperty("mail.pop3.host","pop.126.com");
        Session session = Session.getInstance(prop);
        Store store = session.getStore();
        store.connect(username,password);
        folder = store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
        return messages;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
