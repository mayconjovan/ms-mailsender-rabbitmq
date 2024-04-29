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


    public void publishMessageEmail(JobModel jobModel) {
        var emailDto = new EmailDTO();
        emailDto.setJobId(jobModel.getJobId());
        emailDto.setEmailTo(jobModel.getCompanyEmail());
        emailDto.setSubject(jobModel.getJobReference());
        emailDto.setText(getBodyModel(jobModel.getBodyTemplate(), jobModel.getTechRecruiterName()));
        emailDto.setBodyTemplate(jobModel.getBodyTemplate());

        rabbitTemplate.convertAndSend("", routingKey, emailDto);

    }

    private String getBodyModel(int bodyModel, String techRecruiterName) {
        return switch (bodyModel) {
            case 1 -> getBodyModelPT(techRecruiterName);
            case 2 -> getBodyModelBR(techRecruiterName);
            case 3 -> getBodyModelEN(techRecruiterName);
            default -> null;
        };

    }

    private String getBodyModelPT(String techRecruiterName) {
        return "Olá " + techRecruiterName + ", espero que este email o(a) encontre bem!\n\n" +
                "Me chamo Maycon, estou a procura de um trabalho como desenvolvedor/programador de software.\n\n" +
                "Desenvolvo em linguagem Java/Spring Boot há 5 anos e participei de projetos como E-Commerce's,\n" +
                "ERP's, OMS, com arquiteturas monolíticas e de microserviços com mensagerias (RabbitMQ, Kafka).\n\n" +
                "Sou brasileiro e tenho disponíbilidade para uma mudança de país, possuo inglês intermediário.\n\n" +
                "Em anexo envio meu CV para ser analisado.\n" +
                "Fico a disposição para uma entrevista.\n\n"+
                "Cumprimentos,\n\n" +
                "Maycon J Pereira\n" +
                "+55 47 992271353\n" +
                "Linkedin: https://www.linkedin.com/in/mayconjovan/\n" +
                "Github: https://github.com/mayconjovan\n" +
                "Obs: Este e-mail foi enviado por um microserviço em Java 17 com Postgres e RabbitMQ em ambiente Docker";

    }

    private String getBodyModelBR(String techRecruiterName) {
        return "Olá " + techRecruiterName + ", espero que este email o(a) encontre bem!\n\n" +
                "Me chamo Maycon, estou a procura de um trabalho como desenvolvedor/programador de software.\n\n" +
                "Desenvolvo em linguagem Java/Spring Boot há 5 anos e participei de projetos como E-Commerce's,\n" +
                "ERP's, OMS, com arquiteturas monolíticas e de microserviços com mensagerias (RabbitMQ, Kafka).\n\n" +
                "Em anexo envio meu CV para ser analisado.\n" +
                "Fico a disposição para uma entrevista.\n\n"+
                "Atenciosamente,\n\n" +
                "Maycon J Pereira\n" +
                "+55 47 992271353\n" +
                "Linkedin: https://www.linkedin.com/in/mayconjovan/\n" +
                "Github: https://github.com/mayconjovan\n" +
                "Obs: Este e-mail foi enviado por um microserviço em Java 17 com Postgres e RabbitMQ em ambiente Docker";

    }

    private String getBodyModelEN(String techRecruiterName) {
        return "Hello " + techRecruiterName + ", I hope this email find you well! \n\n" +
                "I'm Maycon, and I'm seeking a Job as a software developer/prorgammer.\n\n" +
                "I have been developing in Java with Spring boot for 5 years and have participated in projects such as \n" +
                "E-Commerce, ERP's, OMS, with both monolithic and microservices architectures using messaging systems \n" +
                "such as RabbitMQ and Apache Kafka.\n\n" +
                "I have attached my CV for your review.\n" +
                "I am available for an interview. \n\n" +
                "Sincerely,\n\n" +
                "Maycon J Pereira\n" +
                "+55 47 992271353\n" +
                "Linkedin: https://www.linkedin.com/in/mayconjovan/\n" +
                "Github: https://github.com/mayconjovan\n" +
                "Note: This email was sent by a microservice in Java 17 with Postgres and RabbitMQ in a Docker environment";
    }
}
