package ee.hitsa.ois.web.dto.midtermresult;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class MidtermResultStudentDto {

    private AutocompleteResult subject;
    private String subjectCode;
    private List<String> teachers;
    private AutocompleteResult studyPeriod;
    private List<MidtermResultDto> results;
    
    public static MidtermResultStudentDto of(DeclarationSubject declarationSubject) {
        MidtermResultStudentDto dto = new MidtermResultStudentDto();
        dto.setSubject(new AutocompleteResult(null, declarationSubject.getSubjectStudyPeriod().getSubject()));
        dto.setSubjectCode(declarationSubject.getSubjectStudyPeriod().getSubject().getCode());
        dto.setStudyPeriod(AutocompleteResult.ofWithYear(declarationSubject.getSubjectStudyPeriod().getStudyPeriod()));
        dto.setResults(declarationSubject.getMidtermTaskStudentResults().stream()
                .map(MidtermResultDto::of)
                .collect(Collectors.toList()));
        dto.setTeachers(declarationSubject.getSubjectStudyPeriod().getTeachers().stream()
                .map(SubjectStudyPeriodTeacher::getTeacher)
                .map(Teacher::getPerson)
                .map(PersonUtil::fullname)
                .collect(Collectors.toList()));
        return dto;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<MidtermResultDto> getResults() {
        return results;
    }

    public void setResults(List<MidtermResultDto> results) {
        this.results = results;
    }
}
