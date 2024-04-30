package dev.maycon.ms.email.services;

import dev.maycon.ms.email.enums.StatusEmail;
import dev.maycon.ms.email.models.EmailModel;
import dev.maycon.ms.email.repositories.EmailRepository;
import jakarta.activation.DataHandler;
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
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

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

            String cvModel = getCvModel(emailModel.getBodyTemplate());

            Path filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(cvModel)).toURI());

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
            textBodyPart.setHeader("Content-Type", "text/html; charset=iso-8859-1");
            textBodyPart.setContent(emailModel.getText(), "text/plain; charset=iso-8859-1");
            textBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
            multipart.addBodyPart(textBodyPart);


            ByteArrayDataSource dataSource = new ByteArrayDataSource(fileContent, "application/pdf");
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.setDataHandler(new DataHandler(dataSource));
            attachment.setFileName(filePath.getFileName().toString());
            multipart.addBodyPart(attachment);

            mimeMessage.setContent(multipart);

            javaMailSender.send(mimeMessage);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MessagingException | MailException | IOException e) {
            e.printStackTrace();
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            repository.save(emailModel);
        }

    }

    private String getCvModel(int bodyTemplate) {
        return switch (bodyTemplate) {
            case 1 -> "static/Maycon Jovan Pereira (pt-PT).pdf";
            case 2 -> "static/Maycon Jovan Pereira (pt-BR).pdf";
            case 3 -> "static/Maycon Jovan Pereira (en-US).pdf";
            default -> throw new IllegalStateException("Unexpected value: " + bodyTemplate);
        };
    }


}
