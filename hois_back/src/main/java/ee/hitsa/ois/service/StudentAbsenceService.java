package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.repository.JournalEntryStudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;

@Transactional
@Service
public class StudentAbsenceService {
    
    @Autowired
    private EntityManager em;
    @Autowired 
    private JournalEntryStudentRepository journalEntryStudentRepository;
    
    private static String SELECT = "sa.id as absenceId, s.id as studentId, p.firstname, p.lastname, sa.valid_from, "
            + "sa.valid_thru, sa.is_accepted, sa.cause, sa.inserted_by, sa.changed_by ";
    private static String FROM = 
              "from student_absence sa "
            + "join student s on s.id = sa.student_id "
            + "join person p on p.id = s.person_id ";
    
    private static String ABSENCE_ENTRY_SELECT = "jes.id ";
    private static String ABSENCE_ENTRY_FROM = "from journal_entry_student jes "
            + "join journal_student js on js.id = jes.journal_student_id "
            + "join journal_entry je on je.id = jes.journal_entry_id";

    public Page<StudentAbsenceDto> search(HoisUserDetails user, StudentAbsenceSearchCommand criteria,
            Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM).sort(pageable);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if(!Boolean.TRUE.equals(criteria.getShowAll())) {
            qb.filter(" sa.is_accepted = false ");
        }
        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        return results.map(this::rowToDto);
    }
    
    private StudentAbsenceDto rowToDto(Object[] row) {
        StudentAbsenceDto dto = new StudentAbsenceDto();
        dto.setId(resultAsLong(row, 0));
        String fullname = PersonUtil.fullname(resultAsString(row, 2), resultAsString(row, 3));
        dto.setStudent(new AutocompleteResult(resultAsLong(row, 1), fullname, fullname));
        dto.setValidFrom(resultAsLocalDate(row, 4));
        dto.setValidThru(resultAsLocalDate(row, 5));
        dto.setIsAccepted(resultAsBoolean(row, 6));
        dto.setCause(resultAsString(row, 7));
        dto.setApplicant(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 8)));
        if(Boolean.TRUE.equals(dto.getIsAccepted())) {
            dto.setAcceptor(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 9)));
        }
        return dto;
    }
    
    public StudentAbsence accept(StudentAbsence studentAbsence) {
        updateJournalEntryStudents(studentAbsence);
        studentAbsence.setIsAccepted(Boolean.TRUE);
        return EntityUtil.save(studentAbsence, em);
    }

    private void updateJournalEntryStudents(StudentAbsence studentAbsence) {
        Set<Long> absences = getAbsenceEntries(studentAbsence);
        if(!CollectionUtils.isEmpty(absences)) {
            journalEntryStudentRepository.acceptAbsences(Absence.PUUDUMINE_V.name(), absences);
        }
    }
    
    private Set<Long> getAbsenceEntries(StudentAbsence studentAbsence) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(ABSENCE_ENTRY_FROM);
        qb.requiredCriteria("js.student_id = :studentId", "studentId", EntityUtil.getId(studentAbsence.getStudent()));
        qb.requiredCriteria("jes.absence_code = :noReason", "noReason", Absence.PUUDUMINE_P.name());
        if(studentAbsence.getValidThru() != null) {
            qb.requiredCriteria("je.entry_date >= :absenceFrom", "absenceFrom", studentAbsence.getValidFrom());
            qb.requiredCriteria("je.entry_date <= :absenceThru", "absenceThru", studentAbsence.getValidThru());   
        } else {
            qb.requiredCriteria("je.entry_date = :absenceFrom", "absenceFrom", studentAbsence.getValidFrom());
        }
        List<?> result = qb.select(ABSENCE_ENTRY_SELECT, em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }

    public boolean hasUnaccepted(HoisUserDetails user) {
        Query q = em.createNativeQuery(unacceptedQuery(user));
        q.setParameter("schoolId", user.getSchoolId());
        if(user.isTeacher()) {
            q.setParameter("teacherId", user.getTeacherId());
        }
        List<?> data = q.getResultList();        
        return resultAsBoolean(data.get(0), 0);
    }
    
    private String unacceptedQuery(HoisUserDetails user) {
        StringBuilder sql = new StringBuilder(
                "select exists( "
                + "select sa.id "
                + "from student_absence sa "
                + "join student s on s.id = sa.student_id "
                + "where s.school_id = :schoolId "
                + "and sa.is_accepted = false ");
        if(user.isTeacher()) {
            sql.append(" and s.student_group_id in (select sg.id from student_group sg where sg.teacher_id = :teacherId) ");
        }
        sql.append(")");
        return sql.toString();
    }
}
