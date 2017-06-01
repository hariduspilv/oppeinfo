package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanJournalForm extends VersionedCommand {

    @NotNull
    private Long lessonPlanModuleId;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMISVIIS)
    private String assessment;
    @NotEmpty
    private String nameEt;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.PAEVIK_GRUPI_JAOTUS)
    private String groupProportion;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.MAHT)
    private List<String> journalCapacityTypes;
    @Valid
    private List<LessonPlanJournalTeacherForm> journalTeachers;
    @NotEmpty
    private List<Long> journalOccupationModuleThemes;

    public Long getLessonPlanModuleId() {
        return lessonPlanModuleId;
    }

    public void setLessonPlanModuleId(Long lessonPlanModuleId) {
        this.lessonPlanModuleId = lessonPlanModuleId;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getGroupProportion() {
        return groupProportion;
    }

    public void setGroupProportion(String groupProportion) {
        this.groupProportion = groupProportion;
    }

    public List<String> getJournalCapacityTypes() {
        return journalCapacityTypes;
    }

    public void setJournalCapacityTypes(List<String> journalCapacityTypes) {
        this.journalCapacityTypes = journalCapacityTypes;
    }

    public List<LessonPlanJournalTeacherForm> getJournalTeachers() {
        return journalTeachers;
    }

    public void setJournalTeachers(List<LessonPlanJournalTeacherForm> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public List<Long> getJournalOccupationModuleThemes() {
        return journalOccupationModuleThemes;
    }

    public void setJournalOccupationModuleThemes(List<Long> journalOccupationModuleThemes) {
        this.journalOccupationModuleThemes = journalOccupationModuleThemes;
    }

    public static class LessonPlanJournalTeacherForm {

        private Long id;
        @NotNull
        private EntityConnectionCommand teacher;
        private Boolean isFiller;
        private Boolean isConfirmer;

        public static LessonPlanJournalTeacherForm of(JournalTeacher journalTeacher) {
            LessonPlanJournalTeacherForm dto = new LessonPlanJournalTeacherForm();
            dto.setId(journalTeacher.getId());
            dto.setTeacher(AutocompleteResult.of(journalTeacher.getTeacher()));
            dto.setIsFiller(journalTeacher.getIsFiller());
            dto.setIsConfirmer(journalTeacher.getIsConfirmer());
            return dto;
        }

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

        public Boolean getIsFiller() {
            return isFiller;
        }

        public void setIsFiller(Boolean isFiller) {
            this.isFiller = isFiller;
        }

        public Boolean getIsConfirmer() {
            return isConfirmer;
        }

        public void setIsConfirmer(Boolean isConfirmer) {
            this.isConfirmer = isConfirmer;
        }
    }
}
