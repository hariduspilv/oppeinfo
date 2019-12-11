package ee.hitsa.ois.report.apelapplication;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class ApelApplicationFormalSubjectOrModuleReport {
    
    private final String school;
    private final String name;
    private final Boolean isCompulsory;
    private final String code;
    private final BigDecimal credits;
    private final String assessment;
    private final String module;
    private final String grade;
    private final LocalDate gradeDate;
    private final String teachers;
    private final Boolean transfer;
    
    public ApelApplicationFormalSubjectOrModuleReport(ApelApplicationFormalSubjectOrModule formalSubjectOrModule,
              Boolean letterGrades, Language lang) {
        if (Boolean.TRUE.equals(formalSubjectOrModule.getIsMySchool())) {
            School applicationSchool = formalSubjectOrModule.getApelApplicationRecord().getApelApplication().getSchool(); 
            school = TranslateUtil.name(applicationSchool, lang);
        } else {
            school = TranslateUtil.name(formalSubjectOrModule.getApelSchool(), lang);
        }
        
        if (formalSubjectOrModule.getSubject() != null) {
           name = TranslateUtil.name(formalSubjectOrModule.getSubject(), lang);
           code = formalSubjectOrModule.getSubjectCode();
           module = TranslateUtil.name(formalSubjectOrModule.getCurriculumVersionHmodule(), lang);
        } else if (formalSubjectOrModule.getCurriculumVersionOmodule() != null) {
            CurriculumModule curriculumModule = formalSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            name = TranslateUtil.name(curriculumModule, lang);
            code = null;
            module = null;
        } else {
            name = TranslateUtil.name(formalSubjectOrModule, lang);
            code = formalSubjectOrModule.getSubjectCode();
            if (formalSubjectOrModule.getCurriculumVersionHmodule() != null) {
                module = TranslateUtil.name(formalSubjectOrModule.getCurriculumVersionHmodule(), lang);
            } else {
                module = null;
            }
        }
        
        isCompulsory = formalSubjectOrModule.getIsOptional() != null
                ? Boolean.valueOf(!formalSubjectOrModule.getIsOptional().booleanValue())
                : null;
        credits = formalSubjectOrModule.getCredits();
        assessment = TranslateUtil.name(formalSubjectOrModule.getAssessment(), lang);
        grade = ReportUtil.gradeValue(formalSubjectOrModule.getGrade(), letterGrades , lang);
        gradeDate = formalSubjectOrModule.getGradeDate();
        teachers = formalSubjectOrModule.getTeachers();
        transfer = formalSubjectOrModule.getTransfer();
    }

    public String getSchool() {
        return school;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsCompulsory() {
        return isCompulsory;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getAssessment() {
        return assessment;
    }

    public String getModule() {
        return module;
    }

    public String getGrade() {
        return grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public String getTeachers() {
        return teachers;
    }

    public Boolean getTransfer() {
        return transfer;
    }
}
