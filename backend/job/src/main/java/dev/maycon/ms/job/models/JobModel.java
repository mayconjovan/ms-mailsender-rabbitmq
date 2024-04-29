package dev.maycon.ms.job.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_JOB")
@Data
public class JobModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID jobId;
    private String techRecruiterName;
    private String companyName;
    private String companyEmail;
    private String jobReference;
    private int bodyTemplate;

}
