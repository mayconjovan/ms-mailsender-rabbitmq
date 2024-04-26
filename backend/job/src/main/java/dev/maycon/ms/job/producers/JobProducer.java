package dev.maycon.ms.job.producers;

import dev.maycon.ms.job.dto.EmailDTO;
import dev.maycon.ms.job.models.JobModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobProducer {

    final RabbitTemplate rabbitTemplate;

    public JobProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;


    public void publishMessageEmail(JobModel userModel) {
        var emailDto = new EmailDTO();
        emailDto.setJobId(userModel.getJobId());
        emailDto.setEmailTo(userModel.getCompanyEmail());
        emailDto.setSubject(userModel.getJobReference());
        emailDto.setText(getHtmlModel(userModel.getCompanyName()));

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

    private String getHtmlModel(String companyName) {
        return "Olá RH da " + companyName + ", espero que este email o(a) encontre bem!\n\n" +
                "Me chamo Maycon, estou a procura de um trabalho como desenvolvedor/programador de software.\n\n" +
                "Desenvolvo em linguagem Java/Spring Framework há 5 anos e participei de projetos como E-Commerce's,\n" +
                "ERP's, CMO, com arquiteturas monolíticas e de microserviços com mensagerias.\n\n" +
                "Sou brasileiro, disponível para uma mudança de país e possuo inglês intermediário.\n\n" +
                "Em anexo envio meu CV para ser analisado.\n" +
                "Fico a disposição para uma entrevista.\n\n"+
                "Cumprimentos,\n\n" +
                "Maycon J Pereira\n" +
                "+55 47 992271353\n" +
                "Linkedin: https://www.linkedin.com/in/mayconjovan/\n" +
                "Obs: Este e-mail foi enviado por um microserviço em Java 17 com Postgres e RabbitMQ em ambiente Docker";

    }
}
