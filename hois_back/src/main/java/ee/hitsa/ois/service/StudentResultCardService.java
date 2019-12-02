package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementStudyResult;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.report.diploma.DiplomaSupplementResultReport;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.student.StudentResultCardForm;
import ee.hitsa.ois.web.dto.student.StudentResultCardDto;

@Transactional
@Service
public class StudentResultCardService {

    public static final String PDF_TEMPLATE_NAME = "student.result.card.vocational.xhtml";

    private static final String STUDENT_INFO_SELECT = "s.id student_id, s.status_code, "
            + "p.idcode, p.birthdate, p.firstname, p.lastname, "
            + "c.name_et, c.code, c.credits, sfc.name_et study_form_code, slc.name_et language_code, "
            + "(select string_agg(f.full_code, ', ') from diploma d join form f on f.id = d.form_id "
            + "where d.student_id = s.id and d.status_code in ('LOPUDOK_STAATUS_T', 'LOPUDOK_STAATUS_V')) diploma_nrs, "
            + "(select string_agg(full_code, ', ') from ( "
            + "select f2.full_code, case when f2.type_code = 'LOPUBLANKETT_HIN' then 1 else 2 end ordernr from diploma_supplement ds "
            + "join diploma_supplement_form dsf on dsf.diploma_supplement_id = ds.id "
            + "join form f2 on f2.id = dsf.form_id "
            + "where ds.student_id = s.id and ds.status_code in ('LOPUDOK_STAATUS_T', 'LOPUDOK_STAATUS_V') "
            + "order by ordernr) nrs) supplement_nrs";

    @Autowired
    private EntityManager em;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private PdfService pdfService;

    private List<StudentResultCardDto> studentResultCards(List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "join curriculum_version cv on cv.id = s.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id "
                + "left join classifier sfc on sfc.code = s.study_form_code "
                + "left join classifier slc on slc.code = s.language_code");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);

        List<?> data = qb.select(STUDENT_INFO_SELECT, em).getResultList();
        List<StudentResultCardDto> students = StreamUtil.toMappedList(r -> {
            StudentResultCardDto dto = new StudentResultCardDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setStudentStatus(resultAsString(r, 1));
            dto.setIdcode(resultAsString(r, 2));
            dto.setBirthdate(resultAsLocalDate(r, 3));
            dto.setFirstname(resultAsString(r, 4));
            dto.setLastname(resultAsString(r, 5));
            dto.setCurriculumName(resultAsString(r, 6));
            dto.setCurriculumCode(resultAsString(r, 7));
            dto.setCurriculumCredits(resultAsDecimal(r, 8));
            dto.setStudyForm(resultAsString(r, 9));
            dto.setStudyLanguage(resultAsString(r, 10));
            dto.setDiplomaNrs(resultAsString(r, 11));
            dto.setSupplementNrs(resultAsString(r, 12));
            return dto;
        }, data);
        setStudentMatriculation(students);
        setStudentExmatriculation(students);
        setStudentResults(students);

        return students;
    }

    private void setStudentMatriculation(List<StudentResultCardDto> students) {
        if (students.isEmpty()) {
            return;
        }
        List<?> data = em.createNativeQuery("select ds.student_id, case when (d.directive_nr != 'YLE' and d.directive_nr not like '%FIKT%') "
                + "then d.directive_nr else null end, d.confirm_date from directive_student ds "
                + "join directive d on d.id = ds.directive_id "
                + "where ds.student_id in (:studentIds) and d.type_code in (:directiveTypes) "
                + "and d.status_code = :directiveStatus "
                + "order by d.confirm_date desc")
                .setParameter("studentIds", StreamUtil.toMappedList(s -> s.getStudentId(), students))
                .setParameter("directiveTypes", EnumUtil.toNameList(DirectiveType.KASKKIRI_IMMAT,
                        DirectiveType.KASKKIRI_IMMATV))
                .setParameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .getResultList();

        Map<Long, StudentResultCardDto> studentMap = StreamUtil.toMap(s -> s.getStudentId(), students);
        for (Object r : data) {
            StudentResultCardDto dto = studentMap.get(resultAsLong(r, 0));
            if (dto.getStudyStartDirectiveConfirmDate() != null) {
                continue;
            }
            dto.setMatriculationDate(resultAsLocalDate(r, 2));
            dto.setStudyStartDirectiveNr(resultAsString(r, 1));
            dto.setStudyStartDirectiveConfirmDate(resultAsLocalDate(r, 2));
        }
    }

    private void setStudentExmatriculation(List<StudentResultCardDto> students) {
        if (students.isEmpty()) {
            return;
        }
        List<?> data = em.createNativeQuery("select ds.student_id, d.directive_nr, d.confirm_date from directive_student ds "
                + "join directive d on d.id = ds.directive_id "
                + "where ds.student_id in (:studentIds) and d.type_code in (:directiveTypes) "
                + "and d.status_code = :directiveStatus "
                + "order by d.confirm_date desc")
                .setParameter("studentIds", StreamUtil.toMappedList(s -> s.getStudentId(), students))
                .setParameter("directiveTypes", EnumUtil.toNameList(DirectiveType.KASKKIRI_LOPET,
                        DirectiveType.KASKKIRI_EKSMAT))
                .setParameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .getResultList();

        Map<Long, StudentResultCardDto> studentMap = StreamUtil.toMap(s -> s.getStudentId(), students);
        for (Object r : data) {
            StudentResultCardDto dto = studentMap.get(resultAsLong(r, 0));
            if (dto.getStudyEndDirectiveConfirmDate() != null) {
                continue;
            }
            dto.setExmatriculationDate(resultAsLocalDate(r, 2));
            dto.setStudyEndDirectiveNr(resultAsString(r, 1));
            dto.setStudyEndDirectiveConfirmDate(resultAsLocalDate(r, 2));
        }
    }

    private void setStudentResults(List<StudentResultCardDto> students) {
        if (students.isEmpty()) {
            return;
        }
        List<Long> finishedStudentsStudents = StreamUtil.nullSafeList(students).stream()
                .filter(s -> StudentStatus.OPPURSTAATUS_K.name().equals(s.getStudentStatus()))
                .map(s -> s.getStudentId()).collect(Collectors.toList());
        Map<Long, DiplomaSupplement> supplementsByStudent = studentDiplomaSupplements(finishedStudentsStudents);

        List<Long> studentsWithoutSupplements = StreamUtil.nullSafeList(students).stream()
                .filter(s -> !supplementsByStudent.keySet().contains(s.getStudentId())).map(s -> s.getStudentId())
                .collect(Collectors.toList());
        Map<Long, List<DiplomaSupplementStudyResult>> studentStudyResults = documentService
                .getStudyResultsVocational(studentsWithoutSupplements);

        Map<Long, StudentResultCardDto> studentMap = StreamUtil.toMap(s -> s.getStudentId(), students);
        for (Long studentId : studentMap.keySet()) {
            StudentResultCardDto dto = studentMap.get(studentId);

            DiplomaSupplementResultReport results = null;
            if (supplementsByStudent.containsKey(studentId)) {
                results = documentService.getReportResults(supplementsByStudent.get(studentId).getStudyResults(),
                        Boolean.FALSE, Boolean.TRUE, Language.ET);
            } else {
                List<DiplomaSupplementStudyResult> studentResults = studentStudyResults.containsKey(studentId)
                        ? studentStudyResults.get(studentId)
                        : new ArrayList<>();
                results = documentService.getReportResults(studentResults, Boolean.FALSE, Boolean.TRUE, Language.ET);
            }
            dto.setApels(results.getApels());
            dto.setFinalResults(results.getFinalResults());
            dto.setFinalThesis(results.getFinalThesis());
            dto.setStudyResults(results.getStudyResults());
            dto.setTotalCredits(results.getTotalCredits());
        }
    }

    private Map<Long, DiplomaSupplement> studentDiplomaSupplements(List<Long> students) {
        List<DiplomaSupplement> supplements = new ArrayList<>();
        if (students.size() > 0) {
            supplements = em.createQuery("select ds from DiplomaSupplement ds where ds.student.id in (?1)",
                    DiplomaSupplement.class)
                .setParameter(1, students)
                .getResultList();
        }
        return StreamUtil.toMap(ds -> EntityUtil.getId(ds.getStudent()), supplements);
    }

    public StudentResultCardDto studentResultCard(Long studentId) {
        List<StudentResultCardDto> cards = studentResultCards(Arrays.asList(studentId));
        return !cards.isEmpty() ? cards.get(0) : new StudentResultCardDto();
    }

    public byte[] studentResultCardsPrint(Long schoolId, StudentResultCardForm form) {
        Map<String, Object> data = new HashMap<>();
        School school = em.getReference(School.class, schoolId);
        data.put("school", school.getNameEt());
        data.put("date", LocalDate.now());
        data.put("cards", studentResultCards(form.getStudentIds()));
        return pdfService.generate(PDF_TEMPLATE_NAME, data);
    }

    public void assertIsAllowedToSeeStudentResultCard(HoisUserDetails user, List<Long> studentIds) {
        List<Student> students = em.createQuery("select s from Student s where s.id in (:studentIds)", Student.class)
                .setParameter("studentIds", studentIds)
                .getResultList();
        for (Student s : students) {
            UserUtil.assertCanViewStudent(user, s);
        }
    }
}
