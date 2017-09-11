package ee.hitsa.ois.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("select j from Job j where j.status.code = ?1 and j.type.code in ?2 and j.jobTime <= ?3")
    List<Job> executableJobs(String status, List<String> type, LocalDateTime time);
}
