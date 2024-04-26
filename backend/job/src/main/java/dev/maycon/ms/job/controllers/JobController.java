package dev.maycon.ms.job.controllers;

import dev.maycon.ms.job.dto.JobRecordDTO;
import dev.maycon.ms.job.models.JobModel;
import dev.maycon.ms.job.services.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jobs")
@AllArgsConstructor
public class JobController {

    final JobService service;

    @PostMapping
    public ResponseEntity<JobModel> insert(@RequestBody @Valid JobRecordDTO jobRecordDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(jobRecordDTO));
    }

}
