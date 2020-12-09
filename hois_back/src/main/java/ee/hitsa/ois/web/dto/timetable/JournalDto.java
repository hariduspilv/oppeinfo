package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
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
import ee.hitsa.ois.web.dto.PersonResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalLessonHoursDto;

public class JournalDto {

    private Long id;
    private Long studyYearId;
    private String studyYear;
    private LocalDate studyYearStartDate;
    private LocalDate studyYearEndDate;
    private String nameEt;
    private List<String> studentGroups = new ArrayList<>();
    private List<PersonResult> journalTeachers = new ArrayList<>();
    private List<AutocompleteResult> curriculumModules = new ArrayList<>();
    private List<String> responsiblesForModules = new ArrayList<>();
    private List<JournalCurriculumVersionDto> curriculumVersions = new ArrayList<>();
    private List<JournalStudentIndividualCurriculumDto> individualCurriculums = new ArrayList<>();
    private JournalLessonHoursDto lessonHours;
    @ClassifierRestriction(MainClassCode.PAEVIK_STAATUS)
    private String status;
    private LocalDate endDate;
    private Boolean hasJournalStudents;
    private List<AutocompleteResult> journalRooms = new ArrayList<>();
    private Boolean includesOutcomes;
    private Boolean isIndividual;
    private Boolean finalEntryAllowed;
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
        dto.setStudyYearId(EntityUtil.getId(journal.getStudyYear()));
        dto.setStudyYear(EntityUtil.getCode(journal.getStudyYear().getYear()));
        dto.setStudyYearStartDate(journal.getStudyYear().getStartDate());
        dto.setStudyYearEndDate(journal.getStudyYear().getEndDate());

        Map<Long, CurriculumVersionResult> curriculumVersionsById = new HashMap<>();
        Map<Long, Map<Long, CurriculumVersionOccupationModuleResult>> modulesByCurriculumVersion = new HashMap<>();
        Map<Long, List<AutocompleteResult>> themesByCurriculumVersion = new HashMap<>();

        for (JournalOccupationModuleTheme theme : journal.getJournalOccupationModuleThemes()) {
            CurriculumVersionOccupationModuleTheme cvomt = theme.getCurriculumVersionOccupationModuleTheme();
            CurriculumVersionOccupationModule cvom = cvomt.getModule();
            CurriculumVersion curriculumVersion = cvom.getCurriculumVersion();
            
            dto.getStudentGroups().add(theme.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode());
            dto.getCurriculumModules().add(AutocompleteResult.of(cvom));
            if (theme.getLessonPlanModule().getTeacher() != null) {
                String responsibleForModule = PersonUtil.fullname(theme.getLessonPlanModule().getTeacher().getPerson());
                if (!dto.getResponsiblesForModules().contains(responsibleForModule)) {
                    dto.getResponsiblesForModules().add(responsibleForModule);
                }
            }

            CurriculumVersionOccupationModuleResult curriculumModuleResult = AutocompleteResult.of(cvom, false);
            CurriculumVersionResult curriculumVersionResult = AutocompleteResult.of(curriculumVersion);

            Long cvId = curriculumVersionResult.getId();
            if (!themesByCurriculumVersion.containsKey(cvId)) {
                themesByCurriculumVersion.put(cvId, new ArrayList<>());
            }
            themesByCurriculumVersion.get(cvId).add(new AutocompleteResult(cvomt.getId(), cvomt.getNameEt(), null));

            if (!modulesByCurriculumVersion.containsKey(cvId)) {
                modulesByCurriculumVersion.put(cvId, new HashMap<>());
            }
            modulesByCurriculumVersion.get(cvId).put(curriculumModuleResult.getId(), curriculumModuleResult);
            curriculumVersionsById.put(cvId, curriculumVersionResult);
        }

        for (CurriculumVersionResult cv : curriculumVersionsById.values()) {
            JournalCurriculumVersionDto cvDto = new JournalCurriculumVersionDto();
            cvDto.setId(cv.getId());
            cvDto.setCurriculumId(cv.getCurriculum());
            cvDto.setNameEt(cv.getNameEt());
            cvDto.setNameEn(cv.getNameEn());
            cvDto.getModules().addAll(modulesByCurriculumVersion.get(cvDto.getId()).values());
            cvDto.getThemes().addAll(themesByCurriculumVersion.get(cvDto.getId()));
            dto.getCurriculumVersions().add(cvDto);
        }

        dto.setStudentGroups(dto.getStudentGroups().stream().distinct().collect(Collectors.toList()));
        dto.setCurriculumModules(dto.getCurriculumModules().stream().distinct().collect(Collectors.toList()));

        for (JournalTeacher teacher : journal.getJournalTeachers()) {
            dto.getJournalTeachers().add(PersonResult.of(teacher.getTeacher()));
        }
        dto.getJournalTeachers().sort(PersonResult.SORT);

        dto.setJournalRooms(StreamUtil.toMappedList(r -> new AutocompleteResult(EntityUtil.getId(r.getRoom()),
                r.getRoom().getCode(), r.getRoom().getCode()), journal.getJournalRooms()));

        dto.setHasJournalStudents(Boolean.valueOf(!journal.getJournalStudents().isEmpty()));
        dto.setAssessment(EntityUtil.getNullableCode(journal.getAssessment()));
        dto.setIsDistinctiveAssessment(Boolean.valueOf(VocationalGradeType.KUTSEHINDAMISVIIS_E.name().equals(dto.getAssessment())));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudyYearId() {
        return studyYearId;
    }

    public void setStudyYearId(Long studyYearId) {
        this.studyYearId = studyYearId;
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

    public List<PersonResult> getJournalTeachers() {
        return journalTeachers;
    }

    public void setJournalTeachers(List<PersonResult> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public List<AutocompleteResult> getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(List<AutocompleteResult> curriculumModules) {
        this.curriculumModules = curriculumModules;
    }

    public List<String> getResponsiblesForModules() {
        return responsiblesForModules;
    }

    public void setResponsiblesForModules(List<String> responsiblesForModules) {
        this.responsiblesForModules = responsiblesForModules;
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

    public List<JournalCurriculumVersionDto> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(List<JournalCurriculumVersionDto> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }

    public List<JournalStudentIndividualCurriculumDto> getIndividualCurriculums() {
        return individualCurriculums;
    }

    public void setIndividualCurriculums(List<JournalStudentIndividualCurriculumDto> individualCurriculums) {
        this.individualCurriculums = individualCurriculums;
    }

    public Boolean getIncludesOutcomes() {
        return includesOutcomes;
    }

    public void setIncludesOutcomes(Boolean includesOutcomes) {
        this.includesOutcomes = includesOutcomes;
    }

    public Boolean getIsIndividual() {
       return isIndividual;
    }

    public void setIsIndividual(Boolean isIndividual) {
        this.isIndividual = isIndividual;
    }

    public Boolean getFinalEntryAllowed() {
        return finalEntryAllowed;
    }

    public void setFinalEntryAllowed(Boolean finalEntryAllowed) {
        this.finalEntryAllowed = finalEntryAllowed;
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
