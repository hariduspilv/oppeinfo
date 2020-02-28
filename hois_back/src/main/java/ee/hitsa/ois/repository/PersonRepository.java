package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ee.hitsa.ois.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByIdcode(String idcode);
    @Query("SELECT p FROM Person p WHERE p.idcode = :idcode or p.uniqueCode = :idcode")
    Person findByIdcodeOrUniqueCode(@Param("idcode")String idcode);
}
