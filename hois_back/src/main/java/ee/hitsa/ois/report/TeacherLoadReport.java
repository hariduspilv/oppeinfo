package ee.hitsa.ois.report;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherOtherLoad;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.repository.model.ITeacherLoadReportResult;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.TeacherOtherLoadDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeacherLoadReport {

    private final LocalDate today;
    private final String fullname;
    private final AutocompleteResult occupation;
    private final List<AutocompleteResult> ehisPositions;
    private final AutocompleteResult studyYear;

    // all loads
    private final BigDecimal loadHigherVocational;
    private final BigDecimal loadVocational;
    private final BigDecimal loadHigher;
    private final BigDecimal loadOther;
    private final BigDecimal percentAll;
    private final BigDecimal loadAll;

    // primary loads
    private final BigDecimal loadCreditsEAP;
    private final List<SubjectJournalDto> primaryLoads;

    // other loads
    private final List<TeacherOtherLoadDto> otherLoads;

    // meta data
    private final Boolean isHigherTeacher;
    private final Boolean hasHigher;
    private final Boolean hasVocational;

    public TeacherLoadReport(Teacher teacher, StudyYear studyYear,
                             SchoolService.SchoolType schoolType,
                             List<ITeacherLoadReportResult> higherResults,
                             List<ITeacherLoadReportResult> vocationalResults,
                             Map<Long, StudyPeriod> studyPeriodsById,
                             Language lang) {
        today = LocalDate.now();
        fullname = teacher.getPerson().getFullname();
        occupation = AutocompleteResult.of(teacher.getTeacherOccupation());
        ehisPositions = teacher.getTeacherPositionEhis().stream()
                .map(AutocompleteResult::of)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.studyYear = AutocompleteResult.of(studyYear);

        this.isHigherTeacher = Boolean.TRUE.equals(schoolType.isHigher());

        BigDecimal loadHigher = BigDecimal.ZERO;
        BigDecimal loadVocational = BigDecimal.ZERO;
        // Vocational
        hasVocational = Boolean.valueOf(!vocationalResults.isEmpty());
        loadVocational = loadVocational.add(BigDecimal.valueOf(vocationalResults.stream()
                .map(ITeacherLoadReportResult::getHoursContact)
                .map(BigDecimal::doubleValue)
                .mapToDouble(Double::doubleValue).sum()));
        // Higher
        hasHigher = Boolean.valueOf(!higherResults.isEmpty());
        loadHigher = loadHigher.add(BigDecimal.valueOf(higherResults.stream()
                .map(ITeacherLoadReportResult::getHoursContact)
                .map(BigDecimal::doubleValue)
                .mapToDouble(Double::doubleValue).sum()));

        primaryLoads = Stream.of(
                vocationalResults.stream()
                        .map(r -> new SubjectJournalDto(r, null, Boolean.FALSE)),
                higherResults.stream()
                        .map(r -> new SubjectJournalDto(r, studyPeriodsById.get(r.getStudyPeriodId()), Boolean.TRUE)))
                .flatMap(Function.identity())
                .sorted(Comparator.comparing(dto -> Language.EN.equals(lang) ? dto.getNameEn() : dto.getNameEt()))
                .collect(Collectors.toList());

        this.loadCreditsEAP = StreamUtil.sumBigDecimals(SubjectJournalDto::getCredits,
                primaryLoads.stream().filter(dto -> Boolean.TRUE.equals(dto.getHigher())).collect(Collectors.toList()));
        this.loadHigher = BigDecimal.ZERO.add(loadHigher);
        this.loadVocational = BigDecimal.ZERO.add(loadVocational);
        this.loadHigherVocational = BigDecimal.ZERO.add(this.loadHigher).add(this.loadVocational);

        BigDecimal loadOther = BigDecimal.ZERO;
        BigDecimal percentAll = BigDecimal.ZERO;
        otherLoads = new ArrayList<>();
        for (TeacherOtherLoad load : teacher.getLoads().stream()
                .filter(tol -> EntityUtil.getId(tol.getStudyYear()).equals(EntityUtil.getId(studyYear)))
                .sorted(Comparator.comparing(TeacherOtherLoad::getNameEt))
                .collect(Collectors.toList())) {
            loadOther = loadOther.add(load.getHours());
            percentAll = percentAll.add(load.getPercent());
            otherLoads.add(TeacherOtherLoadDto.of(load));
        }
        this.loadOther = loadOther;
        this.percentAll = percentAll;

        this.loadAll = BigDecimal.ZERO.add(this.loadHigher).add(this.loadVocational).add(this.loadOther);
    }

    public LocalDate getToday() {
        return today;
    }

    public String getFullname() {
        return fullname;
    }

    public AutocompleteResult getOccupation() {
        return occupation;
    }

    public List<AutocompleteResult> getEhisPositions() {
        return ehisPositions;
    }

    public AutocompleteResult getStudyYear() {
        return studyYear;
    }

    public BigDecimal getLoadHigherVocational() {
        return loadHigherVocational;
    }

    public BigDecimal getLoadVocational() {
        return loadVocational;
    }

    public BigDecimal getLoadHigher() {
        return loadHigher;
    }

    public BigDecimal getLoadOther() {
        return loadOther;
    }

    public BigDecimal getPercentAll() {
        return percentAll;
    }

    public BigDecimal getLoadAll() {
        return loadAll;
    }

    public List<SubjectJournalDto> getPrimaryLoads() {
        return primaryLoads;
    }

    public List<TeacherOtherLoadDto> getOtherLoads() {
        return otherLoads;
    }

    public BigDecimal getLoadCreditsEAP() {
        return loadCreditsEAP;
    }

    public Boolean getIsHigherTeacher() {
        return isHigherTeacher;
    }

    public Boolean getHasHigher() {
        return hasHigher;
    }

    public Boolean getHasVocational() {
        return hasVocational;
    }

    public static class SubjectJournalDto implements Translatable {

        private final String subjectCode;
        private final String nameEt;
        private final String nameEn;
        private final String groups;
        private final String subgroups;
        private final AutocompleteResult semester;
        private final String assessment;
        private final Boolean higher;
        private final BigDecimal contactHours;
        private final BigDecimal credits;

        public SubjectJournalDto(ITeacherLoadReportResult row, StudyPeriod studyPeriod, Boolean higher) {
            subjectCode = row.getSubjectCode();
            nameEt = row.getNameEt();
            nameEn = row.getNameEn();
            groups = row.getGroups();
            subgroups = row.getSubgroups();
            semester = studyPeriod != null ? AutocompleteResult.of(studyPeriod) : null;
            assessment = row.getAssessmentCode();
            this.higher = higher;
            credits = row.getSubjectCredits();
            contactHours = row.getHoursContact();
        }

        public String getSubjectCode() {
            return subjectCode;
        }

        public String getGroups() {
            return groups;
        }

        public String getSubgroups() {
            return subgroups;
        }

        public AutocompleteResult getSemester() {
            return semester;
        }

        public String getAssessment() {
            return assessment;
        }

        public Boolean getHigher() {
            return higher;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public BigDecimal getContactHours() {
            return contactHours;
        }

        @Override
        public String getNameEt() {
            return nameEt;
        }

        @Override
        public String getNameEn() {
            return nameEn;
        }
    }
}
