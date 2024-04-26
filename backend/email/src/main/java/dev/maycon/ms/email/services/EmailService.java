package dev.maycon.ms.email.services;

import dev.maycon.ms.email.enums.StatusEmail;
import dev.maycon.ms.email.models.EmailModel;
import dev.maycon.ms.email.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class EmailService {

    final EmailRepository repository;
    final JavaMailSender javaMailSender;

    public EmailService(EmailRepository repository, JavaMailSender javaMailSender) {
        this.repository = repository;
        this.javaMailSender = javaMailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void sendEmail(EmailModel emailModel) {
        try {
            Path filePath = Path.of("src/main/resources/static/Maycon Jovan Pereira (pt-BR).pdf");
            byte[] fileContent = Files.readAllBytes(filePath);

            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(emailModel.getEmailTo());
            helper.setSubject(emailModel.getSubject());

            helper.setText(emailModel.getText(), false);

            MimeMultipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(emailModel.getText(), "text/plain");
            multipart.addBodyPart(textBodyPart);

            ByteArrayDataSource dataSource = new ByteArrayDataSource(fileContent, "application/pdf");
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.setDataHandler(new jakarta.activation.DataHandler(dataSource));
            attachment.setFileName(filePath.getFileName().toString());
            multipart.addBodyPart(attachment);

            mimeMessage.setContent(multipart);


            javaMailSender.send(mimeMessage);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MessagingException | MailException | IOException e) {
            e.printStackTrace();
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            repository.save(emailModel);
            return;
        }

    }


}
