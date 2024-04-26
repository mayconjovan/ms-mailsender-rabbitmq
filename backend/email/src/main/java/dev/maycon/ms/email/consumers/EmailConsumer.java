package dev.maycon.ms.email.consumers;

import dev.maycon.ms.email.dto.EmailRecordDTO;
import dev.maycon.ms.email.models.EmailModel;
import dev.maycon.ms.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailService service;

    public EmailConsumer(EmailService service) {
        this.service = service;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDTO dto) {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(dto, emailModel);
        service.sendEmail(emailModel);
    }

}
