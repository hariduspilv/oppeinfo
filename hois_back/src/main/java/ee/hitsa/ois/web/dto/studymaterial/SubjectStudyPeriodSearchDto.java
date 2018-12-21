package ee.hitsa.ois.web.dto.studymaterial;

import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectStudyPeriodSearchDto extends SubjectStudyPeriodDto {

    private Long materialCount;
    private Set<String> studentGroups;
    
    public static SubjectStudyPeriodSearchDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodSearchDto dto = EntityUtil.bindToDto(subjectStudyPeriod, new SubjectStudyPeriodSearchDto(), "teachers", "studyPeriod");
        dto.setStudyPeriod(AutocompleteResult.ofWithYear(subjectStudyPeriod.getStudyPeriod()));
        dto.setTeachers(StreamUtil.toMappedList(sspt -> AutocompleteResult.of(sspt.getTeacher()), 
                subjectStudyPeriod.getTeachers()));
        dto.setStudentGroups(subjectStudyPeriod.getStudentGroups().stream().map(g -> g.getStudentGroup().getCode()).collect(Collectors.toSet()));
        return dto;
    }
    
    public Long getMaterialCount() {
        return materialCount;
    }
    public void setMaterialCount(Long materialCount) {
        this.materialCount = materialCount;
    }
    public Set<String> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(Set<String> studentGroups) {
        this.studentGroups = studentGroups;
    }
    
}
