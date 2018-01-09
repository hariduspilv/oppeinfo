package ee.hitsa.ois.report.apelapplication;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Language;

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
    
    public ApelApplicationFormalSubjectOrModuleReport(ApelApplicationFormalSubjectOrModule formalSubjectOrModule, Language lang) {
        if (Boolean.TRUE.equals(formalSubjectOrModule.getIsMySchool())) {
            School applicationSchool = formalSubjectOrModule.getApelApplicationRecord().getApelApplication().getSchool(); 
            school = Language.EN.equals(lang) ? applicationSchool.getNameEn() : applicationSchool.getNameEt();
        } else {
            school = Language.EN.equals(lang) ? formalSubjectOrModule.getApelSchool().getNameEn() : formalSubjectOrModule.getApelSchool().getNameEt();
        }
        
        if (formalSubjectOrModule.getSubject() != null) {
           name = Language.EN.equals(lang) ? formalSubjectOrModule.getSubject().getNameEn() : formalSubjectOrModule.getSubject().getNameEt();
           code = formalSubjectOrModule.getSubjectCode();
           module = Language.EN.equals(lang) ? formalSubjectOrModule.getCurriculumVersionHmodule().getNameEn() : formalSubjectOrModule.getCurriculumVersionHmodule().getNameEt();
        } else if (formalSubjectOrModule.getCurriculumVersionOmodule() != null) {
            CurriculumModule curriculumModule = formalSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            name = Language.EN.equals(lang) ? curriculumModule.getNameEn() : curriculumModule.getNameEt();
            code = null;
            module = null;
        } else {
            name = Language.EN.equals(lang) ? formalSubjectOrModule.getNameEn() : formalSubjectOrModule.getNameEt();
            code = formalSubjectOrModule.getSubjectCode();
            if (formalSubjectOrModule.getCurriculumVersionHmodule() != null) {
                module = Language.EN.equals(lang) ? formalSubjectOrModule.getCurriculumVersionHmodule().getNameEn() : formalSubjectOrModule.getCurriculumVersionHmodule().getNameEt();
            } else {
                module = null;
            }
        }
        
        isCompulsory = Boolean.valueOf(!formalSubjectOrModule.getIsOptional().booleanValue());
        credits = formalSubjectOrModule.getCredits();
        assessment = Language.EN.equals(lang) ? formalSubjectOrModule.getAssessment().getNameEn() : formalSubjectOrModule.getAssessment().getNameEt();
        grade = formalSubjectOrModule.getGrade().getValue();
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
