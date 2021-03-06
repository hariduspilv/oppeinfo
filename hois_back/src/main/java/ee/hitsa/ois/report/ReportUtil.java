package ee.hitsa.ois.report;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.web.dto.GradeDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalEntryResultDto;
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
            ClassifierCache classifierCache, Map<Long, GradingSchemaRow> gradingSchemaRowMap, Language lang) {
        if (resultColumn.getJournalResult() != null) {
            return journalResultAsString(absencesPerJournals, resultColumn.getJournalResult(), classifierCache,
                    gradingSchemaRowMap, lang);
        } else if (resultColumn.getPracticeModuleThemeResult() != null && resultColumn.getPracticeModuleThemeResult().getGrade() != null) {
            return vocationalGradeAsString(resultColumn.getPracticeModuleThemeResult().getGrade(), null,
                    classifierCache, gradingSchemaRowMap, lang);
        } else if (resultColumn.getPracticeModuleResult() != null && resultColumn.getPracticeModuleResult().getGrade() != null) {
            return vocationalGradeAsString(resultColumn.getPracticeModuleResult().getGrade(), null,
                    classifierCache, gradingSchemaRowMap, lang);
        } else if (resultColumn.getOutcomeResult() != null && resultColumn.getOutcomeResult().getGrade() != null) {
            return vocationalGradeAsString(resultColumn.getOutcomeResult().getGrade(), null,
                    classifierCache, gradingSchemaRowMap, lang);
        } else if (resultColumn.getModuleResult() != null && resultColumn.getModuleResult().getGrade() != null) {
            return vocationalGradeAsString(resultColumn.getModuleResult().getGrade(), null,
                    classifierCache, gradingSchemaRowMap, lang);
        }
        return "";
    }
    
    private static String journalResultAsString(Boolean absencesPerJournals, StudentJournalResultDto journalResult,
            ClassifierCache classifierCache, Map<Long, GradingSchemaRow> gradingSchemaRowMap, Language lang) {
        List<StudentJournalEntryResultDto> journalGrades = journalResult.getResults().stream()
                .filter(e -> e.getGrade() != null)
                .collect(Collectors.toList());

        String result = !CollectionUtils.isEmpty(journalGrades)
                ? journalGrades.stream().map(g -> vocationalGradeAsString(g.getGrade(), g.getVerbalGrade(),
                    classifierCache, gradingSchemaRowMap, lang)).collect(Collectors.joining(" "))
                : "";
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

    public static String vocationalGradeAsString(GradeDto grade, String verbalGrade, ClassifierCache classifierCache,
            Map<Long, GradingSchemaRow> gradingSchemaRowMap, Language lang) {
        String result = "";
        if (grade != null) {
            if (grade.getGradingSchemaRowId() != null) {
                GradingSchemaRow row = gradingSchemaRowMap.get(grade.getGradingSchemaRowId());
                result = Language.EN.equals(lang) ? row.getGradeEn() : row.getGrade();
            } else if (grade.getCode() != null) {
                result = classifierValue(grade.getCode(), "KUTSEHINDAMINE", classifierCache);
            }
            if (verbalGrade != null) {
                result = (!result.isEmpty() ? result + ", " : "") + verbalGrade;
            }
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
        return Language.EN.equals(lang) ? grade.getExtraval2() : grade.getExtraval1();
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
