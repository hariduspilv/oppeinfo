package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
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
    @Size(max = 255)
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
    @Valid
    private List<LessonPlanGroupForm> groups;
    
    private List<AutocompleteResult> journalRooms;

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
    

    public List<LessonPlanGroupForm> getGroups() {
        return groups;
    }

    public void setGroups(List<LessonPlanGroupForm> groups) {
        this.groups = groups;
    }

    public List<AutocompleteResult> getJournalRooms() {
        return journalRooms;
    }

    public void setJournalRooms(List<AutocompleteResult> journalRooms) {
        this.journalRooms = journalRooms;
    }

    public static class LessonPlanGroupForm {

        private Long studentGroup;
        @NotNull
        private Long curriculumVersionOccupationModule;
        @NotNull
        private List<Long> curriculumVersionOccupationModuleThemes;
        private EntityConnectionCommand group;
        private Long curriculumVersion;

        public static LessonPlanGroupForm of(Entry<StudentGroup, List<JournalOccupationModuleTheme>> entry) {
            LessonPlanGroupForm dto = new LessonPlanGroupForm();
            dto.setCurriculumVersionOccupationModuleThemes(entry.getValue().stream()
                    .map(it -> EntityUtil.getId(it.getCurriculumVersionOccupationModuleTheme())).collect(Collectors.toList()));
            dto.setCurriculumVersionOccupationModule(
                    EntityUtil.getId(entry.getValue().get(0).getCurriculumVersionOccupationModuleTheme().getModule()));
            dto.setStudentGroup(entry.getKey().getId());
            dto.setGroup(AutocompleteResult.of(entry.getKey()));
            dto.setCurriculumVersion(EntityUtil.getId(entry.getKey().getCurriculumVersion()));
            return dto;
        }

        public Long getStudentGroup() {
            return studentGroup;
        }

        public void setStudentGroup(Long studentGroup) {
            this.studentGroup = studentGroup;
        }

        public Long getCurriculumVersionOccupationModule() {
            return curriculumVersionOccupationModule;
        }

        public void setCurriculumVersionOccupationModule(Long curriculumVersionOccupationModule) {
            this.curriculumVersionOccupationModule = curriculumVersionOccupationModule;
        }

        public List<Long> getCurriculumVersionOccupationModuleThemes() {
            return curriculumVersionOccupationModuleThemes;
        }

        public void setCurriculumVersionOccupationModuleThemes(List<Long> curriculumVersionOccupationModuleThemes) {
            this.curriculumVersionOccupationModuleThemes = curriculumVersionOccupationModuleThemes;
        }

        public EntityConnectionCommand getGroup() {
            return group;
        }

        public void setGroup(EntityConnectionCommand group) {
            this.group = group;
        }

        public Long getCurriculumVersion() {
            return curriculumVersion;
        }

        public void setCurriculumVersion(Long curriculumVersion) {
            this.curriculumVersion = curriculumVersion;
        }

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
