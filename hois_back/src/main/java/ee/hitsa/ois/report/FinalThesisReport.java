package ee.hitsa.ois.report;

import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class FinalThesisReport {

    public static final String TEMPLATE_NAME = "final.thesis.xhtml";
    
    private final String school;
    private final String curriculum;
    private final String student;
    private final String theme;
    private final Boolean hasDraft;
    private final String draft;
    
    public FinalThesisReport(FinalThesis finalThesis) {
        this(finalThesis, Language.ET);
    }
    
    public FinalThesisReport(FinalThesis finalThesis, Language lang) {
        Student s = finalThesis.getStudent();
        CurriculumVersion cv = s.getCurriculumVersion();
        school = TranslateUtil.name(s.getSchool(), lang);
        curriculum = CurriculumUtil.versionName(cv.getCode(), TranslateUtil.name(cv.getCurriculum(), lang));
        student = PersonUtil.fullname(s.getPerson());
        theme = Language.EN.equals(lang) ? finalThesis.getThemeEn() : finalThesis.getThemeEt();
        hasDraft = finalThesis.getHasDraft();
        draft = finalThesis.getDraft();
    }

    public String getSchool() {
        return school;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public String getStudent() {
        return student;
    }

    public String getTheme() {
        return theme;
    }
    
    public Boolean getHasDraft() {
        return hasDraft;
    }

    public String getDraft() {
        return draft;
    }
    
}
