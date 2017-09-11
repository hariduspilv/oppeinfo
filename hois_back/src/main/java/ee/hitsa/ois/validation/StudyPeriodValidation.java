package ee.hitsa.ois.validation;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;

public class StudyPeriodValidation {

    public static void validate(StudyYear studyYear, StudyPeriod studyPeriod, StudyPeriodForm form) {
        assertStudyPeriodInsideStudyYear(studyYear, form);
        assertStudyPeriodDoNotOverlapWithOthers(studyYear, studyPeriod, form);
    }

    private static void assertStudyPeriodInsideStudyYear(StudyYear studyYear, StudyPeriodForm form) {
        if(studyYear.getStartDate().isAfter(form.getStartDate()) || studyYear.getEndDate().isBefore(form.getEndDate())) {
            throw new ValidationFailedException("studyYear.studyPeriod.error.notInsideStudyYear");
        }
    }

    private static void assertStudyPeriodDoNotOverlapWithOthers(StudyYear studyYear, StudyPeriod studyPeriod, StudyPeriodForm form) {
        Long studyPeriodId = EntityUtil.getNullableId(studyPeriod);
        if(studyYear.getStudyPeriods().stream().filter(sp -> studyPeriodId == null || !studyPeriodId.equals(EntityUtil.getId(sp))).anyMatch(sp -> overlap(sp, form))) {
            throw new ValidationFailedException("studyYear.studyPeriod.error.overlapWithOtherStudyPeriod");
        }
    }

    private static boolean overlap(StudyPeriod sp, StudyPeriodForm form) {
        return sp.getStartDate().isBefore(form.getStartDate()) && sp.getEndDate().isAfter(form.getStartDate()) ||
                sp.getStartDate().isAfter(form.getStartDate()) && sp.getStartDate().isBefore(form.getEndDate()) ||

                sp.getStartDate().isEqual(form.getStartDate()) || sp.getStartDate().isEqual(form.getEndDate()) ||
                sp.getEndDate().isEqual(form.getStartDate()) || sp.getEndDate().equals(form.getEndDate());
    }
}
