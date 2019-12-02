package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByIdcode(String idcode);
    Person findByForeignIdcodeAndCitizenshipCode(String foreignIdcode, String citizenshipCode);
}
