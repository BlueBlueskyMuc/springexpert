package com.muc.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot04TaskApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;


    // 简单的邮箱
    @Test
    public void simpleMail() {
        // 创建一个简单的邮箱
        SimpleMailMessage message = new SimpleMailMessage();

        // 设置标题
        message.setSubject("通知-今天开会");
        // 设置内容
        message.setText("今晚7:30开会");

        // 设置发到那个邮箱 这里是163邮箱
        message.setTo("18618193693@163.com");
        // 设置来自哪个邮箱
        message.setFrom("418984564@qq.com");

        mailSender.send(message);
    }

    // 复杂的邮箱(设置字体样式,添加附件等)
    @Test
    public void complexMail() throws MessagingException {
        // 由JavaMailSenderImpl创建一个MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // 设置标题
        helper.setSubject("通知-今天开会");
        // 设置内容
        helper.setText("<b style='color:red;'>今晚7:30开会</b>",true);

        // 设置发到那个邮箱 这里是163邮箱
        helper.setTo("18618193693@163.com");
        // 设置来自哪个邮箱
        helper.setFrom("418984564@qq.com");

        // 添加附件
        helper.addAttachment("a.png", new File("C:\\Users\\41898\\Desktop\\a.png"));
        helper.addAttachment("b.png", new File("C:\\Users\\41898\\Desktop\\b.png"));

        mailSender.send(mimeMessage);
    }

}
