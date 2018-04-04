package ee.hitsa.ois.web.commandobject.exam;

import ee.hitsa.ois.validation.Required;

public class StudentExamSearchForm {

    @Required
    private Long studyPeriod;

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
}
