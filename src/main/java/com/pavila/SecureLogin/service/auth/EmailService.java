package com.pavila.SecureLogin.service.auth;

import com.pavila.SecureLogin.util.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to,
                          String username,
                          EmailTemplateName emailTemplate,
                          String url,
                          String tokenCode,
                          String subject
    ) throws MessagingException {
        String templateName;
        if(emailTemplate == null){
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", url);
        properties.put("activation_code", tokenCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("contact@pavila.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
    }

}
