package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Long> , JpaSpecificationExecutor<Person>{
    Person findByIdcode(String idcode);

    @Query("select s.person from Student s where s.person.idcode = ?1")
    Person findByIdcodeStudent(String idcode);

    @Query("select u.person from User u where u.id = ?1")
    Person findByUserId(Long userId);
}
