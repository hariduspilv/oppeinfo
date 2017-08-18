package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;

@Transactional
@Service
public class ProtocolService {

    @Autowired
    private EntityManager em;

    public BigDecimal vocationalTotalCreditsOnCurrentCurriculum(Student student) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from (select distinct on (cm.id) cm.id, credits from protocol_student ps " + "inner join protocol p on p.id = ps.protocol_id "
                        + "inner join protocol_vdata pvd on pvd.protocol_id = p.id "
                        + "inner join curriculum_version_omodule cvo on cvo.id = pvd.curriculum_version_omodule_id "
                        + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id " + "where p.is_vocational = true and "
                        + "p.status_code = :statusCode and " + "ps.student_id = :studentId and " + "pvd.curriculum_version_id = :curriculumVersionId "
                        + "order by cm.id, p.id desc) as module_results");

        qb.parameter("statusCode", ProtocolStatus.PROTOKOLL_STAATUS_K.name());
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));

        return resultAsDecimal(qb.select("sum(credits)", em).getSingleResult(), 0);
    }

    public BigDecimal vocationalWeightedAverageGrade(Student student) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from (select CAST(grade.value AS integer) as grade_value, sum(cvoyc.credits) as ekap from protocol_student ps "
                        + "inner join protocol_vdata pvd on pvd.protocol_id = ps.protocol_id " + "inner join protocol p on p.id = ps.protocol_id "
                        + "inner join classifier grade on grade.code = grade_code "
                        + "inner join curriculum_version_omodule_year_capacity cvoyc on cvoyc.curriculum_version_omodule_id = pvd.curriculum_version_omodule_id "
                        + "where p.is_vocational = true and " + "p.status_code = :statusCode and " + "ps.student_id = :studentId and "
                        + "pvd.curriculum_version_id = :curriculumVersionId and " + "ps.grade_code in :positiveValueGrades "
                        + "group by cvoyc.curriculum_version_omodule_id, grade.value) as grade_ekap");

        qb.parameter("statusCode", ProtocolStatus.PROTOKOLL_STAATUS_K.name());
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));
        qb.parameter("positiveValueGrades", OccupationalGrade.OCCUPATIONAL_VALUE_GRADE_POSITIVE);

        return resultAsDecimal(qb.select("sum(grade_value * ekap) / sum(ekap)", em).getSingleResult(), 0);
    }

    public List<StudentVocationalResultModuleThemeDto> vocationalResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        result.addAll(vocationalResultsThemeResults(student));
        result.addAll(vocationalResultsModuleResults(student));
        return result.stream().sorted(Comparator.comparing(StudentVocationalResultModuleThemeDto::getDate).reversed()).collect(Collectors.toList());
    }

    private Collection<StudentVocationalResultModuleThemeDto> vocationalResultsModuleResults(Student student) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from ("
                + "select distinct on (cm.id) cm.id, cvo.id cvo_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, cm.credits cm_credits, "
                + "ps.grade_code ps_grade_code, p.confirm_date p_confirm_date, tp.id module_teacher_id, tp.firstname module_teacher_firstname, tp.lastname module_teacher_lastname, "
                + "sy.year_code sy_year_code, sy.start_date sy_start_date "
                + "from protocol_student ps "
                + "inner join protocol p on p.id = ps.protocol_id "
                + "inner join protocol_vdata pvd on pvd.protocol_id = p.id "
                + "inner join study_year sy on sy.id = pvd.study_year_id "
                + "inner join teacher t on t.id = pvd.teacher_id "
                + "inner join person tp on tp.id = t.person_id "
                + "inner join curriculum_version_omodule cvo on cvo.id = pvd.curriculum_version_omodule_id "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id "
                + "where p.is_vocational = true and " + "ps.student_id = :studentId and "
                + "p.status_code = :protocolStatusCode "
                + "order by cm.id desc, confirm_date desc) as module_result " + "order by module_result.p_confirm_date desc");

        qb.parameter("protocolStatusCode", ProtocolStatus.PROTOKOLL_STAATUS_K.name());
        qb.parameter("studentId", EntityUtil.getId(student));

        List<?> rows = qb.select(
                "cvo_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en,"
                        + "cm_credits, ps_grade_code, p_confirm_date, module_teacher_id, module_teacher_firstname, module_teacher_lastname, sy_year_code, sy_start_date",
                em).getResultList();

        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setModule(new AutocompleteResult(resultAsLong(r, 0),
                    CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                    CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1))));
            dto.setCredits(resultAsDecimal(r, 6));
            dto.setGrade(resultAsString(r, 7));
            dto.setDate(resultAsLocalDate(r, 8));
            String teacherName = resultAsString(r, 10) + " " + resultAsString(r, 11);
            dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 9), teacherName, teacherName));
            dto.setStudyYear(resultAsString(r, 12));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 13));
            result.add(dto);
        }
        return result;
    }

    private Collection<StudentVocationalResultModuleThemeDto> vocationalResultsThemeResults(Student student) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from ("
                + "select distinct on (cvot.id, journal_teacher_id) cvot.id cvot_id, cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, jes.grade_code jes_grade_code, jes.grade_inserted jes_grade_inserted, "
                + "tp.id journal_teacher_id, tp.firstname journal_teacher_firstname, tp.lastname journal_teacher_lastname,"
                + "cvo.id cvo_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, cm.credits cm_credits,"
                + "sy.year_code sy_year_code, sy.start_date sy_start_date "
                + "from journal_student js "
                + "inner join journal_omodule_theme jot on jot.journal_id = js.journal_id "
                + "inner join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "inner join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id "
                + "inner join journal j on j.id = js.journal_id "
                + "inner join study_year sy on sy.id = j.study_year_id "
                + "inner join journal_entry je on je.journal_id = js.journal_id "
                + "inner join journal_entry_student jes on jes.journal_entry_id = je.id "
                + "inner join journal_teacher jt on jt.journal_id = js.journal_id "
                + "inner join teacher t on t.id = jt.teacher_id "
                + "inner join person tp on tp.id = t.person_id "
                + "where js.student_id = :studentId and "
                + "je.entry_type_code = :entryTypeCode "
                + "order by cvot.id desc, journal_teacher_id, jes.grade_inserted desc) as module_theme_result "
                + "order by module_theme_result.jes_grade_inserted desc");

        qb.parameter("entryTypeCode", JournalEntryType.SISSEKANNE_L.name());
        qb.parameter("studentId", EntityUtil.getId(student));

        List<?> rows = qb.select(
                "cvo_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en,"
                        + "cvot_id, cvot_name_et, cvot_credits, jes_grade_code, jes_grade_inserted, journal_teacher_id, journal_teacher_firstname, journal_teacher_lastname, sy_year_code, sy_start_date",
                em).getResultList();

        Map<Long, StudentVocationalResultModuleThemeDto> result = new HashMap<>();
        for (Object r : rows) {
            Long themeId = resultAsLong(r, 6);
            if (result.containsKey(themeId)) {
                String teacherName = resultAsString(r, 12) + " " + resultAsString(r, 13);
                result.get(themeId).getTeachers().add(new AutocompleteResult(resultAsLong(r, 11), teacherName, teacherName));
            } else {
                StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
                dto.setModule(new AutocompleteResult(resultAsLong(r, 0),
                        CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                        CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1))));

                dto.setTheme(new AutocompleteResult(themeId, resultAsString(r, 7), resultAsString(r, 7)));
                dto.setCredits(resultAsDecimal(r, 8));
                dto.setGrade(resultAsString(r, 9));
                dto.setDate(resultAsLocalDate(r, 10));
                String teacherName = resultAsString(r, 12) + " " + resultAsString(r, 13);
                dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 11), teacherName, teacherName));
                dto.setStudyYear(resultAsString(r, 14));
                dto.setStudyYearStartDate(resultAsLocalDate(r, 15));
                result.put(dto.getTheme().getId(), dto);
            }

        }
        return result.values();
    }

}
