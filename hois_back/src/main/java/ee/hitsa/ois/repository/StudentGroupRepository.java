package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.StudentGroup;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

}
