package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findByIdcode(String idcode);
}
