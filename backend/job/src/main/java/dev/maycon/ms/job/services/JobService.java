package dev.maycon.ms.job.services;

import dev.maycon.ms.job.dto.JobRecordDTO;
import dev.maycon.ms.job.models.JobModel;
import dev.maycon.ms.job.producers.JobProducer;
import dev.maycon.ms.job.repositories.JobRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {

    final JobRepository repository;
    final JobProducer jobProducer;

    public JobService(JobRepository repository, JobProducer jobProducer) {
        this.repository = repository;
        this.jobProducer = jobProducer;
    }

    @Transactional
    public JobModel insert(JobRecordDTO job) {
        JobModel entity = new JobModel();
        BeanUtils.copyProperties(job, entity);

        entity = repository.save(entity);

        jobProducer.publishMessageEmail(entity);

        return entity;
    }


}
