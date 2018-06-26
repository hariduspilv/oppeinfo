package ee.hitsa.ois.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ResultColumnDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalResultDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentResultColumnDto;

public abstract class ReportUtil {

    public static final String KEY_MISSING = "missing";
    public static final String MODULE_TYPE = "KUTSEMOODUL";
    public static final String VOCATIONAL_GRADE = "KUTSEHINDAMINE";

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
            return classifierValue(resultColumn.getPracticeModuleThemeResult().getGrade(), VOCATIONAL_GRADE, classifierCache);
        } else if (resultColumn.getPracticeModuleResult() != null) {
            return classifierValue(resultColumn.getPracticeModuleResult().getGrade(), VOCATIONAL_GRADE, classifierCache);
        } else if (resultColumn.getModuleResult() != null) {
            return classifierValue(resultColumn.getModuleResult().getGrade(), VOCATIONAL_GRADE, classifierCache);
        }
        return "";
    }
    
    private static String journalResultAsString(Boolean absencesPerJournals, StudentJournalResultDto journalResult,
            ClassifierCache classifierCache) {
        List<String> journalGrades = StreamUtil.toMappedList(e -> e.getGrade(),
                StreamUtil.toFilteredList(e -> e.getGrade() != null, journalResult.getEntries()));
        
        String result = !CollectionUtils.isEmpty(journalGrades) ? journalGrades.stream()
                .map(g -> classifierValue(g, "KUTSEHINDAMINE", classifierCache)).collect(Collectors.joining(" ")) : "";
        if (Boolean.TRUE.equals(absencesPerJournals)) {
            result += " ";
            result += journalResult.getAbsenceH().intValue() != 0 ? " H:" + journalResult.getAbsenceH() : "";
            result += journalResult.getAbsenceP().intValue() != 0 ? " P:" + journalResult.getAbsenceP() : "";
            result += journalResult.getAbsenceV().intValue() != 0 ? " V:" + journalResult.getAbsenceV() : "";
        }
        return result;
    }
    
    public static String classifierName(String code, String mainClassCode, ClassifierCache classifierCache, Language lang) {
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
    
}
