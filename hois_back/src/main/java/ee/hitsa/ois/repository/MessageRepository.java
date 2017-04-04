package ee.hitsa.ois.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Message;

public interface MessageRepository  extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    
    @Query(nativeQuery = true, value = 
            "select distinct sr.person_id "
            + "from student s "
            + "inner join person p on p.id = s.person_id "
            + "inner join student_representative sr on sr.student_id = s.id "
            + "where (date_part('year',age( p.birthdate)) < 18 "
            + "OR s.special_need_code is not null) "
            + "AND  p.id in ?1")
    public Set<BigInteger> getRepresentativePersonIds(Set<Long> receiversIds);
    
    @Query(nativeQuery = true, value = ""
            + "select t.id from teacher t where person_id = ?1 and school_id = ?2")
    public List<Long> getTeacherIdByPersonAndSchool(Long personId, Long schoolId);
}
