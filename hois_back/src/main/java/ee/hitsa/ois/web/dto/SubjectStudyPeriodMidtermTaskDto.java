package ee.hitsa.ois.web.dto;

import java.util.Set;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodMidtermTaskForm;

public class SubjectStudyPeriodMidtermTaskDto extends SubjectStudyPeriodMidtermTaskForm {

    private SubjectStudyPeriodSearchDto subjectStudyPeriod;
    private AutocompleteResult assessment;
    private Set<MidtermTaskDto> midtermTasks;
    private Set<MidtermTaskStudentDto> students;

    public static SubjectStudyPeriodMidtermTaskDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodMidtermTaskDto dto = new SubjectStudyPeriodMidtermTaskDto();
        SubjectStudyPeriodSearchDto ssp = new SubjectStudyPeriodSearchDto();
        ssp.setTeachers(StreamUtil.toMappedSet(t -> t.getTeacher().getPerson().getFullname(), subjectStudyPeriod.getTeachers()));
        ssp.setId(EntityUtil.getId(subjectStudyPeriod));
        ssp.setSubject(AutocompleteResult.of(subjectStudyPeriod.getSubject()));
        ssp.setStudyPeriod(AutocompleteResult.of(subjectStudyPeriod.getStudyPeriod()));

        dto.setSubjectStudyPeriod(ssp);
        dto.setAssessment(getAssessmentValue(subjectStudyPeriod));
        dto.setMidtermTasks(StreamUtil.toMappedSet(MidtermTaskDto::ofForStudentResultsForm, subjectStudyPeriod.getMidtermTasks()));

        dto.setStudentResults(StreamUtil.toMappedSet(MidtermTaskStudentResultDto::of, 
                MidtermTaskUtil.getStudentResults(subjectStudyPeriod)));

        dto.setStudents(StreamUtil.toMappedSet(MidtermTaskStudentDto::of, 
                subjectStudyPeriod.getDeclarationSubjects()));
        return dto;
    }
    
    private static AutocompleteResult getAssessmentValue(SubjectStudyPeriod subjectStudyPeriod) {
        Classifier assessmentClassifier = subjectStudyPeriod.getSubject().getAssessment();
        return new AutocompleteResult(null, 
                assessmentClassifier.getValue() + " - " + assessmentClassifier.getNameEt(), 
                assessmentClassifier.getValue() + " - " + assessmentClassifier.getNameEn());
    }
    
    public Set<MidtermTaskStudentDto> getStudents() {
        return students;
    }
    public void setStudents(Set<MidtermTaskStudentDto> students) {
        this.students = students;
    }
    public SubjectStudyPeriodSearchDto getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(SubjectStudyPeriodSearchDto subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    public AutocompleteResult getAssessment() {
        return assessment;
    }
    public void setAssessment(AutocompleteResult assessment) {
        this.assessment = assessment;
    }
    public Set<MidtermTaskDto> getMidtermTasks() {
        return midtermTasks;
    }
    public void setMidtermTasks(Set<MidtermTaskDto> midtermTasks) {
        this.midtermTasks = midtermTasks;
    }
}
