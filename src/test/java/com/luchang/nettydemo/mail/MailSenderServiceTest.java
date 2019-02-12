package com.luchang.nettydemo.mail;

import com.luchang.nettydemo.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * created by LuChang
 * 2019/1/30 14:35
 */
public class MailSenderServiceTest extends BaseTest {

    @Resource
    private MailSenderService mailSenderService;

    @Test
    public void sendMailTest(){
        mailSenderService.sendMessage("496963664@qq.com","验证码","验证码为：Hello World");
    }

}
