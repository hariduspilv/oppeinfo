package ee.hitsa.ois.web.dto.exam;

import java.util.List;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectStudyPeriodDto {

    private final Long id;
    private final AutocompleteResult subject;
    private final List<String> teacherNames;
    private final AutocompleteResult studyPeriod;
    private final String assessment;

    public SubjectStudyPeriodDto(Long id, AutocompleteResult subject, List<String> teachers, AutocompleteResult studyPeriod, String assessment) {
        this.id = id;
        this.teacherNames = teachers;
        this.subject = subject;
        this.studyPeriod = studyPeriod;
        this.assessment = assessment;
    }

    public static SubjectStudyPeriodDto of(SubjectStudyPeriod ssp, List<String> teachers) {
        Subject subj = ssp.getSubject();
        return new SubjectStudyPeriodDto(ssp.getId(),
                new AutocompleteResult(subj.getId(), SubjectUtil.subjectName(subj.getCode(), subj.getNameEt()), SubjectUtil.subjectName(subj.getCode(), subj.getNameEn())),
                teachers,
                AutocompleteResult.of(ssp.getStudyPeriod()),
                EntityUtil.getCode(subj.getAssessment()));
    }

    public Long getId() {
        return id;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public List<String> getTeacherNames() {
        return teacherNames;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public String getAssessment() {
        return assessment;
    }
}
