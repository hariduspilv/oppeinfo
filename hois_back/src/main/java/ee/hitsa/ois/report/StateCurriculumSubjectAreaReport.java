package ee.hitsa.ois.report;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.util.StreamUtil;

public class StateCurriculumSubjectAreaReport {
    // AINEVALDKOND
    private String subjectArea;
    private List<StateCurriculumSubjectReport> subjects;
    
    public StateCurriculumSubjectAreaReport(Entry<String, List<StateCurriculumModule>> subject) {
        this.subjectArea = subject.getKey();
        this.subjects = StreamUtil.toMappedList(m -> new StateCurriculumSubjectReport(m), subject.getValue());
        Collections.sort(subjects, (o1, o2) -> (o1.getSubject().compareTo(o2.getSubject())));
    }
    public List<StateCurriculumSubjectReport> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<StateCurriculumSubjectReport> subjects) {
        this.subjects = subjects;
    }
    public String getSubjectArea() {
        return subjectArea;
    }
    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

}
