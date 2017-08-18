package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class LessonPlanForm extends VersionedCommand {

    private Boolean isUsable;
    private Boolean showWeeks;
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

    public List<? extends LessonPlanModuleForm> getModules() {
        return modules;
    }

    public void setModules(List<? extends LessonPlanModuleForm> modules) {
        this.modules = modules;
    }

    public static class LessonPlanModuleForm {

        @NotNull
        private Long id;
        private EntityConnectionCommand teacher;
        @Valid
        private List<? extends LessonPlanModuleJournalForm> journals;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
