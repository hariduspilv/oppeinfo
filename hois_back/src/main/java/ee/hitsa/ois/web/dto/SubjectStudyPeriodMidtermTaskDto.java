package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodMidtermTaskForm;

public class SubjectStudyPeriodMidtermTaskDto extends SubjectStudyPeriodMidtermTaskForm {

    private SubjectStudyPeriodSearchDto subjectStudyPeriod;
    private AutocompleteResult assessment;
    private Set<MidtermTaskDto> midtermTasks;
    private Set<HigherProtocolDto> protocols;
    private List<SubjectStudyPeriodSubgroupDto> subgroups;
    private Boolean canEditSubgroups;

    public static SubjectStudyPeriodMidtermTaskDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodMidtermTaskDto dto = new SubjectStudyPeriodMidtermTaskDto();
        SubjectStudyPeriodSearchDto ssp = new SubjectStudyPeriodSearchDto();
        ssp.setTeachers(PersonUtil.sorted(subjectStudyPeriod.getTeachers().stream().map(t -> t.getTeacher().getPerson())));
        ssp.setId(EntityUtil.getId(subjectStudyPeriod));
        ssp.setSubject(AutocompleteResult.of(subjectStudyPeriod.getSubject()));
        ssp.setStudyPeriod(AutocompleteResult.ofWithYear(subjectStudyPeriod.getStudyPeriod()));
        ssp.setIsPracticeSubject(subjectStudyPeriod.getSubject().getIsPractice());
        ssp.setMoodleCourseId(subjectStudyPeriod.getMoodleCourseId());

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
        dto.setSubgroups(StreamUtil.toMappedList(SubjectStudyPeriodSubgroupDto::of, subjectStudyPeriod.getSubgroups().stream().sorted(SubjectStudyPeriodUtil.COMPARATOR_SUBGROUP)));
        dto.setCanEditSubgroups(Boolean.valueOf(SubjectStudyPeriodUtil.canEditSubgroups(subjectStudyPeriod)));
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
        dto.setSubgroups(StreamUtil.toMappedList(SubjectStudyPeriodSubgroupDto::of, subjectStudyPeriod.getSubgroups().stream().sorted(SubjectStudyPeriodUtil.COMPARATOR_SUBGROUP)));
        return dto;
    }

    public Set<HigherProtocolDto> getProtocols() {
        return protocols;
    }

    public void setProtocols(Set<HigherProtocolDto> protocols) {
        this.protocols = protocols;
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

    public List<SubjectStudyPeriodSubgroupDto> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<SubjectStudyPeriodSubgroupDto> subgroups) {
        this.subgroups = subgroups;
    }

    public Boolean getCanEditSubgroups() {
        return canEditSubgroups;
    }

    public void setCanEditSubgroups(Boolean canEditSubgroups) {
        this.canEditSubgroups = canEditSubgroups;
    }

}
