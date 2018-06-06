package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.repository.JournalEntryStudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentAbsenceUtil;
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
    @Autowired
    private StudyYearService studyYearService;

    private static final String SELECT = "sa.id as absenceId, s.id as studentId, p.firstname, p.lastname, sa.valid_from, "
            + "sa.valid_thru, sa.is_accepted, sa.is_rejected, sa.cause, sa.inserted_by, sa.accepted_by, sa.changed_by ";
    private static final String FROM =
              "from student_absence sa "
            + "join student s on s.id = sa.student_id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "join person p on p.id = s.person_id ";

    private static final String ABSENCE_ENTRY_FROM = "from journal_entry_student jes "
            + "join journal_student js on js.id = jes.journal_student_id "
            + "join journal_entry je on je.id = jes.journal_entry_id";
    
    private static final String FILTER_BY_STUDY_PERIOD = " exists("
            + "select sp.id "
            + "from study_period sp "
            + "where sp.id = :studyPeriod "
            + "and sp.start_date <= sa.valid_from "
            + "and (case when sa.valid_thru is null then sp.end_date >= sa.valid_from else sp.end_date >= sa.valid_thru end)) ";

    private static final String FILTER_BY_STUDY_YEAR = " exists("
            + "select sy.id "
            + "from study_year sy "
            + "where sy.id = :studyYear "
            + "and sy.start_date <= sa.valid_from "
            + "and (case when sa.valid_thru is null then sa.valid_from >= sy.start_date and sa.valid_from <= sy.end_date else sy.start_date <= sa.valid_thru and sy.end_date >= sa.valid_from end)) ";

    public Page<StudentAbsenceDto> search(HoisUserDetails user, StudentAbsenceSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(FROM).sort(pageable);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        qb.optionalCriteria("s.curriculum_version_id in :curriculumVersions", "curriculumVersions", criteria.getCurriculumVersions());
        qb.optionalContains("sg.code", "studentGroupCode", criteria.getStudentGroupCode());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getStudentName());
        qb.optionalCriteria("sa.is_accepted = :isAccepted", "isAccepted", criteria.getIsAccepted());
        
        if (Boolean.TRUE.equals(criteria.getIsRejected())) {
            qb.optionalCriteria("(sa.is_rejected = :isRejected or (sa.is_rejected is null and sa.is_accepted != :isRejected))", "isRejected", Boolean.TRUE);
        } else if (Boolean.FALSE.equals(criteria.getIsRejected())){
            qb.optionalCriteria("sa.is_rejected = :isRejected", "isRejected", Boolean.FALSE);
        }
        qb.optionalCriteria(FILTER_BY_STUDY_PERIOD, "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria(FILTER_BY_STUDY_YEAR, "studyYear", criteria.getStudyYear());
        
        if(user.isTeacher()) {
            qb.requiredCriteria("sg.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        boolean hasPermissionToChangeStatus = StudentAbsenceUtil.hasPermissionToChangeStatus(user);
        return results.map(r -> rowToDto(hasPermissionToChangeStatus, r));
    }
    
    private static StudentAbsenceDto rowToDto(boolean hasPermissionToChangeStatus,  Object[] row) {
        StudentAbsenceDto dto = new StudentAbsenceDto();
        dto.setId(resultAsLong(row, 0));
        String fullname = PersonUtil.fullname(resultAsString(row, 2), resultAsString(row, 3));
        dto.setStudent(new AutocompleteResult(resultAsLong(row, 1), fullname, fullname));
        dto.setValidFrom(resultAsLocalDate(row, 4));
        dto.setValidThru(resultAsLocalDate(row, 5));
        dto.setIsAccepted(resultAsBoolean(row, 6));
        dto.setIsRejected(resultAsBoolean(row, 7));
        dto.setCause(resultAsString(row, 8));
        dto.setApplicant(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 9)));
        if(Boolean.TRUE.equals(dto.getIsAccepted()) || Boolean.TRUE.equals(dto.getIsRejected())) {
            dto.setAcceptor(resultAsString(row, 10) != null ? resultAsString(row, 10)
                    : PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 11)));
        }
        dto.setCanChangeStatus(Boolean.valueOf(hasPermissionToChangeStatus && Boolean.FALSE.equals(dto.getIsAccepted())
                && (dto.getIsRejected() != null ? Boolean.FALSE.equals(dto.getIsRejected()) : true)));
        return dto;
    }

    public StudentAbsence accept(HoisUserDetails user, StudentAbsence studentAbsence) {
        updateJournalEntryStudents(studentAbsence);
        studentAbsence.setIsAccepted(Boolean.TRUE);
        studentAbsence.setAcceptedBy(PersonUtil.fullname(em.getReference(Person.class, user.getPersonId())));
        return EntityUtil.save(studentAbsence, em);
    }
    
    public StudentAbsence reject(HoisUserDetails user, StudentAbsence studentAbsence) {
        studentAbsence.setIsRejected(Boolean.TRUE);
        studentAbsence.setAcceptedBy(PersonUtil.fullname(em.getReference(Person.class, user.getPersonId())));
        return EntityUtil.save(studentAbsence, em);
    }

    private void updateJournalEntryStudents(StudentAbsence studentAbsence) {
        Set<Long> absences = getAbsenceEntries(studentAbsence);
        if(!absences.isEmpty()) {
            journalEntryStudentRepository.acceptAbsences(Absence.PUUDUMINE_V.name(), absences);
        }
    }

    private Set<Long> getAbsenceEntries(StudentAbsence studentAbsence) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(ABSENCE_ENTRY_FROM);
        qb.requiredCriteria("js.student_id = :studentId", "studentId", EntityUtil.getId(studentAbsence.getStudent()));
        qb.requiredCriteria("jes.absence_code = :noReason", "noReason", Absence.PUUDUMINE_P);
        if(studentAbsence.getValidThru() != null) {
            qb.requiredCriteria("je.entry_date >= :absenceFrom", "absenceFrom", studentAbsence.getValidFrom());
            qb.requiredCriteria("je.entry_date <= :absenceThru", "absenceThru", studentAbsence.getValidThru());   
        } else {
            qb.requiredCriteria("je.entry_date = :absenceFrom", "absenceFrom", studentAbsence.getValidFrom());
        }
        List<?> result = qb.select("jes.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }

    public boolean hasUnaccepted(HoisUserDetails user) {
        StudyYear currentYear = studyYearService.getCurrentStudyYear(user.getSchoolId());
        if(currentYear == null) {
            return false;
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_absence sa join student s on s.id = sa.student_id");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("sa.is_accepted = false");
        qb.requiredCriteria(FILTER_BY_STUDY_YEAR, "studyYear", EntityUtil.getId(currentYear));
        if(user.isTeacher()) {
            qb.requiredCriteria("s.student_group_id in (select sg.id from student_group sg where sg.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        }
        List<?> data = qb.select("sa.id", em).setMaxResults(1).getResultList();
        return !data.isEmpty();
    }
}
