package dev.maycon.ms.job.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record JobRecordDTO(@NotBlank String techRecruiterName, @NotBlank @Email String companyEmail,
                           @NotBlank String companyName, @NotBlank String jobReference, @Min(1) @Max(3) int bodyTemplate) {

}
