package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.VocationalGradeType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalLessonHoursDto;

public class JournalDto {

    private Long id;
    private String studyYear;
    private LocalDate studyYearStartDate;
    private LocalDate studyYearEndDate;
    private String nameEt;
    private List<String> studentGroups = new ArrayList<>();
    private List<String> journalTeachers = new ArrayList<>();
    private List<AutocompleteResult> curriculumModules = new ArrayList<>();
    private List<JournalModuleDescriptionDto> moduleDescriptions = new ArrayList<>();
    private List<JournalStudentIndividualCurriculumDto> individualCurriculums = new ArrayList<>();
    private JournalLessonHoursDto lessonHours;
    @ClassifierRestriction(MainClassCode.PAEVIK_STAATUS)
    private String status;
    private LocalDate endDate;
    private Boolean hasJournalStudents;
    private List<AutocompleteResult> journalRooms = new ArrayList<>();
    private Boolean moduleOutcomesAsEntries;
    private String assessment;
    private Boolean isDistinctiveAssessment;
    private Long moodleCourseId;
    
    private Boolean isReviewOk;
    private LocalDate reviewDate;
    private String reviewInfo;
    
    private Boolean canBeConfirmed;
    private Boolean canBeUnconfirmed;
    private Boolean canEdit;
    private Boolean canViewReview;
    private Boolean canReview;
    private Boolean canConnectStudyMaterials;

    public static JournalDto of(Journal journal) {
        JournalDto dto = EntityUtil.bindToDto(journal, new JournalDto(), "studyYear", "journalTeachers",
                "journalStudents", "journalEntries", "journalRooms", "isReviewOk", "reviewDate", "reviewInfo");
        dto.setStudyYear(EntityUtil.getCode(journal.getStudyYear().getYear()));
        dto.setStudyYearStartDate(journal.getStudyYear().getStartDate());
        dto.setStudyYearEndDate(journal.getStudyYear().getEndDate());

        if (!journal.getJournalOccupationModuleThemes().isEmpty()
                && journal.getJournalOccupationModuleThemes().get(0) != null) {
            CurriculumVersionOccupationModule cvom = journal.getJournalOccupationModuleThemes().get(0)
                    .getCurriculumVersionOccupationModuleTheme().getModule();
            CurriculumModule module = cvom.getCurriculumModule();
            JournalModuleDescriptionDto moduleDescription = EntityUtil.bindToDto(cvom,
                    new JournalModuleDescriptionDto());
            moduleDescription.setNameEt(module.getNameEt());
            moduleDescription.setNameEn(module.getNameEn());
            moduleDescription.setIsModule(Boolean.TRUE);
            moduleDescription.setAssessment(EntityUtil.getCode(cvom.getAssessment()));
            dto.moduleDescriptions.add(moduleDescription);
        }

        for (JournalOccupationModuleTheme theme : journal.getJournalOccupationModuleThemes()) {
            CurriculumVersionOccupationModuleTheme cvomt = theme.getCurriculumVersionOccupationModuleTheme();
            
            dto.getStudentGroups().add(theme.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode());
            dto.getCurriculumModules().add(AutocompleteResult.of(cvomt.getModule()));

            if (cvomt.getAssessment() != null) {
                JournalModuleDescriptionDto themeDescription = EntityUtil.bindToDto(cvomt, new JournalModuleDescriptionDto());
                themeDescription.setIsModule(Boolean.FALSE);
                themeDescription.setAssessment(EntityUtil.getCode(cvomt.getAssessment()));
                dto.moduleDescriptions.add(themeDescription);
            }
        }
        dto.setStudentGroups(dto.getStudentGroups().stream().distinct().collect(Collectors.toList()));
        dto.setCurriculumModules(dto.getCurriculumModules().stream().distinct().collect(Collectors.toList()));
        
        for (JournalTeacher teacher : journal.getJournalTeachers()) {
            dto.getJournalTeachers().add(PersonUtil.fullname(teacher.getTeacher().getPerson()));
        }
        dto.setJournalRooms(StreamUtil.toMappedList(r -> new AutocompleteResult(EntityUtil.getId(r.getRoom()),
                r.getRoom().getCode(), r.getRoom().getCode()), journal.getJournalRooms()));

        dto.setHasJournalStudents(Boolean.valueOf(!journal.getJournalStudents().isEmpty()));
        dto.setModuleOutcomesAsEntries(journal.getAddModuleOutcomes());
        dto.setAssessment(EntityUtil.getCode(journal.getAssessment()));
        dto.setIsDistinctiveAssessment(Boolean.valueOf(VocationalGradeType.KUTSEHINDAMISVIIS_E.name().equals(dto.getAssessment())));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public LocalDate getStudyYearStartDate() {
        return studyYearStartDate;
    }

    public void setStudyYearStartDate(LocalDate studyYearStartDate) {
        this.studyYearStartDate = studyYearStartDate;
    }

    public LocalDate getStudyYearEndDate() {
        return studyYearEndDate;
    }

    public void setStudyYearEndDate(LocalDate studyYearEndDate) {
        this.studyYearEndDate = studyYearEndDate;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public List<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<String> getJournalTeachers() {
        return journalTeachers;
    }

    public void setJournalTeachers(List<String> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public List<AutocompleteResult> getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(List<AutocompleteResult> curriculumModules) {
        this.curriculumModules = curriculumModules;
    }

    public JournalLessonHoursDto getLessonHours() {
        return lessonHours;
    }

    public void setLessonHours(JournalLessonHoursDto lessonHours) {
        this.lessonHours = lessonHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getHasJournalStudents() {
        return hasJournalStudents;
    }

    public void setHasJournalStudents(Boolean hasJournalStudents) {
        this.hasJournalStudents = hasJournalStudents;
    }

    public List<AutocompleteResult> getJournalRooms() {
        return journalRooms;
    }

    public void setJournalRooms(List<AutocompleteResult> journalRooms) {
        this.journalRooms = journalRooms;
    }

    public List<JournalModuleDescriptionDto> getModuleDescriptions() {
        return moduleDescriptions;
    }

    public void setModuleDescriptions(List<JournalModuleDescriptionDto> moduleDescriptions) {
        this.moduleDescriptions = moduleDescriptions;
    }

    public List<JournalStudentIndividualCurriculumDto> getIndividualCurriculums() {
        return individualCurriculums;
    }

    public void setIndividualCurriculums(List<JournalStudentIndividualCurriculumDto> individualCurriculums) {
        this.individualCurriculums = individualCurriculums;
    }

    public Boolean getModuleOutcomesAsEntries() {
        return moduleOutcomesAsEntries;
    }

    public void setModuleOutcomesAsEntries(Boolean moduleOutcomesAsEntries) {
        this.moduleOutcomesAsEntries = moduleOutcomesAsEntries;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Boolean getIsDistinctiveAssessment() {
        return isDistinctiveAssessment;
    }

    public void setIsDistinctiveAssessment(Boolean isDistinctiveAssessment) {
        this.isDistinctiveAssessment = isDistinctiveAssessment;
    }

    public Long getMoodleCourseId() {
        return moodleCourseId;
    }

    public void setMoodleCourseId(Long moodleCourseId) {
        this.moodleCourseId = moodleCourseId;
    }

    public Boolean getIsReviewOk() {
        return isReviewOk;
    }

    public void setIsReviewOk(Boolean isReviewOk) {
        this.isReviewOk = isReviewOk;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(String reviewInfo) {
        this.reviewInfo = reviewInfo;
    }

    public Boolean getCanBeConfirmed() {
        return canBeConfirmed;
    }

    public void setCanBeConfirmed(Boolean canBeConfirmed) {
        this.canBeConfirmed = canBeConfirmed;
    }

    public Boolean getCanBeUnconfirmed() {
        return canBeUnconfirmed;
    }

    public void setCanBeUnconfirmed(Boolean canBeUnconfirmed) {
        this.canBeUnconfirmed = canBeUnconfirmed;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanViewReview() {
        return canViewReview;
    }

    public void setCanViewReview(Boolean canViewReview) {
        this.canViewReview = canViewReview;
    }

    public Boolean getCanReview() {
        return canReview;
    }

    public void setCanReview(Boolean canReview) {
        this.canReview = canReview;
    }

    public Boolean getCanConnectStudyMaterials() {
        return canConnectStudyMaterials;
    }

    public void setCanConnectStudyMaterials(Boolean canConnectStudyMaterials) {
        this.canConnectStudyMaterials = canConnectStudyMaterials;
    }

}
