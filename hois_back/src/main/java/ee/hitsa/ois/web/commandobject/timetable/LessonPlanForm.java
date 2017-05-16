package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class LessonPlanForm extends VersionedCommand {

    private Boolean isUsable;
    private Boolean showWeeks;
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

        private Long id;
        private EntityConnectionCommand teacher;

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
    }
}
