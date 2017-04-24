package ee.hitsa.ois.web.dto.timetable;

import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.util.EntityUtil;

public class LessonPlanDto {

    private Long id;
    private Boolean isUsable;
    private Boolean showWeeks;
    private List<StudyPeriodDto> studyPeriods;
    private List<LessonPlanModuleDto> modules;

    public static LessonPlanDto of(LessonPlan lessonPlan) {
        LessonPlanDto dto = EntityUtil.bindToDto(lessonPlan, new LessonPlanDto());
        dto.setStudyPeriods(lessonPlan.getStudyYear().getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate)).map(StudyPeriodDto::of).collect(Collectors.toList()));
        // TODO modules
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(Boolean isUsable) {
        this.isUsable = isUsable;
    }

    public Boolean getShowWeeks() {
        return showWeeks;
    }

    public void setShowWeeks(Boolean showWeeks) {
        this.showWeeks = showWeeks;
    }

    public List<StudyPeriodDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public List<LessonPlanModuleDto> getModules() {
        return modules;
    }

    public void setModules(List<LessonPlanModuleDto> modules) {
        this.modules = modules;
    }

    public static class LessonPlanModuleDto {
        private Long id;
    }

    public static class StudyPeriodDto {
        private String nameEt;
        private String nameEn;
        private List<Long> weekNrs;

        public static StudyPeriodDto of(StudyPeriod studyPeriod) {
            StudyPeriodDto dto = EntityUtil.bindToDto(studyPeriod, new StudyPeriodDto());
            List<Long> weekNrs = new ArrayList<>();
            int startWeek = studyPeriod.getStartDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            int endWeek = studyPeriod.getEndDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            if(endWeek < startWeek) {
                endWeek = endWeek + 52;
            }

            do {
                weekNrs.add(Long.valueOf(startWeek % 52));
            } while(++startWeek < endWeek);

            return dto;
        }

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public List<Long> getWeekNrs() {
            return weekNrs;
        }

        public void setWeekNrs(List<Long> weekNrs) {
            this.weekNrs = weekNrs;
        }
    }
}
