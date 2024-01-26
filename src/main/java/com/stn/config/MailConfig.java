package com.stn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:/egovframework/egovProps/globals.properties")
public class MailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.smtp.starttls.enable}")
    private String enable;

    @Value("${mail.smtp.ssl.enable}")
    private String sslEnable;

    @Value("${mail.smtp.ssl.protocols}")
    private String sslProtocols;

    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.starttls.enable", enable);
        properties.setProperty("mail.smtp.ssl.enable", sslEnable);
        properties.setProperty("mail.smtp.ssl.protocols", sslProtocols);
        properties.setProperty("mail.debug", debug);

        return properties;
    }
}