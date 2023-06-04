package com.DiscussionCommunity.service.impl;

import com.DiscussionCommunity.exception.custom.InternalServerException;
import com.DiscussionCommunity.service.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class EmailServiceImpl implements EmailService {

    @Async
    private void mailSender(String to, String subject, String userMessage){
        //smtp properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        String username = "barcafan830";
        String password = "pemzespckackwtdj";

        //session
        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress("barcafan830@gmail.com"));
            message.setSubject(subject);
            message.setContent(userMessage,"text/html");

            Transport.send(message);
        }catch (Exception e){
            throw new InternalServerException("Mail cannot be sent !!" + e);
        }
    }

    @Override
    @Async
    public void verifyAccount(String email, String username, String otp) {
        String userVerifyLink = "http://localhost:8081/auth/verify-user?type=verify&user="+email+"&activate_user="+otp;

        String message =  "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;border:3px solid #f8f8f8;padding:10px; border-radius:10px;\">\n"+
                "<p>Hello </p><b>" + username + "</b>, \n"+
                "\n"+
                "<p>Thank you for signing up with Discussion Community !! </p>\n"+
                "\n"+
                "<p>This Verify Link will expire in 15 minutes.</p> \n"+
                "\n"+
                "<p>To activate your account, please click the link below to confirm your email address:</p>\n" +
                "\n"+
                "<button>" +
                "<a href=\""+ userVerifyLink+"\">Verify Account</a>"+
                "</button style=\"padding:10px;color:#fff;background:#0ECA89;border-radius:10px;\"\n>"+
                "\n"+
                "Regards,"+
                "\n"+
                "<p>&#169; Discussion Community . All rights reserved.</p>\n"+
                "</div>"
                ;
        mailSender(email, "Account Activation Email", message);
    }

    @Override
    @Async
    public void forgotPassword(String email, String username,  String otp) {
        String userVerifyLink = "http://localhost:8081/auth/forgot-password?type=password&user="+email+"&activate_user="+otp;

        String message =  "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;border:3px solid #f8f8f8;padding:10px; border-radius:10px;\">\n"+
                "<p>Hello </p><b>" + username + "</b>, \n"+
                "\n"+
                "<p>Thank you for signing up with Discussion Community !! </p>\n"+
                "\n"+
                "<p>This Verify Link will expire in 15 minutes.</p> \n"+
                "\n"+
                "<p>To activate your account, please click the link below to confirm your email address:</p>\n" +
                "\n"+
                "<button>" +
                "<a href=\""+ userVerifyLink+"\">Verify Account</a>"+
                "</button style=\"padding:10px;color:#fff;background:#0ECA89;border-radius:10px;\"\n>"+
                "\n"+
                "Regards,"+
                "\n"+
                "<p>&#169; Discussion Community . All rights reserved.</p>\n"+
                "</div>"
                ;
        mailSender(email, "Reset Password Email", message);
    }
}
