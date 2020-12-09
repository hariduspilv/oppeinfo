package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class LessonPlanForm extends VersionedCommand {

    private Boolean isUsable;
    private Boolean showWeeks;
    @ClassifierRestriction(MainClassCode.KOEFITSIENT)
    private String coefficient;
    @Valid
    private List<? extends LessonPlanModuleForm> modules;

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

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    public List<? extends LessonPlanModuleForm> getModules() {
        return modules;
    }

    public void setModules(List<? extends LessonPlanModuleForm> modules) {
        this.modules = modules;
    }

    public static class LessonPlanModuleForm {

        private Long id;
        private Long occupationModuleId;
        private EntityConnectionCommand teacher;
        @Valid
        private List<? extends LessonPlanModuleJournalForm> journals;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOccupationModuleId() {
            return occupationModuleId;
        }

        public void setOccupationModuleId(Long occupationModuleId) {
            this.occupationModuleId = occupationModuleId;
        }

        public EntityConnectionCommand getTeacher() {
            return teacher;
        }

        public void setTeacher(EntityConnectionCommand teacher) {
            this.teacher = teacher;
        }

        public List<? extends LessonPlanModuleJournalForm> getJournals() {
            return journals;
        }

        public void setJournals(List<? extends LessonPlanModuleJournalForm> journals) {
            this.journals = journals;
        }
    }

    public static class LessonPlanModuleJournalForm {

        @NotNull
        private Long id;
        // {capabilityType: [weekNrs from all studyPeriods ordered by studyPeriod.startDate]}
        private Boolean capacityDiff;
        private Map<String, List<Short>> hours;
        private List<? extends LessonPlanModuleJournalTeacherForm> teachers;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Boolean getCapacityDiff() {
            return capacityDiff;
        }

        public void setCapacityDiff(Boolean capacityDiff) {
            this.capacityDiff = capacityDiff;
        }

        public Map<String, List<Short>> getHours() {
            return hours;
        }

        public void setHours(Map<String, List<Short>> hours) {
            this.hours = hours;
        }

        public List<? extends LessonPlanModuleJournalTeacherForm> getTeachers() {
            return teachers;
        }

        public void setTeachers(List<? extends LessonPlanModuleJournalTeacherForm> teachers) {
            this.teachers = teachers;
        }
    }

    public static class LessonPlanModuleJournalTeacherForm {
        @NotNull
        private Long id;
        private Map<String, List<Short>> hours;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Map<String, List<Short>> getHours() {
            return hours;
        }

        public void setHours(Map<String, List<Short>> hours) {
            this.hours = hours;
        }
    }
}
