package ee.hitsa.ois.web.dto;

import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodMidtermTaskForm;

public class SubjectStudyPeriodMidtermTaskDto extends SubjectStudyPeriodMidtermTaskForm {

    private SubjectStudyPeriodSearchDto subjectStudyPeriod;
    private AutocompleteResult assessment;
    private Set<MidtermTaskDto> midtermTasks;
    private Set<MidtermTaskStudentDto> students;
    private Set<HigherProtocolDto> protocols;

    public static SubjectStudyPeriodMidtermTaskDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodMidtermTaskDto dto = new SubjectStudyPeriodMidtermTaskDto();
        SubjectStudyPeriodSearchDto ssp = new SubjectStudyPeriodSearchDto();
        ssp.setTeachers(PersonUtil.sorted(subjectStudyPeriod.getTeachers().stream().map(t -> t.getTeacher().getPerson())));
        ssp.setId(EntityUtil.getId(subjectStudyPeriod));
        ssp.setSubject(AutocompleteResult.of(subjectStudyPeriod.getSubject()));
        ssp.setStudyPeriod(AutocompleteResult.of(subjectStudyPeriod.getStudyPeriod()));
        ssp.setIsPracticeSubject(subjectStudyPeriod.getSubject().getIsPractice());

        dto.setSubjectStudyPeriod(ssp);
        dto.setAssessment(getAssessmentValue(subjectStudyPeriod));
        dto.setMidtermTasks(StreamUtil.toMappedSet(MidtermTaskDto::ofForStudentResultsForm, subjectStudyPeriod.getMidtermTasks()));
        return dto;
    }

    public static SubjectStudyPeriodMidtermTaskDto ofForMidtermTasksStudentResultsForm(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodMidtermTaskDto dto = SubjectStudyPeriodMidtermTaskDto.of(subjectStudyPeriod);
       
        dto.setStudentResults(StreamUtil.toMappedSet(MidtermTaskStudentResultDto::of, 
                MidtermTaskUtil.getStudentResults(subjectStudyPeriod)));

        dto.setStudents(StreamUtil.toMappedSet(MidtermTaskStudentDto::of, 
                subjectStudyPeriod.getDeclarationSubjects()));
        dto.setProtocols(StreamUtil.toMappedSet(p -> HigherProtocolDto.ofForMidtermTasksForm(p.getProtocol()), subjectStudyPeriod.getProtocols()));
        return dto;
    }

    private static AutocompleteResult getAssessmentValue(SubjectStudyPeriod subjectStudyPeriod) {
        Classifier assessmentClassifier = subjectStudyPeriod.getSubject().getAssessment();
        return new AutocompleteResult(null, 
                assessmentClassifier.getValue() + " - " + assessmentClassifier.getNameEt(), 
                assessmentClassifier.getValue() + " - " + assessmentClassifier.getNameEn());
    }

    public static SubjectStudyPeriodMidtermTaskDto ofForProtocol(Set<Long> studentIds,
            SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodMidtermTaskDto dto = SubjectStudyPeriodMidtermTaskDto.of(subjectStudyPeriod);

        dto.setStudentResults(MidtermTaskUtil.getStudentResults(subjectStudyPeriod).stream()
                .filter(sr -> studentIds.contains(EntityUtil.getId(sr.getDeclarationSubject()
                        .getDeclaration().getStudent())))
                .map(MidtermTaskStudentResultDto::of).collect(Collectors.toSet()));
        return dto;
    }

    public Set<HigherProtocolDto> getProtocols() {
        return protocols;
    }

    public void setProtocols(Set<HigherProtocolDto> protocols) {
        this.protocols = protocols;
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
