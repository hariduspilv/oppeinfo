package ee.hitsa.ois.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ResultColumnDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalResultDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentResultColumnDto;

public abstract class ReportUtil {

    public static final String KEY_MISSING = "missing";
    public static final String MODULE_TYPE = "KUTSEMOODUL";
    public static final String VOCATIONAL_GRADE = "KUTSEHINDAMINE";
    
    public static final List<String> CURRICULUM_MODULE_ORDER = EnumUtil.toNameList(
            CurriculumModuleType.KUTSEMOODUL_P, CurriculumModuleType.KUTSEMOODUL_Y, 
            CurriculumModuleType.KUTSEMOODUL_V, CurriculumModuleType.KUTSEMOODUL_L);

    public static String valueOrMissing(String value, Language lang) {
        return value != null && !value.isEmpty() ? value : TranslateUtil.translate(KEY_MISSING, lang);
    }
    
    // Student group teacher report functions
    public static String resultColumnAsString(ResultColumnDto resultColumn, Boolean columnsForExcel, Language lang) {
        if (resultColumn.getJournal() != null) {
            return TranslateUtil.name(resultColumn.getJournal(), lang);
        } else if (resultColumn.getPracticeModuleTheme() != null) {
            return Boolean.TRUE.equals(columnsForExcel)
                    ? TranslateUtil.name(resultColumn.getPracticeModuleTheme(), lang)
                    : "T: " + TranslateUtil.name(resultColumn.getPracticeModuleTheme(), lang);
        } else if (resultColumn.getFullPracticeModule() != null) {
            return Boolean.TRUE.equals(columnsForExcel)
                    ? TranslateUtil.translate("studentgroupteacher.wholePracticeModule", lang)
                    : "PM: " + TranslateUtil.name(resultColumn.getFullPracticeModule(), lang);
        } else if (resultColumn.getOutcome() != null) {
            int orderNr = resultColumn.getOutcome().getOrderNr() != null
                ? resultColumn.getOutcome().getOrderNr().intValue() : 0;
            return "OV" + (orderNr + 1) + ": " + TranslateUtil.name(resultColumn.getOutcome(), lang);
        } else if (resultColumn.getModule() != null) {
            return Boolean.TRUE.equals(columnsForExcel)
                    ? TranslateUtil.translate("studentgroupteacher.moduleGrade", lang)
                    : "M: " + TranslateUtil.name(resultColumn.getModule(), lang);
        }
        return "";
    }
    
    public static String studentResultColumnAsString(Boolean absencesPerJournals, StudentResultColumnDto resultColumn,
            ClassifierCache classifierCache) {
        if (resultColumn.getJournalResult() != null) {
            return journalResultAsString(absencesPerJournals, resultColumn.getJournalResult(), classifierCache);
        } else if (resultColumn.getPracticeModuleThemeResult() != null) {
            return classifierValue(resultColumn.getPracticeModuleThemeResult().getGrade().getCode(),
                    VOCATIONAL_GRADE, classifierCache);
        } else if (resultColumn.getPracticeModuleResult() != null) {
            return classifierValue(resultColumn.getPracticeModuleResult().getGrade().getCode(),
                    VOCATIONAL_GRADE, classifierCache);
        } else if (resultColumn.getOutcomeResult() != null) {
            return classifierValue(resultColumn.getOutcomeResult().getGrade().getCode(),
                    VOCATIONAL_GRADE, classifierCache);
        } else if (resultColumn.getModuleResult() != null) {
            return classifierValue(resultColumn.getModuleResult().getGrade().getCode(),
                    VOCATIONAL_GRADE, classifierCache);
        }
        return "";
    }
    
    private static String journalResultAsString(Boolean absencesPerJournals, StudentJournalResultDto journalResult,
            ClassifierCache classifierCache) {
        List<String> journalGrades = journalResult.getResults().stream().filter(e -> e.getGrade() != null)
                .map(e -> e.getGrade().getCode()).collect(Collectors.toList());

        String result = !CollectionUtils.isEmpty(journalGrades) ? journalGrades.stream()
                .map(g -> classifierValue(g, "KUTSEHINDAMINE", classifierCache)).collect(Collectors.joining(" ")) : "";
        if (Boolean.TRUE.equals(absencesPerJournals)) {
            result += " ";
            result += absencePerJournal(journalResult, Absence.PUUDUMINE_H) != 0
                    ? " H:" + absencePerJournal(journalResult, Absence.PUUDUMINE_H)
                    : "";
            result += absencePerJournal(journalResult, Absence.PUUDUMINE_P) != 0
                    ? " P:" + absencePerJournal(journalResult, Absence.PUUDUMINE_P)
                    : "";
            result += absencePerJournal(journalResult, Absence.PUUDUMINE_V) != 0
                    ? " V:" + absencePerJournal(journalResult, Absence.PUUDUMINE_V)
                    : "";
            result += absencePerJournal(journalResult, Absence.PUUDUMINE_PR) != 0
                    ? " PR:" + absencePerJournal(journalResult, Absence.PUUDUMINE_PR)
                    : "";
        }
        return result;
    }

    private static int absencePerJournal(StudentJournalResultDto journalResult, Absence absence) {
        if (journalResult.getAbsences() != null && journalResult.getAbsenceTotals().get(absence.name()) != null) {
            return journalResult.getAbsenceTotals().get(absence.name()).intValue();
        }
        return 0;
    }

    public static String classifierName(String code, String mainClassCode, ClassifierCache classifierCache,
            Language lang) {
        if (code == null) {
            return "";
        }
        Classifier c = classifierCache.getByCode(code, MainClassCode.valueOf(mainClassCode));
        return c != null ? TranslateUtil.name(c, lang) : "? - " + code;
    }

    public static String classifierValue(String code, String mainClassCode, ClassifierCache classifierCache) {
        if (code == null) {
            return "";
        }
        Classifier c = classifierCache.getByCode(code, MainClassCode.valueOf(mainClassCode));
        return c != null ? c.getValue() : "? - " + code;
    }

    public static String gradeValue(Classifier grade, Boolean isLetterGrade, Language lang) {
        if (grade == null) {
            return "";
        }
        if (MainClassCode.KORGHINDAMINE.name().equals(grade.getMainClassCode())) {
            return higherGradeValue(grade, isLetterGrade, lang);
        }
        return grade.getValue();
    }

    private static String higherGradeValue(Classifier grade, Boolean isLetterGrade, Language lang) {
        HigherAssessment assessment = EnumUtil.valueOf(HigherAssessment.class, grade);
        if (assessment == null) {
            return "";
        }
        if (Boolean.TRUE.equals(assessment.getIsDistinctive())) {
            return Boolean.TRUE.equals(isLetterGrade) ? grade.getValue2() : grade.getValue();
        }
        if (Boolean.TRUE.equals(isLetterGrade)) {
            return TranslateUtil.name(grade, lang);
        }
        return Language.EN == lang ? grade.getExtraval2() : grade.getExtraval1();
    }

    public static void assertCanViewStudentGroupTeacherReport(HoisUserDetails user, StudentGroup studentGroup) {
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_RYHMAJUHATAJA);
        if (user.isSchoolAdmin() || user.isTeacher()) {
            UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, studentGroup);
        } else if (user.isLeadingTeacher()) {
            UserUtil.assertIsLeadingTeacher(user, studentGroup.getSchool());
        } else {
            throw new AccessDeniedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanViewIndividualCurriculumStatistics(HoisUserDetails user) {
        if (user.isSchoolAdmin() || user.isLeadingTeacher()) {
            UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_INDIVID);
        } else if (user.isTeacher()) {
            UserUtil.assertIsTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_RYHMAJUHATAJA);
        } else {
            throw new AccessDeniedException("main.messages.error.nopermission");
        }
    }
    
    public static long val(Long l) {
        return l == null ? 0L : l.longValue();
    }

}
