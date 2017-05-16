package ee.hitsa.ois.web.dto.timetable;

import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanDto extends LessonPlanForm {

    private Long id;
    private List<StudyPeriodDto> studyPeriods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static LessonPlanDto of(LessonPlan lessonPlan) {
        LessonPlanDto dto = EntityUtil.bindToDto(lessonPlan, new LessonPlanDto());
        dto.setStudyPeriods(lessonPlan.getStudyYear().getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate)).map(StudyPeriodDto::of).collect(Collectors.toList()));
        dto.setModules(lessonPlan.getLessonPlanModules().stream().map(LessonPlanModuleDto::of).sorted().collect(Collectors.toList()));
        return dto;
    }

    public List<StudyPeriodDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    protected static class LessonPlanModuleDto extends LessonPlanModuleForm {

        private String nameEt;
        private String nameEn;
        private List<LessonPlanJournalDto> journals;

        public static LessonPlanModuleDto of(LessonPlanModule lessonPlanModule) {
            LessonPlanModuleDto dto = new LessonPlanModuleDto();
            dto.setId(lessonPlanModule.getId());
            CurriculumModule cm = lessonPlanModule.getCurriculumVersionOccupationModule().getCurriculumModule();
            dto.setNameEt(cm.getNameEt());
            dto.setNameEn(cm.getNameEn());
            dto.setTeacher(lessonPlanModule.getTeacher() != null ? AutocompleteResult.of(lessonPlanModule.getTeacher()) : null);
            dto.setJournals(Collections.emptyList());
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

        public List<LessonPlanJournalDto> getJournals() {
            return journals;
        }

        public void setJournals(List<LessonPlanJournalDto> journals) {
            this.journals = journals;
        }
    }

    protected static class LessonPlanJournalDto {
        private String nameEt;

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }
    }

    protected static class StudyPeriodDto {
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
