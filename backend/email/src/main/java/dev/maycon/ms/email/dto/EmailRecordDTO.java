package dev.maycon.ms.email.dto;

import java.util.UUID;

public record EmailRecordDTO(UUID jobId, String emailTo, String subject, String text) {
}
