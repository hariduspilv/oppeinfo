package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.enums.TimetableStatus;
import ee.hitsa.ois.enums.TimetableType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.TimetableRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalForTimetableDto;
import ee.hitsa.ois.web.dto.timetable.StudentGroupForTimetableDto;
import ee.hitsa.ois.web.dto.timetable.SubjectTeacherPairDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDatesDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;

@Transactional
@Service
public class TimetableService {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private EntityManager em;
    @Autowired
    private TimetableRepository timetableRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private SubjectStudyPeriodService subjectStudyPeriodService;
    
    public TimetableDto get(HoisUserDetails user, Timetable timetable) {
        TimetableDto timetableDto = new TimetableDto();
        timetableDto.setId(EntityUtil.getId(timetable));
        timetableDto.setStudyYears(autocompleteService.studyYears(user.getSchoolId()));
        timetableDto.setStudyPeriods(autocompleteService.studyPeriodsWithYear(user.getSchoolId()));
        timetableDto.setCurrentStudyPeriod(studyYearService.getCurrentStudyPeriod(user.getSchoolId()));
        timetableDto.setCode(timetable.getIsHigher().booleanValue() ? TimetableType.TUNNIPLAAN_LIIK_H.name() : TimetableType.TUNNIPLAAN_LIIK_V.name());
        timetableDto.setStartDate(timetable.getStartDate());
        timetableDto.setEndDate(timetable.getEndDate());
        return timetableDto;
    }
    
    public TimetableDto getForView(HoisUserDetails user, Timetable timetable) {
        TimetableDto timetableDto = new TimetableDto();
        timetableDto.setId(EntityUtil.getId(timetable));
        timetableDto.setStartDate(timetable.getStartDate());
        timetableDto.setEndDate(timetable.getEndDate());
        timetableDto.setJournals(getJournalsForTimetable(timetable));
        if(timetable.getIsHigher().booleanValue()) {
            timetableDto.setPairs(getPairsforTimetable(timetable));
        }
        timetableDto.setStatus(timetable.getStatus().getCode());
        return timetableDto;
    }

    public Map<String, ?> managementSearchFormData(Long schoolId) {
        Map<String, Object> data = new HashMap<>();
        data.put("studyYears", autocompleteService.studyYears(schoolId));
        data.put("studyPeriods", autocompleteService.studyPeriodsWithYear(schoolId));
        data.put("currentStudyPeriod", studyYearService.getCurrentStudyPeriod(schoolId));
        SchoolType type = schoolService.schoolType(schoolId);
        data.put("higher", Boolean.valueOf(type.isHigher()));
        data.put("vocational", Boolean.valueOf(type.isVocational()));
        return data;
    }

    public Page<TimetableManagementSearchDto> searchTimetableForManagement(TimetableManagementSearchCommand criteria,
            Pageable pageable, HoisUserDetails user) {
        if (TimetableType.isHigher(criteria.getType())) {
            return searchHigherTimetableForManagement(criteria, pageable, user);
        }
        return searchVocationalTimetableForManagement(criteria, pageable, user);
    }

    public Page<TimetableManagementSearchDto> searchHigherTimetableForManagement(
            TimetableManagementSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from timetable t")
                .sort("t.start_date desc");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableManagementSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLocalDate(r, 2),
                    resultAsLocalDate(r, 3));
        });
    }

    public Page<TimetableManagementSearchDto> searchVocationalTimetableForManagement(
            TimetableManagementSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from timetable t")
                .sort("t.start_date desc");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date";
        List<?> data = qb.select(select, em).getResultList();
        
        List<TimetableManagementSearchDto> wrappedData = StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 0);
            String status = resultAsString(r, 1);
            LocalDate start = resultAsLocalDate(r, 2);
            LocalDate end = resultAsLocalDate(r, 3);
            return new TimetableManagementSearchDto(id, status, start, end);
        }, data);
        
        StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());
        wrappedData = addMissingDatesToBlocked(sp, wrappedData);
        
        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > wrappedData.size() ? wrappedData.size() : (start + pageable.getPageSize());
        return new PageImpl<>(wrappedData.subList(start, end), pageable, wrappedData.size());
    }
    
    private List<TimetableManagementSearchDto> addMissingDatesToBlocked(StudyPeriod sp, List<TimetableManagementSearchDto> data) {
        LocalDate currentStart = sp.getStartDate();
        LocalDate currentEnd = sp.getStartDate();
        LocalDate spEnd = sp.getEndDate();
        Classifier tunniplaanStaatusA = classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_A.name());
        List<TimetableManagementSearchDto> toAdd = new ArrayList<>();
        
        Collections.sort(data, new Comparator<TimetableManagementSearchDto>() {
            @Override
            public int compare(TimetableManagementSearchDto dto1, TimetableManagementSearchDto dto2) {
                return dto1.getStart().compareTo(dto2.getStart());
            }
        });
        
        for(TimetableManagementSearchDto dto : data) {
            //TODO: put the while into a function , both whiles are same
            while(currentStart.isBefore(dto.getStart())) {
                currentEnd = currentStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                currentEnd = currentEnd.isBefore(dto.getStart()) ? currentEnd : dto.getStart().minusDays(1);
                toAdd.add(new TimetableManagementSearchDto(null, tunniplaanStaatusA.getCode(), currentStart, currentEnd));
                currentStart = currentStart.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
            currentEnd = dto.getEnd().plusDays(1);
            currentStart = currentEnd.getDayOfWeek() == DayOfWeek.MONDAY ? currentEnd : currentEnd.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        if(currentEnd.isBefore(spEnd)) {
            while(currentStart.isBefore(spEnd)) {
                currentEnd = currentStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                currentEnd = currentEnd.isBefore(spEnd) ? currentEnd : spEnd;
                toAdd.add(new TimetableManagementSearchDto(null, tunniplaanStaatusA.getCode(), currentStart, currentEnd));
                currentStart = currentStart.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
        }
        
        //TODO: add sort to a function 
        Collections.sort(data, new Comparator<TimetableManagementSearchDto>() {
            @Override
            public int compare(TimetableManagementSearchDto dto1, TimetableManagementSearchDto dto2) {
                return dto2.getStart().compareTo(dto1.getStart());
            }
        });
        
        Collections.sort(toAdd, new Comparator<TimetableManagementSearchDto>() {
            @Override
            public int compare(TimetableManagementSearchDto dto1, TimetableManagementSearchDto dto2) {
                return dto2.getStart().compareTo(dto1.getStart());
            }
        });
        
        toAdd.addAll(data);
        return toAdd;
    }

    public List<TimetableDatesDto> getBlockedDatesForPeriod(HoisUserDetails user, Long studyPeriod, String code, Long currentTimetable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from timetable t");

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", studyPeriod);
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", Boolean.valueOf(TimetableType.isHigher(code)));
        qb.optionalCriteria("t.id != :currentTimetable", "currentTimetable", currentTimetable);
        
        List<?> data = qb.select("t.id, t.start_date, t.end_date", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            LocalDate from = resultAsLocalDate(r, 1);
            LocalDate thru = resultAsLocalDate(r, 2);
            return new TimetableDatesDto(from, thru);
        }, data);
    }

    public Timetable createTimetable(HoisUserDetails user, TimetableEditForm form) {
        Timetable timetable = new Timetable();
        timetable.setIsHigher(Boolean.valueOf(TimetableType.isHigher(form.getCode())));
        timetable.setSchool(em.getReference(School.class, user.getSchoolId()));
        timetable.setStatus(classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_S.name()));
        EntityUtil.bindToEntity(form, timetable);
        timetable.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        List<TimetableObject> timetableObjects = new ArrayList<>();

        List<Journal> readyJournals = journalRepository.findAllBySchool_idAndStudyYear_id(
                EntityUtil.getId(timetable.getSchool()), EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        for (Journal j : readyJournals) {
            TimetableObject timetableObject = new TimetableObject();
            timetableObject.setTimetable(timetable);
            timetableObject.setJournal(j);
            timetableObjects.add(timetableObject);
        }

        if (timetable.getIsHigher().booleanValue()) {
            List<SubjectStudyPeriod> readySubjectStudyPeriods = subjectStudyPeriodService
                    .getSubjectStudyPeriodsForSchoolAndPeriod(EntityUtil.getId(timetable.getSchool()),
                            EntityUtil.getId(timetable.getStudyPeriod()));
            for (SubjectStudyPeriod s : readySubjectStudyPeriods) {
                TimetableObject timetableObject = new TimetableObject();
                timetableObject.setTimetable(timetable);
                timetableObject.setSubjectStudyPeriod(s);
                timetableObjects.add(timetableObject);
            }
        }
        timetable.setTimetableObjects(timetableObjects);
        return timetableRepository.save(timetable);
    }

    public Timetable save(HoisUserDetails user, TimetableEditForm form, Timetable timetable) {
        EntityUtil.bindToEntity(form, timetable);
        timetable.setIsHigher(Boolean.valueOf(TimetableType.isHigher(form.getCode())));
        timetable.setSchool(em.getReference(School.class, user.getSchoolId()));
        timetable.setStatus(classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_S.name()));
        timetable.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return timetableRepository.save(timetable);
    }
    
    public List<JournalForTimetableDto> getJournalsForTimetable(Timetable timetable) {
        String from = "from timetable t"
                + " inner join timetable_object too on too.timetable_id = t.id"
                + " inner join journal j on j.id = too.journal_id"
                + " inner join journal_omodule_theme jot on jot.id = j.id"
                + " inner join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id"
                + " inner join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id"
                + " inner join curriculum_version cv on cv.id = cvo.curriculum_version_id"
                + " inner join curriculum c on c.id = cv.curriculum_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("t.id = :timetableId", "timetableId", EntityUtil.getId(timetable));
        qb.optionalCriteria("c.is_higher = :isHigher" , "isHigher", timetable.getIsHigher());
        
        String select = "distinct cv.id, c.name_et, c.name_en, cv.code, c.orig_study_level_code";
        List<?> data = qb.select(select, em).getResultList();
        Map<Long, JournalForTimetableDto> curriculums = StreamUtil.toMap(r -> (resultAsLong(r, 0)), r -> (new JournalForTimetableDto(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4))), data);
        if(!curriculums.isEmpty()) {
            JpaQueryUtil.NativeQueryBuilder qbTwo = new JpaQueryUtil.NativeQueryBuilder(from + " inner join student_group sg on sg.curriculum_version_id = cv.id");
            qbTwo.requiredCriteria("cv.id in (:curriculumVersionIds)", "curriculumVersionIds", curriculums.keySet());
            qbTwo.requiredCriteria("t.id = :timetableId", "timetableId", EntityUtil.getId(timetable));
            qb.optionalCriteria("c.is_higher = :isHigher" , "isHigher", timetable.getIsHigher());
            List<?> studentGroups = qbTwo.select("distinct sg.id, sg.code, cv.id as curriculum_version_id", em).getResultList();
            List<StudentGroupForTimetableDto> groups = StreamUtil.toMappedList(r -> (new StudentGroupForTimetableDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 2))), studentGroups);
            
            for(StudentGroupForTimetableDto group : groups) {
                curriculums.get(group.getCurriculumId()).getGroups().add(group);
            }
        }
        return new ArrayList<>(curriculums.values());
    }
    
    public List<SubjectTeacherPairDto> getPairsforTimetable(Timetable timetable) {
        String from = "from timetable t"
                + " inner join timetable_object too on too.timetable_id = t.id"
                + " inner join subject_study_period ssp on ssp.id = too.id"
                + " inner join subject s on s.id = ssp.subject_id"
                + " inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " inner join teacher tea on tea.id = sspt.teacher_id"
                + " inner join person p on p.id = tea.person_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("t.id = :timetableId", "timetableId", EntityUtil.getId(timetable));
        String select = "ssp.id, s.name_et, s.name_en, s.code, p.firstname, p.lastname";
        List<?> data = qb.select(select, em).getResultList();
        
        return StreamUtil.toMappedList(r -> (new SubjectTeacherPairDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5))), data);
    }

}
