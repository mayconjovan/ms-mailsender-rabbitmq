package dev.maycon.ms.job.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailDTO {

    private UUID jobId;
    private String emailTo;
    private String subject;
    private String text;
    private int bodyTemplate;

}
