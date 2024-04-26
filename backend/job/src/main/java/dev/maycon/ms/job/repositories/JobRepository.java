package dev.maycon.ms.job.repositories;

import dev.maycon.ms.job.models.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobModel, UUID> {

}
