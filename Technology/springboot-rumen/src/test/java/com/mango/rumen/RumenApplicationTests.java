package com.mango.rumen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RumenApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
        System.out.println(dataSource);
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void redisTest() {
        redisTemplate.opsForValue().append("msg", "Hello World");
    }

    @Test
    public void rabbitTest() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("key1", "value1");
        objectObjectHashMap.put("key2", "value2");
        rabbitTemplate.convertAndSend("mango.direct", "mango.direct", objectObjectHashMap);
        System.out.println("发布消息成功");
    }

    @Test
    public void rabbitTest01() {
        Object object = rabbitTemplate.receiveAndConvert("mango.direct");
        System.out.println("消息接收成功" + object.toString());
    }

    @Test
    public void rabbitTest02() {
        amqpAdmin.declareExchange(new DirectExchange("mango.direct"));
    }

    @Test
    public void mailTest01() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("今晚8点开会");
        simpleMailMessage.setText("开会");
        simpleMailMessage.setFrom("thorne5691@foxmail.com");
        simpleMailMessage.setTo("1192128500@qq.com");
        mailSender.send(simpleMailMessage);

    }

    @Test
    public void mailTest02() throws Exception{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("今晚8点开会");
        mimeMessageHelper.setText("<b>开会la</b>", true);
        mimeMessageHelper.setFrom("thorne5691@foxmail.com", "梁聪");
        mimeMessageHelper.setTo("1192128500@qq.com");
        // 上传文件
        mimeMessageHelper.addAttachment("1.jpg", new File("C:\\Users\\mango\\Downloads\\1578489469108.jpeg"));
        mailSender.send(mimeMessage);

    }

}
