package ee.hitsa.ois.web.dto.exam;

import java.util.Collections;
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
    private final List<String> groupNames;
    private final AutocompleteResult studyPeriod;
    private final String assessment;

    public SubjectStudyPeriodDto(Long id, AutocompleteResult subject, List<String> teachers,
                                 AutocompleteResult studyPeriod, String assessment, List<String> groups) {
        this.id = id;
        this.teacherNames = teachers;
        this.subject = subject;
        this.studyPeriod = studyPeriod;
        this.assessment = assessment;
        this.groupNames = groups;
    }

    public SubjectStudyPeriodDto(Long id, AutocompleteResult subject, List<String> teachers, AutocompleteResult studyPeriod, String assessment) {
        this(id, subject, teachers, studyPeriod, assessment, Collections.emptyList());
    }

    public static SubjectStudyPeriodDto of(SubjectStudyPeriod ssp, List<String> teachers, List<String> groups) {
        Subject subj = ssp.getSubject();
        return new SubjectStudyPeriodDto(ssp.getId(),
                new AutocompleteResult(subj.getId(), SubjectUtil.subjectName(subj.getCode(), subj.getNameEt()), SubjectUtil.subjectName(subj.getCode(), subj.getNameEn())),
                teachers,
                AutocompleteResult.ofWithYear(ssp.getStudyPeriod()),
                EntityUtil.getCode(subj.getAssessment()),
                groups);
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

    public List<String> getGroupNames() {
        return groupNames;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public String getAssessment() {
        return assessment;
    }
}
