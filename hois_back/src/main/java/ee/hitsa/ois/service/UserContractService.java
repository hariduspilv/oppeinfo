package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.UserContractForm;
import ee.hitsa.ois.web.commandobject.UserContractSearchCommand;
import ee.hitsa.ois.web.dto.UserContractSearchDto;

@Transactional
@Service
public class UserContractService {
    
    @Autowired
    private EntityManager em;

    public UserContractForm getContractData(School school) {
        return UserContractForm.of(school);
    }

    public void saveContractData(UserContractForm form, School school) {
        school.setIsStudentTerms(form.getIsStudentTerms());
        school.setContractText(form.getContract());
    }

    public String getContract(School school) {
        return school.getContractText();
    }

    public void confirmContract(Student student, School school) {
        student.setIsContractAgreed(Boolean.TRUE);
        student.setContractText(school.getContractText());
        student.setContractAgreed(LocalDateTime.now());
    }

    public Page<UserContractSearchDto> search(HoisUserDetails user, UserContractSearchCommand cmd, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "left join student_group sg on sg.id = s.student_group_id").sort(pageable);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("s.is_contract_agreed");

        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", cmd.getStudentName());
        qb.optionalContains("sg.code", "group", cmd.getGroupName());
        qb.optionalCriteria("s.contract_agreed >= :from", "from", cmd.getFrom() != null ? cmd.getFrom().atTime(LocalTime.MIN) : null);
        qb.optionalCriteria("s.contract_agreed <= :thru", "thru", cmd.getThru() != null ? cmd.getThru().atTime(LocalTime.MAX) : null);
        
        return JpaQueryUtil.pagingResult(qb, "s.id, p.firstname || ' ' || p.lastname as \"name\", p.idcode as idcode, sg.code as group, s.contract_agreed as agreed", em, pageable).map(r -> {
            UserContractSearchDto dto = new UserContractSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setIdcode(resultAsString(r, 2));
            dto.setGroup(resultAsString(r, 3));
            dto.setAgreed(resultAsLocalDateTime(r, 4));
            return dto;
        });
    }
}
