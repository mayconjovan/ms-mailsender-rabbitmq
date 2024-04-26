package dev.maycon.ms.job.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record JobRecordDTO(@NotBlank String companyName, @NotBlank @Email String companyEmail, @NotBlank String jobReference) {

}
