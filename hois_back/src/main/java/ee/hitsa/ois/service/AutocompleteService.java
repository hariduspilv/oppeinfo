package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.enums.ScholarshipType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ResponseStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.poll.PollService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EnterpriseUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.BuildingAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.commandobject.CommitteeAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAndSubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.commandobject.PracticeEvaluationAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.RoomAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.SpecialitiesAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionOccupationModuleAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionOccupationModuleThemeAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.VocationalModuleCommand;
import ee.hitsa.ois.web.commandobject.poll.PollAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.poll.PollQuestionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.studymaterial.StudyMaterialAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectAutocompleteCommand;
import ee.hitsa.ois.web.curriculum.CurriculumVersionHigherModuleAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.JournalAutocompleteResult;
import ee.hitsa.ois.web.dto.LiteralResult;
import ee.hitsa.ois.web.dto.OccupiedAutocompleteResult;
import ee.hitsa.ois.web.dto.PersonDto;
import ee.hitsa.ois.web.dto.RoomAutocompleteResult;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.SchoolWithLogo;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.SpecialityAutocompleteResult;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearIdDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.SubjectResult;
import ee.hitsa.ois.web.dto.SupervisorDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOModulesAndThemesResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseResult;
import ee.hitsa.ois.web.dto.poll.PollResultsQuestionDto;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupResult;

@Transactional
@Service
public class AutocompleteService {

    private static final int MAX_ITEM_COUNT = 20;
    private static final List<String> POSITIVE_HIGHER_GRADES = Stream.of(HigherAssessment.values())
            .filter(HigherAssessment::getIsPositive).map(HigherAssessment::name).collect(Collectors.toList());
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
    private EmailGeneratorService emailGeneratorService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private StudyYearService studyYearService;

    public List<AutocompleteResult> basemodules(Long schoolId, BaseModuleAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from base_module b");
        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("b.name_et", "b.name_en"), "name", lookup.getName());
        if (lookup.getNotExpired().equals(Boolean.TRUE)) {
            qb.requiredCriteria("b.valid_from <= :now and (b.valid_thru is null or b.valid_thru >= :now)", "now", LocalDate.now());
        }
        List<?> data = qb.select("b.id, b.name_et, b.name_en, b.add_name_et", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String additionalName = resultAsString(r, 3);
            if (additionalName != null) {
                String nameEn = resultAsString(r, 2);
                return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1) + " (" + additionalName + ")",
                        nameEn != null ? nameEn + " (" + additionalName + ")" : null);
            }
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2));
        }, data);
    }
    
    public List<AutocompleteResult> buildings(Long schoolId, BuildingAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from building b");

        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("b.is_dormitory = :isDormitory", "isDormitory", lookup.getIsDormitory());

        qb.sort("b.code, b.name");
        List<?> data = qb.select("b.id, b.code, b.name", em).getResultList(); 

        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1) + " - " + resultAsString(r, 2);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    public List<RoomAutocompleteResult> rooms(Long schoolId, RoomAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from room r inner join building b on b.id = r.building_id");

        // For case when ID is given in lookup
        qb.optionalCriteria("r.id = :rId", "rId", lookup.getId());
        
        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("b.code", "r.code",
                String.format("concat(%s,'-',%s,' (','%s',' ',%s,')')", "b.code", "r.code", Language.ET.equals(lookup.getLang()) ? "kohti" : "seats", "r.seats")), "code", lookup.getName());
        qb.optionalCriteria("b.id in (:buildingIds)", "buildingIds", lookup.getBuildingIds());
        qb.optionalCriteria("r.is_study = :isStudy", "isStudy", lookup.getIsStudy());
        qb.optionalCriteria("b.is_dormitory = :isDormitory", "isDormitory", lookup.getIsDormitory());

        qb.sort("b.code, r.code");
        List<?> data = qb.select("r.id r_id, b.id b_id, b.code as building_code, r.code as room_code, r.seats", em)
                .getResultList();

        Map<Long, RoomAutocompleteResult> roomsResult = new LinkedHashMap<>();
        if (!data.isEmpty()) {
            roomsResult = data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0),
                    r -> new RoomAutocompleteResult(resultAsLong(r, 0), resultAsLong(r, 1), resultAsString(r, 2),
                    resultAsString(r, 3), resultAsLong(r, 4)), (v1, v2) -> v1, LinkedHashMap::new));
        }

        if (Boolean.TRUE.equals(lookup.getOccupied()) && !roomsResult.isEmpty()) {
            if (lookup.getDate() != null && lookup.getStartTime() != null && lookup.getEndTime() != null) {
                setRoomsOccupationStatus(lookup, roomsResult);
            }
        }
        return new ArrayList<>(roomsResult.values());
    }

    private void setRoomsOccupationStatus(RoomAutocompleteCommand lookup, Map<Long, RoomAutocompleteResult> roomsResult) {
        List<Long> roomIds = roomsResult.values().stream().map(t -> t.getId()).collect(Collectors.toList());
        
        List<LocalDateTime> starts = new ArrayList<>();
        List<LocalDateTime> ends = new ArrayList<>();
        starts.add(LocalDateTime.of(lookup.getDate(), 
                LocalTime.of(lookup.getStartTime().getHour(), lookup.getStartTime().getMinute())));
        ends.add(LocalDateTime.of(lookup.getDate(), 
                LocalTime.of(lookup.getEndTime().getHour(), lookup.getEndTime().getMinute())));
        if (lookup.getWeekAmount() != null) {
            eventRepeatStartAndEndTimes(lookup.getRepeatCode(), lookup.getWeekAmount(), starts, ends);
        } else if (lookup.getTimetable() != null) {
            eventRepeatStartAndEndTimes(lookup.getTimetable(), lookup.getRepeatCode(), starts, ends);
        }
        
        JpaNativeQueryBuilder occupiedQb = new JpaNativeQueryBuilder("from timetable_event te "
                + "join timetable_event_time tem on te.id = tem.timetable_event_id "
                + "join timetable_event_room ter on tem.id = ter.timetable_event_time_id");
        occupiedQb.requiredCriteria("ter.room_id in (:roomIds)", "roomIds", roomIds);
        occupiedQb.filter(getTimeFilter(starts, ends));
        
        List<?> occupiedRooms = occupiedQb.select("ter.room_id", em).getResultList();
        if (!occupiedRooms.isEmpty()) {
            List<Long> occupiedRoomIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), occupiedRooms); 
            for (Long id : occupiedRoomIds) {
                roomsResult.get(id).setIsOccupied(Boolean.TRUE);
            }
        }
    }

    private static String getTimeFilter(List<LocalDateTime> starts, List<LocalDateTime> ends) {
        String timeFilter = "";
        for (int i = 0; i < starts.size(); i++) {
            timeFilter += timeFilter.isEmpty() ? "(" : " or ";
            timeFilter += "(tem.start < '" + JpaQueryUtil.parameterAsTimestamp(ends.get(i)) + "' and tem.end > '"
                    + JpaQueryUtil.parameterAsTimestamp(starts.get(i)) + "')";
        }
        timeFilter += ")";
        return timeFilter;
    }

    public List<Classifier> classifierForAutocomplete(ClassifierSearchCommand classifierSearchCommand) {
        String nameField = Language.EN.equals(classifierSearchCommand.getLang()) ? "nameEn" : "nameEt";
        JpaQueryBuilder<Classifier> qb = new JpaQueryBuilder<>(Classifier.class, "c").sort(nameField);
        qb.requiredCriteria("c.mainClassCode = :mainClassCode", "mainClassCode", classifierSearchCommand.getMainClassCode());
        qb.optionalCriteria("c.valid = :valid", "valid", classifierSearchCommand.getValid());
        qb.optionalContains("c." + nameField, "name", classifierSearchCommand.getName());

        return qb.select(em).setMaxResults(MAX_ITEM_COUNT).getResultList();
     }

    public List<ClassifierSelection> classifiers(List<String> mainClassCodes) {
        // ClassifierSelection includes attributes for filtering in frontend
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from classifier c");

        qb.requiredCriteria("c.main_class_code in (:mainClassCodes)", "mainClassCodes", mainClassCodes);

        List<?> data = qb.select("c.code, c.name_et, c.name_en, c.name_ru, c.valid, c.is_higher, c.is_vocational"
                + ", c.main_class_code, c.value, c.value2, c.valid_from, c.valid_thru, c.extraval1, c.extraval2", em)
                .getResultList();
        List<ClassifierSelection> result = StreamUtil.toMappedList(
                r -> new ClassifierSelection(resultAsString(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3), resultAsBoolean(r, 4), resultAsBoolean(r, 5), resultAsBoolean(r, 6),
                        resultAsString(r, 7), resultAsString(r, 8), resultAsString(r, 9), resultAsLocalDate(r, 10),
                        resultAsLocalDate(r, 11), resultAsString(r, 12), resultAsString(r, 13)),
                data);

        return ClassifierUtil.sort(mainClassCodes, result);
    }

    /**
     * Get list of classifiers with parents (bound via ClassifierConnect) for filtering in front-end
     */
    public List<ClassifierSelection> classifiersWithParents(List<String> mainClassCodes) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from classifier c left join (select array_agg(cc.connect_classifier_code) as parent, cc.classifier_code from classifier_connect cc group by cc.classifier_code) parents on c.code = parents.classifier_code");

        qb.requiredCriteria("c.main_class_code in (:mainClassCodes)", "mainClassCodes", mainClassCodes);

        List<?> data = qb.select("c.code, c.name_et, c.name_en, c.name_ru, c.valid, c.is_higher, c.is_vocational"
                + ", c.main_class_code, c.value, array_to_string(parents.parent, ', '), c.valid_from, c.valid_thru"
                + ", c.extraval1, c.extraval2", em).getResultList();
        List<ClassifierSelection> result = StreamUtil.toMappedList(r -> {
            ClassifierSelection c = new ClassifierSelection(resultAsString(r, 0), resultAsString(r, 1),
                    resultAsString(r, 2), resultAsString(r, 3), resultAsBoolean(r, 4), resultAsBoolean(r, 5),
                    resultAsBoolean(r, 6), resultAsString(r, 7), resultAsString(r, 8), resultAsString(r, 9),
                    resultAsLocalDate(r, 10), resultAsLocalDate(r, 11), resultAsString(r, 12), resultAsString(r, 13));
            String parents = resultAsString(r, 9);
            if(parents != null) {
                c.setParents(Arrays.asList(parents.split(", ")));
            }
            return c;
        }, data);

        return ClassifierUtil.sort(mainClassCodes, result);
    }

    public List<Classifier> schoolCapacityTypes(Long schoolId, SchoolCapacityTypeCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school_capacity_type sct "
                + "join classifier c on sct.capacity_type_code = c.code");
        qb.requiredCriteria("sct.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sct.is_usable = :isUsable", "isUsable", Boolean.TRUE);
        qb.optionalCriteria("sct.is_higher = :isHigher", "isHigher", command.getIsHigher());
        qb.optionalCriteria("sct.is_timetable = :isTimetable", "isTimetable", command.getIsTimetable());
        if (command.getJournalId() != null && Boolean.TRUE.equals(command.getEntryTypes())) {
            qb.filter("c.code in (select jct.capacity_type_code from journal_capacity_type jct " +
                    "where jct.journal_id = " + command.getJournalId() + ")");
        }

        List<?> data = qb.select("c.code", em).getResultList();
        Set<String> typeCodes = StreamUtil.toMappedSet(r -> resultAsString(r, 0), data);

        if (command.getJournalId() != null) {
            if (Boolean.TRUE.equals(command.getEntryTypes())) {
                data = em.createNativeQuery("select ject.capacity_type_code from journal_entry_capacity_type ject "
                        + "join journal_entry je on je.id = ject.journal_entry_id where je.journal_id = ?1")
                        .setParameter(1, command.getJournalId())
                        .getResultList();
                typeCodes.addAll(StreamUtil.toMappedSet(r -> resultAsString(r, 0), data));
            } else {
                data = em.createNativeQuery("select jct.capacity_type_code from journal_capacity_type jct "
                        + "where jct.journal_id = ?1")
                        .setParameter(1, command.getJournalId())
                        .getResultList();
                typeCodes.addAll(StreamUtil.toMappedSet(r -> resultAsString(r, 0), data));
            }
        }

        List<Classifier> types = new ArrayList<>();
        if (!typeCodes.isEmpty()) {
            types = em.createQuery("select c from Classifier c where c.code in (:codes) order by c."
                    + (Language.EN.equals(command.getLang()) ? "nameEn" : "nameEt"), Classifier.class)
                    .setParameter("codes", typeCodes).getResultList();
        }
        return types;
    }

    public List<ClassifierDto> schoolCapacityTypeDtos(Long schoolId, SchoolCapacityTypeCommand command) {
        return StreamUtil.toMappedList(ClassifierDto::of, schoolCapacityTypes(schoolId, command));
    }

    public List<CurriculumResult> curriculums(Long schoolId, CurriculumAutocompleteCommand term, boolean setMaxResults) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum c "
                + "left join curriculum_speciality cs on cs.curriculum_id = c.id "
                + "left join classifier_connect clc on clc.classifier_code = c.orig_study_level_code")
                .groupBy("c.id");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("c.id = :curriculumId", "curriculumId", term.getId());
        qb.optionalContains(Language.EN.equals(term.getLang()) ? "concat(c.code, ' - ', c.name_en)"
                : "concat(c.code, ' - ', c.name_et)", "name", term.getName());
        qb.optionalCriteria("c.is_higher = :higher", "higher", term.getHigher());
        
        if (Boolean.TRUE.equals(term.getClosed())) {
            qb.requiredCriteria("c.status_code = :status", "status", CurriculumStatus.OPPEKAVA_STAATUS_C);
        } else if (Boolean.FALSE.equals(term.getClosed())) {
            qb.requiredCriteria("c.status_code != :status", "status", CurriculumStatus.OPPEKAVA_STAATUS_C);
        }

        qb.optionalCriteria("c.id in (select sg.curriculum_id from student_group sg where sg.teacher_id = :teacherId)",
                "teacherId", term.getTeacher());
        qb.optionalCriteria("clc.connect_classifier_code = :studyLevel", "studyLevel", term.getStudyLevel());

        qb.optionalCriteria("c.id in (select uc.curriculum_id from user_ u "
                + "join user_curriculum uc on uc.user_id = u.id where u.id = :userId)", "userId", term.getUserId());

        qb.sort(Language.EN.equals(term.getLang()) ? "c.name_en, c.code" : "c.name_et, c.code");
        List<?> data = qb.select("c.id, c.name_et, c.name_en, c.code, c.mer_code, string_agg(cs.id\\:\\:character varying, ';') as spec, c.orig_study_level_code", em)
                .setMaxResults(setMaxResults ? MAX_ITEM_COUNT : Integer.MAX_VALUE).getResultList();
        return data.stream().map(r -> {
            CurriculumResult dto = null;
            if (Boolean.TRUE.equals(term.getWithMerCode())) {
                dto = new CurriculumResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3), resultAsString(r, 4));
            } else {
                dto = new CurriculumResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3));
            }
            String specs = resultAsString(r, 5);
            if (specs != null) {
                dto.setSpecialities(Arrays.stream(specs.split(";")).map(Long::parseLong).collect(Collectors.toSet()));
            }
            dto.setOrigStudyLevel(resultAsString(r, 6));
            return dto;
        }).filter(res -> {
            if (term.getMinSpecialities() != null) {
                return res.getSpecialities() != null && res.getSpecialities().size() >= term.getMinSpecialities().intValue();
            }
            return true;
        }).collect(Collectors.toList());
    }

    public List<CurriculumVersionResult> curriculumVersions(Long schoolId, CurriculumVersionAutocompleteCommand lookup) {
        String from = "from curriculum_version cv inner join curriculum c on cv.curriculum_id = c.id " +
            "left join curriculum_version_speciality cvs on cvs.curriculum_version_id = cv.id " +
            "left outer join curriculum_study_form sf on cv.curriculum_study_form_id = sf.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).groupBy("cv.id, c.id, sf.id");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("c.id in (select uc.curriculum_id from user_ u join user_curriculum uc on uc.user_id = u.id where u.id = :userId)", "userId",
                lookup.getUserId());

        if(Boolean.TRUE.equals(lookup.getValid())) {
            // only valid ones
            qb.requiredCriteria("cv.status_code = :statusCode", "statusCode", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K);
            qb.requiredCriteria("c.valid_from <= :currentDate and (c.valid_thru is null or c.valid_thru >= :currentDate)", "currentDate", LocalDate.now());
        }
        
        qb.optionalCriteria("c.id = :curriculumId", "curriculumId", lookup.getCurriculumId());
        if (Boolean.TRUE.equals(lookup.getHasGroup())) {
            qb.requiredCriteria("exists (select cv.id from student_group sg where sg.curriculum_version_id = cv.id"
                    + " and (sg.valid_from is null or sg.valid_from <= :now)"
                    + " and (sg.valid_thru is null or sg.valid_thru >= :now)"
                    + ((Boolean.TRUE.equals(lookup.getCheckStudentGroupStudyForm()) && lookup.getStudyForm() != null) ? 
                            " and sg.study_form_code = '" + lookup.getStudyForm() + "')" : ")"), "now", LocalDate.now());
        } else if (Boolean.FALSE.equals(lookup.getHasGroup())) {
            qb.requiredCriteria("not exists (select cv.id from student_group sg where sg.curriculum_version_id = cv.id"
                    + " and (sg.valid_from is null or sg.valid_from <= :now)"
                    + " and (sg.valid_thru is null or sg.valid_thru >= :now)"
                    + ((Boolean.TRUE.equals(lookup.getCheckStudentGroupStudyForm()) && lookup.getStudyForm() != null) ? 
                            " and sg.study_form_code = '" + lookup.getStudyForm() + "')" : ")"), "now", LocalDate.now());
        }

        if (lookup.getCheckStudentGroupStudyForm() == null || Boolean.FALSE.equals(lookup.getCheckStudentGroupStudyForm())) {
            qb.optionalCriteria("sf.study_form_code = :form", "form", lookup.getStudyForm());
        }
       
        if(Boolean.TRUE.equals(lookup.getClosed())) {
            qb.requiredCriteria("cv.status_code = :statusCode", "statusCode", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C);
        } else if (Boolean.FALSE.equals(lookup.getClosed())) {
            qb.requiredCriteria("cv.status_code != :statusCode", "statusCode", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C);
        }
        
        if(Boolean.TRUE.equals(lookup.getSais())) {
            qb.filter("exists(select sa.id from sais_admission sa where sa.curriculum_version_id = cv.id and (sa.is_archived is null or sa.is_archived = false))");
        }
        qb.optionalCriteria("cv.id = :curriculumVersionId", "curriculumVersionId", lookup.getId());
        qb.optionalCriteria("c.is_higher = :higher", "higher", lookup.getHigher());
        qb.optionalContains(Arrays.asList("cv.code", "cv.code || ' ' || c.name_et", "cv.code || ' ' || c.name_en"), "name", lookup.getName());

        qb.sort(Language.EN.equals(lookup.getLang()) ? "cv.code, c.name_et" : "cv.code, c.name_en");
        List<?> data = qb.select("cv.id, cv.code, c.name_et, c.name_en, c.id as curriculum_id, cv.school_department_id, sf.study_form_code, c.is_higher, string_agg(cvs.curriculum_speciality_id\\:\\:character varying, ';') as spec",
                em).getResultList();
        List<CurriculumVersionResult> result = StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            CurriculumVersionResult dto = new CurriculumVersionResult(resultAsLong(r, 0), CurriculumUtil.versionName(code, resultAsString(r, 2)),
                    CurriculumUtil.versionName(code, resultAsString(r, 3)), resultAsLong(r, 4), resultAsLong(r, 5), resultAsString(r, 6), Boolean.valueOf(!Boolean.TRUE.equals(resultAsBoolean(r, 7))));
            String specs = resultAsString(r, 8);
            if (specs != null) {
                dto.setSpecialities(Arrays.stream(specs.split(";")).map(Long::parseLong).collect(Collectors.toSet()));
            }
            return dto;
        }, data);

        if(Boolean.TRUE.equals(lookup.getLanguages())) {
            // attach study languages
            Set<Long> curriculumIds = result.stream().map(CurriculumVersionResult::getCurriculum).collect(Collectors.toSet());
            if(!curriculumIds.isEmpty()) {
                data = em.createNativeQuery("select csl.curriculum_id, csl.study_lang_code from curriculum_study_lang csl where csl.curriculum_id in ?1")
                    .setParameter(1, curriculumIds).getResultList();
                Map<Long, List<String>> curriculumLanguages = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
                for(CurriculumVersionResult r : result) {
                    r.setStudyLang(curriculumLanguages.get(r.getCurriculum()));
                }
            }
        }
        return result;
    }
    
    public List<CurriculumVersionHigherModuleResult> curriculumVersionHigherModules(HoisUserDetails user,
            CurriculumVersionHigherModuleAutocompleteCommand lookup) {
        String from = "from curriculum_version_hmodule cvh "
                + "inner join curriculum_version cv on cvh.curriculum_version_id = cv.id "
                + "inner join curriculum c on c.id = cv.curriculum_id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("cvh.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                lookup.getCurriculumVersion());
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains("cv.status_code",  "statusCode", lookup.getCurriculumVersionStatusCode());

        List<?> data = qb.select("cvh.id, cvh.name_et, cvh.name_en, cvh.type_code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new CurriculumVersionHigherModuleResult(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsString(r, 2), resultAsString(r, 3));
        }, data);
    }
  
    public List<CurriculumVersionOccupationModuleResult> curriculumVersionOccupationModules(HoisUserDetails user,
            CurriculumVersionOccupationModuleAutocompleteCommand lookup) {
        String from = "from curriculum_version_omodule cvo"
                + " inner join curriculum_module cm on cvo.curriculum_module_id = cm.id"
                + " inner join curriculum_version cv on cvo.curriculum_version_id = cv.id"
                + " inner join curriculum c on cv.curriculum_id = c.id";

        boolean otherStudents = Boolean.TRUE.equals(lookup.getOtherStudents());
        if (otherStudents) {
            from += " inner join student_vocational_result svr on svr.curriculum_version_omodule_id = cvo.id";
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.optionalCriteria("cvo.id = :id", "id", lookup.getId());
        
        if (Boolean.FALSE.equals(lookup.getCurriculumModules())) {
            qb.requiredCriteria("cvo.curriculum_version_id != :curriculumVersionId", "curriculumVersionId",
                    lookup.getCurriculumVersion());
        } else if (lookup.getCurriculumVersion() != null){
            qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                    lookup.getCurriculumVersion());
        }
        qb.requiredCriteria("c.school_id = :schoolId",  "schoolId", user.getSchoolId());
        qb.optionalContains(Language.EN.equals(lookup.getLang()) ? "cm.name_en" : "cm.name_et", "name", lookup.getName());
        
        if (!Boolean.TRUE.equals(lookup.getIgnoreStatuses())) {
            if (Boolean.TRUE.equals(lookup.getClosedCurriculumVersionModules())) {
                qb.optionalCriteria("cv.status_code = :statusCode", "statusCode",
                        CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
            } else {
                qb.optionalCriteria("cv.status_code != :statusCode", "statusCode",
                        CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
            }
        }
        
        if (lookup.getStudent() != null) {
            if (otherStudents) {
                qb.requiredCriteria("svr.grade_code in (:posVocGrades)", "posVocGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
                qb.requiredCriteria("svr.student_id in (select s2.id from student s"
                        + " join student s2 on s.person_id=s2.person_id and s.id!=s2.id "
                        + "where s2.id!=:studentId and s.id = :studentId)", "studentId", lookup.getStudent());
            }
            qb.requiredCriteria(
                    "cvo.id not in (select svr2.curriculum_version_omodule_id from student_vocational_result svr2 "
                            + "where svr2.student_id = :studentId and svr2.curriculum_version_omodule_id is not null",
                    "studentId", lookup.getStudent());
            qb.requiredCriteria("svr2.grade_code in (:posVocGrades2))", "posVocGrades2",
                    OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
            qb.requiredCriteria(
                    "(select count(*) from student_vocational_result svr2 where svr2.student_id = :studentId and cvo.id=any(svr2.arr_modules)",
                    "studentId", lookup.getStudent());
            qb.requiredCriteria("svr2.grade_code in (:posVocGrades3)) = 0", "posVocGrades3",
                    OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        }
        
        qb.sort(Language.EN.equals(lookup.getLang()) ? "cm.name_en" : "cm.name_et");
        
        String query;
        if (otherStudents) {
            query = "distinct cvo.id, cm.name_et, cm.name_en, cm.credits, cvo.assessment_code, svr.grade_code, svr.grade_date, svr.teachers";
        } else {
            query = "distinct cvo.id, cm.name_et, cm.name_en, cm.credits, cvo.assessment_code, null as grade_code, null as grade_date, null as teachers";
        }
        
        List<?> data = qb.select(query, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new CurriculumVersionOccupationModuleResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), 
                    resultAsDecimal(r, 3), resultAsString(r, 4), resultAsString(r, 5), resultAsLocalDate(r, 6), resultAsString(r, 7));
        }, data);
    }

    public List<CurriculumVersionOccupationModuleThemeResult> curriculumVersionOccupationModuleThemes(
            CurriculumVersionOccupationModuleThemeAutocompleteCommand lookup) {
        String from = "from curriculum_version_omodule_theme cvot"
                + " join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id"
                + " join curriculum_version cv on cvo.curriculum_version_id = cv.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        if (lookup.getCurriculumVersionOmoduleId() != null) {
            qb.requiredCriteria("cvot.curriculum_version_omodule_id = :omoduleId", "omoduleId",
                    lookup.getCurriculumVersionOmoduleId());
        } else {
            qb.requiredCriteria("cvot.curriculum_version_omodule_id in (:omoduleIds)", "omoduleIds",
                    lookup.getCurriculumVersionOmoduleIds());
        }

        if (Boolean.TRUE.equals(lookup.getClosedCurriculumVersionModules())) {
            qb.optionalCriteria("cv.status_code = :statusCode", "statusCode",
                    CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
        } else {
            qb.optionalCriteria("cv.status_code != :statusCode", "statusCode",
                    CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
        }

        List<?> data = qb.select("cvot.id, cvot.name_et, cvot.credits, cvot.study_year_number, cvo.id module_id", em)
                .getResultList();

        List<CurriculumVersionOccupationModuleThemeResult> results = StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            Short studyYearNumber = resultAsShort(r, 3);
            if (Boolean.TRUE.equals(lookup.getAddStudyYearToName()) && studyYearNumber != null) {
                name += " (" + studyYearNumber + ". õa)";
            }
            return new CurriculumVersionOccupationModuleThemeResult(resultAsLong(r, 0), name, name,
                    resultAsDecimal(r, 2), studyYearNumber, resultAsLong(r, 4));
        }, data);

        if (Boolean.TRUE.equals(lookup.getExistInOtherJournals()) && lookup.getStudentGroupId() != null) {
            Map<Long, CurriculumVersionOccupationModuleThemeResult> themes = StreamUtil.toMap(r -> r.getId(), r -> r,
                    results);
            setThemesInOtherJournals(themes, lookup.getStudentGroupId(), lookup.getJournalId());
        }
        return results;
    }

    public void setThemesInOtherJournals(Map<Long, CurriculumVersionOccupationModuleThemeResult> themes,
            Long studentGroupId, Long journalId) {
        if (!themes.isEmpty()) {
            Set<Long> themesInOtherJournals = themesInOtherJournals(themes.keySet(), studentGroupId, journalId);
            for (CurriculumVersionOccupationModuleThemeResult theme : themes.values()) {
                theme.setExistsInOtherJournals(Boolean.valueOf(themesInOtherJournals.contains(theme.getId())));
            }
        }
    }

    private Set<Long> themesInOtherJournals(Set<Long> themeIds, Long studentGroupId, Long journalId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_omodule_theme cvot");
        qb.requiredCriteria("cvot.id in (:themeIds)", "themeIds", themeIds);

        String filter = "exists (select j.id from journal j"
                + " join journal_omodule_theme jot on j.id = jot.journal_id"
                + " join curriculum_version_omodule_theme cvot2 on jot.curriculum_version_omodule_theme_id = cvot.id"
                + " join lesson_plan_module lpm on cvot2.curriculum_version_omodule_id = lpm.curriculum_version_omodule_id"
                + " join lesson_plan lp on lpm.lesson_plan_id = lp.id"
                + " where lp.student_group_id = " + studentGroupId + (journalId != null ? " and j.id != " + journalId : "") + ")";
        qb.filter(filter);

        List<?> data = qb.select("cvot.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    public List<CurriculumVersionOModulesAndThemesResult> curriculumVersionOccupationModulesAndThemes(
            HoisUserDetails user, CurriculumVersionOccupationModuleAutocompleteCommand lookup) {
        List<CurriculumVersionOModulesAndThemesResult> modulesAndThemes = new ArrayList<>();
        List<CurriculumVersionOccupationModuleResult> modules = curriculumVersionOccupationModules(user, lookup);

        if (!modules.isEmpty()) {
            CurriculumVersionOccupationModuleThemeAutocompleteCommand themeLookup = new CurriculumVersionOccupationModuleThemeAutocompleteCommand();
            themeLookup.setCurriculumVersionOmoduleIds(StreamUtil.toMappedList(m -> m.getId(), modules));
            themeLookup.setClosedCurriculumVersionModules(lookup.getClosedCurriculumVersionModules());
            List<CurriculumVersionOccupationModuleThemeResult> themes = curriculumVersionOccupationModuleThemes(
                    themeLookup);
            Map<Long, List<CurriculumVersionOccupationModuleThemeResult>> themesByModule = StreamUtil.nullSafeList(themes)
                    .stream().collect(Collectors.groupingBy(t -> t.getModuleId()));
            
            for (CurriculumVersionOccupationModuleResult module : modules) {
                List<CurriculumVersionOccupationModuleThemeResult> moduleThemes = themesByModule.get(module.getId());
                modulesAndThemes.add(new CurriculumVersionOModulesAndThemesResult(module.getId(), module.getNameEt(),
                        module.getNameEn(), module.getCredits(), module.getAssessment(), module.getGradeCode(),
                        module.getGradeDate(), module.getTeachers(),
                        moduleThemes != null ? moduleThemes : new ArrayList<>()));
            }
        }
        return modulesAndThemes;
    }

    public List<AutocompleteResult> directiveCoordinators(Long schoolId, DirectiveCoordinatorAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_coordinator dc");

        qb.requiredCriteria("dc.school_id = :schoolId", "schoolId", schoolId);
        if(Boolean.TRUE.equals(lookup.getIsDirective())) {
            qb.filter("dc.is_directive = true");
        }
        if(Boolean.TRUE.equals(lookup.getIsCertificate())) {
            qb.filter("dc.is_certificate = true");
        }

        List<?> data = qb.select("dc.id, dc.name", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    public PersonDto person(HoisUserDetails user, PersonLookupCommand lookup) {
        Person person;
        if("foreignidcode".equals(lookup.getRole())) {
            if(!StringUtils.hasText(lookup.getForeignIdcode())) {
                throw new ValidationFailedException("foreignIdcode", Required.MESSAGE);
            }
            List<Person> data = em.createQuery("select p from Person p where p.foreignIdcode = ?1", Person.class)
                    .setParameter(1, lookup.getForeignIdcode())
                    .setMaxResults(1).getResultList();
            person = data.isEmpty() ? null : data.get(0);
        } else {
            if(!StringUtils.hasText(lookup.getIdcode())) {
                throw new ValidationFailedException("idcode", Required.MESSAGE);
            }
            String idcode = lookup.getIdcode().trim();
            if("student".equals(lookup.getRole())) {
                // FIXME multiple students with same idcode?
                // FIXME should filter by school?
                List<Person> data = em.createQuery("select s.person from Student s where s.person.idcode = ?1", Person.class)
                        .setParameter(1, idcode)
                        .setMaxResults(1).getResultList();
                person = data.isEmpty() ? null : data.get(0);
            } else if("activestudent".equals(lookup.getRole())) {
                List<Person> data = em.createQuery("select s.person from Student s where s.person.idcode = ?1 and s.status.code in ?2", Person.class)
                        .setParameter(1, idcode)
                        .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                        .setMaxResults(1).getResultList();
                person = data.isEmpty() ? null : data.get(0);
            } else {
               person = personRepository.findByIdcode(idcode);
            }
        }
        PersonDto dto = null;
        if(person != null) {
            dto = PersonDto.of(person);
            if("forteacher".equals(lookup.getRole())) {
                if(user.isSchoolAdmin()) {
                    List<?> teacher = em.createNativeQuery("select id from teacher where school_id = ?1 and person_id = ?2")
                            .setParameter(1, user.getSchoolId())
                            .setParameter(2, person.getId())
                            .setMaxResults(1).getResultList();
                    dto.setTeacherId(teacher.isEmpty() ? null : resultAsLong(teacher.get(0), 0));
                    dto.setSchoolEmail(emailGeneratorService.lookupSchoolEmail(em.getReference(School.class, user.getSchoolId()), person));
                }
            }
        }
        return dto;
    }

    public List<SchoolWithoutLogo> schools(SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school s");

        qb.optionalCriteria("s.id = :schoolId", "schoolId", lookup.getId());

        List<?> data = qb.select("s.id, s.code, s.name_et, s.name_en, s.email", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new SchoolWithoutLogo(
                    resultAsLong(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3),
                    resultAsString(r, 4));
        }, data);
    }

    public List<SchoolWithoutLogo> schoolsWithType(SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school s");

        qb.optionalCriteria("s.id = :schoolId", "schoolId", lookup.getId());

        List<?> data = qb.select("s.id, s.code, s.name_et, s.name_en, s.email", em).getResultList();
        
        return StreamUtil.toMappedList(r -> {
            SchoolWithoutLogo dto = new SchoolWithoutLogo(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4));
            SchoolType type = schoolService.schoolType(dto.getId());
            dto.setVocational(Boolean.valueOf(type.isVocational()));
            dto.setHigher(Boolean.valueOf(type.isHigher()));
            dto.setDoctoral(Boolean.valueOf(type.isDoctoral()));
            return dto;
        }, data);
    }
    
    public List<SchoolWithLogo> schoolsWithLogo(SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school s");

        qb.optionalCriteria("s.id = :schoolId", "schoolId", lookup.getId());

        List<?> data = qb.select("s.id, s.code, s.name_et, s.name_en, s.email, s.ois_file_id", em).getResultList();
        List<SchoolWithLogo> schoolsWithLogo = StreamUtil.toMappedList(r -> {
            return new SchoolWithLogo(
                    resultAsLong(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3),
                    resultAsString(r, 4),
                    resultAsLong(r, 5));
        }, data);

        List<Long> logoIds = schoolsWithLogo.stream().filter(r -> r.getOisFileId() != null).map(r -> r.getOisFileId()).collect(Collectors.toList());
        Map<Long, OisFile> logos = new HashMap<>();
        if (!logoIds.isEmpty()) {
            logos = em.createQuery("select file from OisFile as file where file.id in (?1)", OisFile.class)
                    .setParameter(1, logoIds)
                    .getResultList()
                    .stream().collect(Collectors.toMap(r -> EntityUtil.getId(r), r -> r, (o, n) -> o));
        }

        for (SchoolWithLogo school : schoolsWithLogo) {
            OisFile logo = school.getOisFileId() != null ? logos.get(school.getOisFileId()) : null;
            school.setLogo(logo != null ? logo.getFdata() : null);
        }
        return schoolsWithLogo;
    }

    public List<SchoolWithoutLogo> ldapSchools() {
        List<?> data = em.createNativeQuery("select s.id, s.code, s.name_et, s.name_en, s.email" +
                " from school s where s.ad_domain is not null and s.ad_base is not null" + 
                " and s.ad_port is not null and s.ad_url is not null and s.ad_idcode_field is not null")
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            return new SchoolWithoutLogo(
                    resultAsLong(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3),
                    resultAsString(r, 4));
        }, data);
    }
    
    public List<ApelSchoolResult> apelSchools(Long schoolId, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from apel_school s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        
        qb.sort(Language.EN.equals(lookup.getLang()) ? "s.name_en" : "s.name_et");
        List<?> data = qb.select("s.id, s.name_et, s.name_en, s.ehis_school_code, s.country_code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new ApelSchoolResult(
                    resultAsLong(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3),
                    resultAsString(r, 4));
        }, data);
    }

    /**
     * Values for selecting department.
     * @param schoolId
     * @return
     */
    public List<SchoolDepartmentResult> schoolDepartments(Long schoolId, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school_department sd inner join school s on s.id = sd.school_id");
        // optional for teacher's view form, when user is external expert
        qb.optionalCriteria("sd.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("sd.name_et", "departmentEt", lookup.getName());

        List<?> data = qb.select(
                "sd.id, sd.name_et, sd.name_en, sd.school_id, s.code, sd.valid_from, sd.valid_thru, "
                + "(sd.valid_from is null or sd.valid_from <= date(now())) and (sd.valid_thru is null or sd.valid_thru >= date(now())) as valid",
                em).getResultList();
        return StreamUtil.toMappedList(r -> {
            SchoolDepartmentResult dto = new SchoolDepartmentResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsLong(r, 3), resultAsString(r, 4));
            dto.setValidFrom(resultAsLocalDate(r, 5));
            dto.setValidThru(resultAsLocalDate(r, 6));
            dto.setValid(resultAsBoolean(r, 7));
            return dto;
        }, data);
    }
    
    public List<SchoolDepartmentResult> schoolDepartments(Long schoolId) {
        return schoolDepartments(schoolId, new SearchCommand());
    }
    
    /**
     * Values for selecting department.
     * @param schoolId
     * @return
     */
    public List<SchoolDepartmentResult> curriculumDepartments(Long schoolId, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_department cd "
                + "join school_department sd on sd.id = cd.school_department_id "
                + "join school s on s.id = sd.school_id");
        qb.requiredCriteria("sd.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("sd.name_et", "departmentEt", lookup.getName());
        qb.optionalCriteria("cd.curriculum_id = :curriculumId", "curriculumId", lookup.getId());

        List<?> data = qb.select(
                "distinct sd.id, sd.name_et, sd.name_en, sd.school_id, s.code, sd.valid_from, sd.valid_thru, "
                + "(sd.valid_from is null or sd.valid_from <= date(now())) and (sd.valid_thru is null or sd.valid_thru >= date(now())) as valid",
                em).getResultList();
        return StreamUtil.toMappedList(r -> {
            SchoolDepartmentResult dto = new SchoolDepartmentResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsLong(r, 3), resultAsString(r, 4));
            dto.setValidFrom(resultAsLocalDate(r, 5));
            dto.setValidThru(resultAsLocalDate(r, 6));
            dto.setValid(resultAsBoolean(r, 7));
            return dto;
        }, data);
    }

    public List<StudentGroupResult> studentGroups(Long schoolId, StudentGroupAutocompleteCommand lookup, boolean forExpert) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student_group sg "
                + "left join curriculum c on sg.curriculum_id = c.id " 
                + (lookup.getStudyYear() != null ? "join study_year sy on sy.id = :studyYearId " : "") 
                + "left join curriculum_version cv on sg.curriculum_version_id = cv.id");
        if (!forExpert) qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("c.id = :curriculumId", "curriculumId", lookup.getCurriculumId());
        qb.optionalCriteria("cv.id = :cVersionId", "cVersionId", lookup.getCurriculumVersionId());
        qb.optionalCriteria("cv.id in (:cVersionIds)", "cVersionIds", lookup.getCurriculumVersionIds());
        qb.optionalCriteria("sg.id = :studentGroupId", "studentGroupId", lookup.getId());
        if (Boolean.TRUE.equals(lookup.getIsGuest())) qb.filter("sg.is_guest = true");
        if (Boolean.FALSE.equals(lookup.getIsGuest())) qb.filter("(sg.is_guest is null or is_guest = false)");
        qb.optionalCriteria("sg.teacher_id = :studentGroupTeacherId", "studentGroupTeacherId",
                lookup.getStudentGroupTeacherId());
        qb.optionalCriteria("c.id in (select uc.curriculum_id from user_ u join user_curriculum uc on uc.user_id = u.id where u.id = :userId)", "userId",
                lookup.getUserId());

        if (lookup.getStudyYear() != null) {
            qb.filter("((sg.valid_from is null or sg.valid_from <= sy.end_date) AND (sg.valid_thru is null or sg.valid_thru >= sy.start_date))");
            qb.parameter("studyYearId", lookup.getStudyYear());
        }
        qb.optionalCriteria("sg.study_form_code = :form", "form", lookup.getStudyForm());

        if(Boolean.TRUE.equals(lookup.getValid())) {
            qb.requiredCriteria("(sg.valid_from is null or sg.valid_from <= :now) and (sg.valid_thru is null or sg.valid_thru >= :now)", "now", LocalDate.now());
        }
        qb.optionalCriteria("(case when sg.curriculum_id is not null then c.is_higher = :higher else true end)", "higher", lookup.getHigher());
        qb.optionalContains("sg.code",  "code", lookup.getName());
        qb.sort("sg.code");

        List<?> data = qb.select("sg.id, sg.code, c.id as c_id, cv.id as cv_id, sg.study_form_code, sg.language_code, sg.valid_from, sg.valid_thru", em).getResultList();
        
        Map<Long, StudentGroupResult> studentGroupsResult = new LinkedHashMap<>();
        if (!data.isEmpty()) {
            studentGroupsResult = data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
                StudentGroupResult dto = new StudentGroupResult(resultAsLong(r, 0), resultAsString(r, 1));
                dto.setCurriculum(resultAsLong(r, 2));
                dto.setCurriculumVersion(resultAsLong(r, 3));
                dto.setStudyForm(resultAsString(r, 4));
                dto.setLanguage(resultAsString(r, 5));
                dto.setValidFrom(resultAsLocalDate(r, 6));
                dto.setValidThru(resultAsLocalDate(r, 7));
                return dto;
            }, (v1, v2) -> v1, LinkedHashMap::new));
        }

        if (Boolean.TRUE.equals(lookup.getOccupied()) && !studentGroupsResult.isEmpty()) {
            if (lookup.getDate() != null && lookup.getStartTime() != null && lookup.getEndTime() != null) {
                setStudentGroupsOccupationStatus(lookup, studentGroupsResult);
            }
        }
        return new ArrayList<>(studentGroupsResult.values());
    }

    private void setStudentGroupsOccupationStatus(StudentGroupAutocompleteCommand lookup, Map<Long, StudentGroupResult> studentGroupsResult) {
        List<Long> studentGroupIds = studentGroupsResult.values().stream().map(t -> t.getId()).collect(Collectors.toList());
        
        List<LocalDateTime> starts = new ArrayList<>();
        List<LocalDateTime> ends = new ArrayList<>();
        starts.add(LocalDateTime.of(lookup.getDate(), 
                LocalTime.of(lookup.getStartTime().getHour(), lookup.getStartTime().getMinute())));
        ends.add(LocalDateTime.of(lookup.getDate(), 
                LocalTime.of(lookup.getEndTime().getHour(), lookup.getEndTime().getMinute())));
        if (lookup.getWeekAmount() != null) {
            eventRepeatStartAndEndTimes(lookup.getRepeatCode(), lookup.getWeekAmount(), starts, ends);
        }
        
        JpaNativeQueryBuilder occupiedQb = new JpaNativeQueryBuilder("from timetable_event te "
                + "join timetable_event_time tem on te.id = tem.timetable_event_id "
                + "join timetable_event_student_group tesg on tem.id = tesg.timetable_event_time_id");
        occupiedQb.requiredCriteria("tesg.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        occupiedQb.filter(getTimeFilter(starts, ends));
        
        List<?> occupiedRooms = occupiedQb.select("tesg.student_group_id", em).getResultList();
        if (!occupiedRooms.isEmpty()) {
            List<Long> occupiedRoomIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), occupiedRooms); 
            for (Long id : occupiedRoomIds) {
                studentGroupsResult.get(id).setIsOccupied(Boolean.TRUE);
            }
        }
    }

    private List<?> studentResults(Long schoolId, StudentAutocompleteCommand lookup) {
        String from = "from student s inner join person p on s.person_id = p.id";
        if (Boolean.TRUE.equals(lookup.getShowStudentGroup())) {
            from += " left join student_group sg on s.student_group_id = sg.id";
        }
        if (Boolean.TRUE.equals(lookup.getShowGuestStudent())) {
            from += " left join (select s.study_start, ds.student_id "
                        + "from directive_student ds "
                        + "join student s on ds.student_id = s.id "
                        + "join directive d on d.id = ds.directive_id "
                        + "where d.type_code = 'KASKKIRI_KYLALIS' and ds.start_date < date(now()) and ds.canceled = false) "
                        + "DS1 on DS1.student_id = s.id";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).sort("p.lastname", "p.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname", "concat(p.firstname, ' ', p.lastname, ' (', p.idcode, ')')"), "name", lookup.getName());
        qb.optionalCriteria("s.id = :studentId", "studentId", lookup.getId());
        
        if (Boolean.FALSE.equals(lookup.getShowGuestStudent())) {
            qb.requiredCriteria("s.type_code != :typeCode", "typeCode", StudentType.OPPUR_K.name());
        }
        
        if (Boolean.TRUE.equals(lookup.getOnlyStudyingOrFinishedGuestStudent())) {
            qb.requiredCriteria("case when s.type_code = 'OPPUR_K' then s.status_code in :statusCodes else true end",
                    "statusCodes", EnumUtil.toNameList(StudentStatus.OPPURSTAATUS_O, StudentStatus.OPPURSTAATUS_L));
        }

        if (Boolean.TRUE.equals(lookup.getActive())) {
            qb.requiredCriteria("s.status_code in :statusCodes", "statusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        }

        StudentStatus status = null;
        if (Boolean.TRUE.equals(lookup.getFinished())) {
            status = StudentStatus.OPPURSTAATUS_L;
        } else if (Boolean.TRUE.equals(lookup.getStudying())) {
            status = StudentStatus.OPPURSTAATUS_O;
        } else if (Boolean.TRUE.equals(lookup.getAcademicLeave())) {
            status = StudentStatus.OPPURSTAATUS_A;
        }
        qb.optionalCriteria("s.status_code = :statusCode", "statusCode", status);
        
        if (Boolean.TRUE.equals(lookup.getHideGuestStudents())) {
            qb.requiredCriteria("s.type_code != :studentType", "studentType", StudentType.OPPUR_K.name());
        }

        if (Boolean.TRUE.equals(lookup.getNominalStudy())) {
            qb.requiredCriteria("s.nominal_study_end > :currentDate", "currentDate", LocalDate.now());
        }
        if (Boolean.TRUE.equals(lookup.getHigher())) {
            qb.filter("(exists (select c.id from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id "
                    + "where cv.id = s.curriculum_version_id and c.is_higher = true ) "
                    // check is higher from directive if curriculum version is missing from student
                    + "or (case when s.curriculum_version_id is null then "
                    // h at the end means higher and is there to make this directive naming differ from other directives in this query
                    + "exists (select dh.id from directive dh join directive_student dsh on dsh.directive_id = dh.id "
                    + "where dh.is_higher = true and dh.type_code = 'KASKKIRI_KYLALIS' and dh.school_id = s.school_id and s.type_code = 'OPPUR_K'"
                    + ") else false end))");
        }
        if (Boolean.FALSE.equals(lookup.getHigher())) {
            qb.filter("exists (select c.id from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id "
                    + "where cv.id = s.curriculum_version_id "
                    + "and c.is_higher = false )");
        }
        if (Boolean.TRUE.equals(lookup.getFinishing())) {
            qb.requiredCriteria("exists (select 1 from directive_student ds"
                    + " join directive d on d.id = ds.directive_id"
                    + " where ds.student_id = s.id and ds.canceled = false"
                    + " and d.type_code = '" + DirectiveType.KASKKIRI_LOPET.name() + "'"
                    + " and d.inserted >= :from)", "from", DocumentService.DIRECTIVE_INSERTED_FROM);
        }

        qb.optionalCriteria("s.curriculum_version_id in (:curriculumVersion)", "curriculumVersion", lookup.getCurriculumVersion());
        qb.optionalCriteria("s.student_group_id in (:studentGroup)", "studentGroup", lookup.getStudentGroup());

        if (lookup.getStudentGroupTeacher() != null) {
            qb.filter("s.student_group_id in (select sg2.id from student_group sg2 where sg2.teacher_id = :studentGroupTeacher)");
            qb.parameter("studentGroupTeacher", lookup.getStudentGroupTeacher());
        }

        qb.optionalCriteria("exists (select c2.id from curriculum_version cv2 "
                + "join curriculum c2 on c2.id = cv2.curriculum_id "
                + "join user_curriculum uc on uc.curriculum_id = c2.id "
                + "join user_ u on u.id = uc.user_id where cv2.id = s.curriculum_version_id "
                + "and u.id = :userId)", "userId", lookup.getUserId());

        String select = "s.id, p.firstname, p.lastname, p.idcode"
                + (Boolean.TRUE.equals(lookup.getShowStudentGroup()) ? ", sg.code" : ", null") + " sg_code"
                + (Boolean.TRUE.equals(lookup.getShowGuestStudent()) ? ", s.type_code studentType, DS1.study_start as guestStart" : ", null studentType, null as guestStart");
        return qb.select(select, em).setMaxResults(MAX_ITEM_COUNT).getResultList();
    }
    
    public List<AutocompleteResult> students(Long schoolId, StudentAutocompleteCommand lookup) {
        List<?> data = studentResults(schoolId, lookup);
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullnameAndIdcodeOptionalGuest(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 5));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }
    
    public List<AutocompleteResult> studentsForCertificate(Long schoolId, StudentAutocompleteCommand lookup) {
        List<?> data = studentResults(schoolId, lookup);
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullnameAndIdcodeOptionalGuestForCertificate(resultAsString(r, 1), resultAsString(r, 2)
                    , resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5), JpaQueryUtil.resultAsLocalDate(r, 6));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    public List<AutocompleteResult> stipendAvailableStudents(HoisUserDetails user, ScholarshipTerm term,
            SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on s.person_id = p.id "
                + "join student_group sg on sg.id = s.student_group_id")
                .sort("p.lastname", "p.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(term.getSchool()));
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname",
                "concat(p.firstname, ' ', p.lastname, ' (', p.idcode, ')')"), "name", lookup.getName());
        qb.requiredCriteria("s.status_code in :statusCodes", "statusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("s.type_code != :studentType", "studentType", StudentType.OPPUR_K.name());
        qb.requiredCriteria("not exists (select 1 from scholarship_application sa where sa.student_id = s.id "
                        + "and sa.scholarship_term_id = :termId)", "termId", term.getId());

        String termType = EntityUtil.getCode(term.getType());
        if (ScholarshipType.GRANTS.contains(termType)) {
            qb.filter("exists (select c.id from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id "
                    + "where cv.id = s.curriculum_version_id "
                    + "and c.is_higher = false)");
        } else {
            qb.filter("exists (select c.id from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id "
                    + "where cv.id = s.curriculum_version_id "
                    + "and c.is_higher = true)");
        }
        if (user.isTeacher()) {
            qb.requiredCriteria("s.student_group_id in (select sg2.id from student_group sg2 where sg2.teacher_id = :teacherId)",
                    "teacherId", user.getTeacherId());
        }

        String select = "s.id, p.firstname, p.lastname, sg.code";
        List<?> data = qb.select(select, em).setMaxResults(AutocompleteService.MAX_ITEM_COUNT).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)) + " - " + resultAsString(r, 3);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    /**
     * SubjectService.search() is not used as it does not enable to search by both code and name using autocomplete
     */
    public List<SubjectResult> subjects(Long schoolId, SubjectAutocompleteCommand lookup) {
        String from ="from subject s"
                + " left join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = s.id"
                + " left join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id"
                + " left join curriculum_version cv on cv.id = cvh.curriculum_version_id";

        boolean otherStudents = Boolean.TRUE.equals(lookup.getOtherStudents());
        if (otherStudents) {
            from += " inner join student_higher_result shr on s.id = shr.subject_id";
        }
        

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        if (!Boolean.TRUE.equals(lookup.getIgnoreCurriculumVersionStatus())) {
            qb.optionalCriteria("coalesce(cvh.type_code, 'x') != :cvStatusCode", "cvStatusCode",
                    CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
            if (Boolean.TRUE.equals(lookup.getNoFinalSubjects())) {
                qb.optionalCriteria("cvh.type_code not in (:finalTypes)", "finalTypes", HigherModuleType.FINAL_MODULES);
            }
        } else {
            if (Boolean.TRUE.equals(lookup.getNoFinalSubjects())) {
                qb.optionalCriteria("coalesce(cvh.type_code, 'x') not in (:finalTypes)", "finalTypes",
                        HigherModuleType.FINAL_MODULES);
            }
        }

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("exists (select uc.curriculum_id from user_curriculum uc"
                + " where uc.curriculum_id = cv.curriculum_id and uc.user_id = :userId)", "userId", lookup.getUserId());
        
        if (lookup.getIsComplete() != null) {
            if (lookup.getIsComplete().booleanValue()) {
                qb.requiredCriteria("s.status_code != :statusCheck", "statusCheck", SubjectStatus.AINESTAATUS_S);
            } else {
                qb.requiredCriteria("s.status_code = :statusCheck", "statusCheck", SubjectStatus.AINESTAATUS_S);
            }
        } else if (!otherStudents) {
            qb.requiredCriteria("s.status_code = :subjectStatusCode", "subjectStatusCode", SubjectStatus.AINESTAATUS_K);
        }
        
        String name = Language.EN.equals(lookup.getLang()) ? "s.name_en" : "s.name_et";
        qb.optionalContains(Arrays.asList(name, "s.code"), "name", lookup.getName());
        // Never used at this moment
        qb.optionalContains("s.code", "code", lookup.getCode());
        qb.optionalCriteria("is_practice = :isPractice", "isPractice", lookup.getPractice());

        if (lookup.getCurriculumSubjects() != null && lookup.getStudent() != null) {
            Student student = em.getReference(Student.class, lookup.getStudent());
            Long curriculumVersionId = EntityUtil.getId(student.getCurriculumVersion());
            Long curriculumSpecialityId = EntityUtil.getNullableId(student.getCurriculumSpeciality());

            if (Boolean.TRUE.equals(lookup.getCurriculumSubjects())) {
                qb.optionalCriteria("cv.id = :curriculumVersionId", "curriculumVersionId", curriculumVersionId);
                qb.optionalCriteria(":curriculumSpecialityId in (select cs.id from curriculum_version_hmodule_speciality cvhs "
                        + "join curriculum_version_speciality cvs on cvs.id = cvhs.curriculum_version_speciality_id "
                        + "join curriculum_speciality cs on cs.id = cvs.curriculum_speciality_id "
                        + "where cvhs.curriculum_version_hmodule_id = cvh.id)", "curriculumSpecialityId", curriculumSpecialityId);
                qb.filter("cvh.is_minor_speciality = false");
            } else {
                if (curriculumSpecialityId != null) {
                    qb.optionalCriteria("(coalesce(cv.id, 0) != :curriculumVersionId or (cv.id = :curriculumVersionId"
                            + " and :curriculumSpecialityId not in (select cs.id from curriculum_version_hmodule_speciality cvhs"
                            + " join curriculum_version_speciality cvs on cvs.id = cvhs.curriculum_version_speciality_id"
                            + " join curriculum_speciality cs on cs.id = cvs.curriculum_speciality_id"
                            + " where cvhs.curriculum_version_hmodule_id = cvh.id)))", "curriculumVersionId", curriculumVersionId);
                    qb.filter("s.id not in (select cvhs2.subject_id from curriculum_version_hmodule_subject cvhs2"
                            + " join curriculum_version_hmodule cvh2 on cvh2.id = cvhs2.curriculum_version_hmodule_id"
                            + " left join curriculum_version_hmodule_speciality cvhsp2 on cvhsp2.curriculum_version_hmodule_id = cvh2.id"
                            + " left join curriculum_version_speciality cvs2 on cvs2.id = cvhsp2.curriculum_version_speciality_id"
                            + " left join curriculum_speciality cs2 on cs2.id = cvs2.curriculum_speciality_id"
                            + " where cvh2.curriculum_version_id = :curriculumVersionId and cs2.id = :curriculumSpecialityId)");
                    qb.parameter("curriculumSpecialityId", curriculumSpecialityId);
                } else {
                    qb.optionalCriteria("coalesce(cv.id, 0) != :curriculumVersionId", "curriculumVersionId", curriculumVersionId);
                    qb.optionalCriteria(
                            "s.id not in (select cvhs2.subject_id from curriculum_version_hmodule_subject cvhs2"
                                    + " join curriculum_version_hmodule cvh2 on cvh2.id = cvhs2.curriculum_version_hmodule_id"
                                    + " where cvh2.curriculum_version_id = :curriculumVersionId)",
                                    "curriculumVersionId", curriculumVersionId);
                }
            }
        }

        if (lookup.getStudent() != null) {
            if (otherStudents) {
                qb.requiredCriteria("shr.grade_code in (:posHighGrades)", "posHighGrades", POSITIVE_HIGHER_GRADES);

                qb.requiredCriteria("shr.student_id in (select s2.id from student s join"
                        + " student s2 on s.person_id=s2.person_id and s.id!=s2.id"
                        + " where s2.id!=:studentId and s.id = :studentId)",
                        "studentId", lookup.getStudent());
            }
            qb.requiredCriteria("s.id not in (select shr2.subject_id from student_higher_result shr2 "
                    + "where shr2.student_id = :studentId", "studentId", lookup.getStudent());
            qb.requiredCriteria("shr2.grade_code in (:posHighGrades2) and shr2.subject_id is not null)",
                    "posHighGrades2", POSITIVE_HIGHER_GRADES);
        }

        qb.sort(Language.EN.equals(lookup.getLang()) ? "s.name_en" : "s.name_et");

        String query;
        if (otherStudents) {
            query = "distinct s.id, s.name_et, s.name_en, s.code, s.credits, s.assessment_code,"
                    + " shr.grade_code, shr.grade_date, shr.teachers";
        } else {
            query = "distinct s.id, s.name_et, s.name_en, s.code, s.credits, s.assessment_code,"
                    + " null as grade_code, null as grade_date, null as teachers";
        }
        List<?> data = qb.select(query, em).getResultList();

        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 3);
            BigDecimal credits = resultAsDecimal(r, 4);
            String nameEt;
            String nameEn;
            if (Boolean.TRUE.equals(lookup.getWithCode())) {
                nameEt = SubjectUtil.subjectName(code, resultAsString(r, 1),
                        Boolean.TRUE.equals(lookup.getWithCredits()) ? credits : null);
                nameEn = SubjectUtil.subjectName(code, resultAsString(r, 2),
                        Boolean.TRUE.equals(lookup.getWithCredits()) ? credits : null);
            } else {
                nameEt = SubjectUtil.subjectNameWithoutCode(resultAsString(r, 1),
                        Boolean.TRUE.equals(lookup.getWithCredits()) ? credits : null);
                nameEn = SubjectUtil.subjectNameWithoutCode(resultAsString(r, 2),
                        Boolean.TRUE.equals(lookup.getWithCredits()) ? credits : null);
            }
            return new SubjectResult(resultAsLong(r, 0), nameEt, nameEn, code, credits, resultAsString(r, 5),
                    resultAsString(r, 6), resultAsLocalDate(r, 7), resultAsString(r, 8));
        }, data);
    }

    public List<OccupiedAutocompleteResult> teachers(Long schoolId, TeacherAutocompleteCommand lookup, boolean setMaxResults) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from teacher t inner join person p on t.person_id = p.id").sort("p.lastname", "p.firstname");

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("t.id = :tId", "tId", lookup.getId());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"),  "name", lookup.getName());
        qb.optionalCriteria("t.is_higher = :higher", "higher", lookup.getHigher());
        qb.optionalCriteria("t.is_vocational = :vocational", "vocational", lookup.getVocational());
        if(Boolean.TRUE.equals(lookup.getValid())) {
            String validFilter = "t.is_active = true";
            if (lookup.getSelectedTeacherId() != null) {
                validFilter += " or t.id = " + lookup.getSelectedTeacherId(); 
            }
            qb.filter(validFilter);
        }

        List<?> data = qb.select("t.id, p.firstname, p.lastname", em)
                .setMaxResults(setMaxResults ? MAX_ITEM_COUNT : Integer.MAX_VALUE).getResultList();

        Map<Long, OccupiedAutocompleteResult> teachersResult = new LinkedHashMap<>();
        if (!data.isEmpty()) {
            teachersResult = data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
                String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
                return new OccupiedAutocompleteResult(resultAsLong(r, 0), name, name);
            }, (v1, v2) -> v1, LinkedHashMap::new));
        }

        if (Boolean.TRUE.equals(lookup.getOccupied()) && !teachersResult.isEmpty()) {
            if (lookup.getDate() != null && lookup.getStartTime() != null && lookup.getEndTime() != null) {
                setTeachersOccupationStatus(lookup, teachersResult);
            }
        }
        return new ArrayList<>(teachersResult.values());
    }
    
    private void setTeachersOccupationStatus(TeacherAutocompleteCommand lookup, Map<Long, OccupiedAutocompleteResult> teachersResult) {
        List<Long> teacherIds = teachersResult.values().stream().map(t -> t.getId()).collect(Collectors.toList());
        
        List<LocalDateTime> starts = new ArrayList<>();
        List<LocalDateTime> ends = new ArrayList<>();
        starts.add(LocalDateTime.of(lookup.getDate(), 
                LocalTime.of(lookup.getStartTime().getHour(), lookup.getStartTime().getMinute())));
        ends.add(LocalDateTime.of(lookup.getDate(), 
                LocalTime.of(lookup.getEndTime().getHour(), lookup.getEndTime().getMinute())));
        
        if (lookup.getWeekAmount() != null) {
            eventRepeatStartAndEndTimes(lookup.getRepeatCode(), lookup.getWeekAmount(), starts, ends);
        } else if (lookup.getTimetable() != null) {
            eventRepeatStartAndEndTimes(lookup.getTimetable(), lookup.getRepeatCode(), starts, ends);
        }
        
        JpaNativeQueryBuilder occupiedQb = new JpaNativeQueryBuilder("from timetable_event te "
                + "join timetable_event_time tem on te.id = tem.timetable_event_id "
                + "join timetable_event_teacher tet on tem.id = tet.timetable_event_time_id");
        occupiedQb.requiredCriteria("tet.teacher_id in (:teacherIds)", "teacherIds", teacherIds);
        occupiedQb.filter(getTimeFilter(starts, ends));
        
        List<?> occupiedTeachers = occupiedQb.select("tet.teacher_id", em).getResultList();
        if (!occupiedTeachers.isEmpty()) {
            List<Long> occupiedTeacherIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), occupiedTeachers); 
            for (Long id : occupiedTeacherIds) {
                teachersResult.get(id).setIsOccupied(Boolean.TRUE);
            }
        }
    }
    
    public void eventRepeatStartAndEndTimes(String repeatCode, Long weekAmount, List<LocalDateTime> starts, List<LocalDateTime> ends) {
        long daysToAdd = daysToAdd(repeatCode);
        if (daysToAdd == 0) {
            return;
        }
        
        LocalDateTime endTime = starts.get(0).plusWeeks(weekAmount.longValue());
        LocalDateTime start = starts.get(0).plusDays(daysToAdd);
        LocalDateTime end = ends.get(0).plusDays(daysToAdd);
        while (endTime.isAfter(start)) {
            starts.add(start);
            ends.add(end);
            start = start.plusDays(daysToAdd);
            end = end.plusDays(daysToAdd);
        }
    }
    
    public void eventRepeatStartAndEndTimes(Long timetableId, String repeatCode, List<LocalDateTime> starts, List<LocalDateTime> ends) {
        long daysToAdd = daysToAdd(repeatCode);
        if (daysToAdd == 0) {
            return;
        }
        
        Timetable timetable = em.getReference(Timetable.class, timetableId);
        StudyPeriod sp = timetable.getStudyPeriod();
        
        LocalDateTime endTime = DateUtils.lastMomentOfDay(sp.getEndDate());
        LocalDateTime start = starts.get(0).plusDays(daysToAdd);
        LocalDateTime end = ends.get(0).plusDays(daysToAdd);
        while (endTime.isAfter(start)) {
            starts.add(start);
            ends.add(end);
            start = start.plusDays(daysToAdd);
            end = end.plusDays(daysToAdd);
        }
    }
    
    private static long daysToAdd(String repeatCode) {
        long daysToAdd;
        if (TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_P.name().equals(repeatCode)) {
            daysToAdd = 1;
        } else if (TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N.name().equals(repeatCode)) {
            daysToAdd = 7;
        } else if (TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N2.name().equals(repeatCode)) {
            daysToAdd = 14;
        } else {
            daysToAdd = 0;
        }
        return daysToAdd;
    }

    public List<AutocompleteResult> studyMaterials(Long schoolId, StudyMaterialAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from study_material m").sort("m.name_et");

        qb.requiredCriteria("m.school_id = :school_id", "school_id", schoolId);

        qb.optionalCriteria("m.teacher_id = :teacher_id", "teacher_id", lookup.getTeacher());
        qb.optionalCriteria("m.id not in (select study_material_id from study_material_connect where subject_study_period_id = :subject_study_period_id)", 
                "subject_study_period_id", lookup.getSubjectStudyPeriod());
        qb.optionalCriteria("m.id not in (select study_material_id from study_material_connect where journal_id = :journal_id)", 
                "journal_id", lookup.getJournal());
        qb.optionalContains("m.name_et", "name", lookup.getName());

        List<?> data = qb.select("m.id as material_id, m.name_et as material_name", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    /**
     * startDate and endDate required to get current studyPeriod in front end
     */
    public List<StudyPeriodWithYearIdDto> studyPeriods(Long schoolId) {
        List<StudyPeriod> data = em.createQuery("select sp from StudyPeriod sp where sp.studyYear.school.id = ?1 "
                + "order by sp.studyYear.startDate, sp.startDate", StudyPeriod.class)
                .setParameter(1, schoolId).getResultList();
        return StreamUtil.toMappedList(StudyPeriodWithYearIdDto::of, data);
    }
    
    public List<StudyPeriodWithYearDto> studyPeriodsWithYear(Long schoolId) {
        List<?> data = em.createNativeQuery("select sp.id as sp_id, sp.name_et as sp_name_et, sp.name_en as sp_name_en"
                + ", sp.type_code, sp.start_date as sp_start_date, sp.end_date as sp_end_date, sp.version"
                + ", c.code, c.name_et as c_name_et, c.name_en as c_name_en, sy.id as sy_id"
                + ", sy.start_date as sy_start_date, sy.end_date as sy_end_date, 0 as count"
                + " from study_period sp"
                + " join study_year sy on sy.id = sp.study_year_id"
                + " join classifier c on sy.year_code = c.code"
                + " where sy.school_id = ?1"
                + " order by sy_start_date, sp_start_date")
                .setParameter(1, schoolId)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            StudyPeriodWithYearDto dto = new StudyPeriodWithYearDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setNameEn(resultAsString(r, 2));
            dto.setType(resultAsString(r, 3));
            dto.setStartDate(resultAsLocalDate(r, 4));
            dto.setEndDate(resultAsLocalDate(r, 5));
            dto.setVersion(resultAsLong(r, 6));
            StudyYearSearchDto yearDto = new StudyYearSearchDto();
            yearDto.setCode(resultAsString(r, 7));
            yearDto.setNameEt(resultAsString(r, 8));
            yearDto.setNameEn(resultAsString(r, 9));
            yearDto.setId(resultAsLong(r, 10));
            yearDto.setStartDate(resultAsLocalDate(r, 11));
            yearDto.setEndDate(resultAsLocalDate(r, 12));
            yearDto.setCount(resultAsLong(r, 13));
            dto.setStudyYear(yearDto);
            return dto;
        }, data);
    }

    public List<StudyYearSearchDto> studyYears(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from study_year sy inner join classifier c on sy.year_code = c.code").sort("sy.start_date asc");
        qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", schoolId);
        List<?> data = qb.select("c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, 0 as count", em).getResultList();
        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }
    
    public List<AutocompleteResult> saisAdmissionCodesArchived(Long schoolId) {
        List<?> data = em.createNativeQuery("select sa.id, sa.code from sais_admission sa "+
                "where sa.curriculum_version_id in (select cv.id from curriculum_version cv "+
                "join curriculum c on cv.curriculum_id = c.id where c.school_id = ?1)" +
                "and (sa.is_archived is null OR sa.is_archived = false)")
                .setParameter(1, schoolId)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), code, code);
        }, data);
    }
    
    public List<SaisClassifierSearchDto> saisCurriculumClassifiers(Long schoolId) {
        List<?> data = em.createNativeQuery("select distinct cl.code, cl.name_et, cl.name_en from sais_admission sa"+
                " join curriculum_version cv on sa.curriculum_version_id = cv.id"+
        		" join curriculum c on cv.curriculum_id = c.id"+
                " join classifier cl on c.orig_study_level_code = cl.code"+
        		" where c.school_id = ?1 and (sa.is_archived is null OR sa.is_archived = false)")
                .setParameter(1, schoolId)
                .getResultList();
        List<SaisClassifierSearchDto> result = StreamUtil.toMappedList(r -> {
            SaisClassifierSearchDto c = new SaisClassifierSearchDto();
            c.setCode(resultAsString(r, 0));
            c.setNameEt(resultAsString(r, 1));
            c.setNameEn(resultAsString(r, 2));
            return c;
        }, data);
        result.sort(Comparator.comparing(SaisClassifierSearchDto::getNameEt));
        return result;
    }

    public List<AutocompleteResult> saisAdmissionCodes(Long schoolId) {
        List<?> data = em.createNativeQuery("select distinct sa.code from sais_admission sa "+
                "where sa.curriculum_version_id in (select cv.id from curriculum_version cv "+
                "join curriculum c on cv.curriculum_id = c.id where c.school_id = ?1) ")
                .setParameter(1, schoolId)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r,0);
            return new AutocompleteResult(null, code, code);
        }, data);
    }

    public List<SaisClassifierSearchDto> saisClassifiers(String parentCode) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from sais_classifier c");
        qb.requiredCriteria("c.parent_code = :parentCode", "parentCode", parentCode);

        List<?> data = qb.select("c.code, c.name_et, c.name_en", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            SaisClassifierSearchDto c = new SaisClassifierSearchDto();
            c.setCode(resultAsString(r, 0));
            c.setNameEt(resultAsString(r, 1));
            c.setNameEn(resultAsString(r, 2));
            return c;
        }, data);
    }

    public Page<AutocompleteResult> vocationalModules(Long schoolId, VocationalModuleCommand lookup) {
        String nameField = Language.EN.equals(lookup.getLang()) ? "name_en" : "name_et";
        PageRequest pageable = sortAndLimit(nameField);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_module cm "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join curriculum c on c.id = cm.curriculum_id").sort(pageable);

        qb.requiredCriteria("c.school_id =:schoolId", "schoolId", schoolId);
        qb.optionalCriteria("c.id in (select uc.curriculum_id from user_curriculum uc "
                + "where uc.user_id = :userId)", "userId", lookup.getUserId());

        qb.filter("c.is_higher = false");
        qb.optionalContains(String.format("concat(%s,' - ',%s,' (',%s,')')", "cm." + nameField,
                "mcl." + nameField, "c.code"), "name", lookup.getName());

        return JpaQueryUtil.pagingResult(qb, "cm.id, cm.name_et, cm.name_en, mcl.name_et mcl_name_et, "
                + "mcl.name_en mcl_name_en, c.code", em, pageable).map(r -> {
            return AutocompleteResult.curriculumModuleResult(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5));
        });
    }

    public Page<AutocompleteResult> vocationalOccupationModules(Long schoolId, VocationalModuleCommand lookup) {
        String nameField = Language.EN.equals(lookup.getLang()) ? "name_en" : "name_et";
        PageRequest pageable = sortAndLimit(nameField);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_omodule cvo "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id and cm.curriculum_id = c.id "
                + "join classifier mcl on mcl.code = cm.module_code").sort(pageable);

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("c.id in (select uc.curriculum_id from user_curriculum uc "
                + "where uc.user_id = :userId)", "userId", lookup.getUserId());

        qb.filter("c.is_higher = false");
        qb.optionalContains(String.format("concat(%s,' - ',%s,' (',%s,')')", "cm." + nameField,
                "mcl." + nameField, "cv.code"), "name", lookup.getName());

        return JpaQueryUtil.pagingResult(qb, "cvo.id, cm.name_et, cm.name_en, mcl.name_et mcl_name_et, "
                + "mcl.name_en mcl_name_en, cv.code", em, pageable).map(r -> {
            return AutocompleteResult.curriculumVersionOccupationModuleResult(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5));
        });
    }

    public List<JournalAutocompleteResult> journals(HoisUserDetails user, JournalAutocompleteCommand lookup) {
        return journals(user.getSchoolId(), user.getTeacherId(), lookup);
    }
    
    public List<JournalAutocompleteResult> journals(Long schoolId, JournalAutocompleteCommand lookup) {
        return journals(schoolId, null, lookup);
    }
    
    private List<JournalAutocompleteResult> journals(Long schoolId, Long teacherId, JournalAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j");
        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("exists (select jot.journal_id from journal_omodule_theme jot "
                + "join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id "
                + "join lesson_plan lp on lpm.lesson_plan_id = lp.id "
                + "join student_group sg on lp.student_group_id = sg.id "
                + "join user_curriculum uc on uc.curriculum_id = sg.curriculum_id "
                + "where jot.journal_id = j.id and uc.user_id = :userId)", "userId", lookup.getUserId());

        if (teacherId != null) {
            qb.requiredCriteria("j.id in (select jt.journal_id from journal_teacher jt where jt.teacher_id = :teacherId)", "teacherId", teacherId);
        }
        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", lookup.getStudyYear());
        qb.optionalContains("j.name_et",  "name_et", lookup.getName());
        
        qb.sort("j.name_et");
        List<?> data = qb.select("j.id, j.name_et, j.study_year_id", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            return new JournalAutocompleteResult(resultAsLong(r, 0), name, name, resultAsLong(r, 2));
        }, data);
    }

    private static PageRequest sortAndLimit(String... sortFields) {
        return new PageRequest(0, MAX_ITEM_COUNT, new Sort(sortFields));
    }

    public List<EnterpriseResult> enterprises(SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise e");
        qb.optionalContains("e.name", "name", lookup.getName());
        List<?> data = qb.select("e.id, e.name, e.contact_person_name, e.contact_person_email, e.contact_person_phone, e.reg_code", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            String enterpriseName = EnterpriseUtil.getName(resultAsString(r, 1), resultAsString(r, 5));
            EnterpriseResult enterpriseResult = new EnterpriseResult(resultAsLong(r, 0), enterpriseName, enterpriseName);
            enterpriseResult.setContactPersonName(resultAsString(r, 2));
            enterpriseResult.setContactPersonEmail(resultAsString(r, 3));
            enterpriseResult.setContactPersonPhone(resultAsString(r, 4));
            return enterpriseResult;
        }, data);
    }
    
    public List<EnterpriseResult> activeEnterprises(HoisUserDetails user, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise e "
                + "join enterprise_school es on es.enterprise_id = e.id");
        qb.requiredCriteria("es.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains("e.name", "name", lookup.getName());
        qb.filter("es.is_active = true");
        List<?> data = qb.select("e.id, e.name, e.contact_person_name, e.contact_person_email, e.contact_person_phone, e.reg_code", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            String enterpriseName = EnterpriseUtil.getName(resultAsString(r, 1), resultAsString(r, 5));
            EnterpriseResult enterpriseResult = new EnterpriseResult(resultAsLong(r, 0), enterpriseName, enterpriseName);
            enterpriseResult.setContactPersonName(resultAsString(r, 2));
            enterpriseResult.setContactPersonEmail(resultAsString(r, 3));
            enterpriseResult.setContactPersonPhone(resultAsString(r, 4));
            return enterpriseResult;
        }, data);
    }
    
    public List<AutocompleteResult> journalsAndSubjects(Long schoolId, JournalAndSubjectAutocompleteCommand lookup) {
        JournalAutocompleteCommand journalLookup = new JournalAutocompleteCommand();
        journalLookup.setStudyYear(lookup.getStudyYear());
        if (lookup.getName() != null) {
            journalLookup.setLang(lookup.getLang());
            journalLookup.setName(lookup.getName());
        }
        List<JournalAutocompleteResult> journalsList = journals(schoolId, journalLookup);
        // Change journal ids to negative to differentiate between journal and subject ids
        for (AutocompleteResult journal : journalsList) {
            journal.setId(Long.valueOf(-journal.getId().longValue()));
        }

        SubjectAutocompleteCommand subjectLookup = new SubjectAutocompleteCommand();
        if (lookup.getName() != null) {
            journalLookup.setLang(lookup.getLang());
            subjectLookup.setName(lookup.getName());
        }
        subjectLookup.setPractice(lookup.getPractice());
        List<SubjectResult> subjectsList = subjects(schoolId, subjectLookup);
        
        List<AutocompleteResult> journalsAndSubjects = new ArrayList<>();
        journalsAndSubjects.addAll(journalsList);
        journalsAndSubjects.addAll(subjectsList);

        journalsAndSubjects.sort(Comparator.comparing(
                Language.EN.equals(lookup.getLang()) ? AutocompleteResult::getNameEn : AutocompleteResult::getNameEt,
                String.CASE_INSENSITIVE_ORDER));
        
        return journalsAndSubjects;
    }
    
    public List<LiteralResult> journalsAndStudentGroups(Long schoolId, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j "
                + "left join journal_omodule_theme jot on j.id = jot.journal_id "
                + "left join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id "
                + "left join lesson_plan lp on lpm.lesson_plan_id = lp.id "
                + "left join student_group sg on lp.student_group_id = sg.id");
        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        try {
            StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
            qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", EntityUtil.getId(studyYear));
        } catch (Exception e) {
            log.error("no studyYear found. fault={}", e.getMessage());
        }
        qb.optionalContains("j.name_et",  "name_et", lookup.getName());
        qb.sort("j.name_et");
        qb.groupBy("j.id, j.name_et");
        List<?> data = qb.select("j.id, j.name_et, string_agg(distinct sg.code, ', ') as student_groups", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String studentGroups = resultAsString(r, 2);
            String name = resultAsString(r, 1) + (studentGroups != null ? "(" + studentGroups + ")" : "");
            return new LiteralResult(resultAsLong(r, 0), name, name, resultAsString(r, 1));
        }, data);
    }
    
    public List<AutocompleteResult> committees(Long schoolId, CommitteeAutocompleteCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from committee c "
                + "join committee_member cm on c.id = cm.committee_id "
                + "join person p on cm.person_id = p.id");
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("c.type_code = :typeCode", "typeCode", command.getType());
        qb.optionalCriteria("c.id = :committeeId", "committeeId", command.getId());
        qb.optionalContains("concat(c.name_et, ' (', p.firstname, ' ', p.lastname,')')", "name", command.getName());
        qb.optionalCriteria("c.valid_from >= :validFrom", "validFrom", command.getValidFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("d.valid_thru <= :validThru", "validThru", command.getValidThru(), DateUtils::lastMomentOfDay);
        if (Boolean.TRUE.equals(command.getValid())) {
            qb.filter(" (c.valid_from <= current_date and c.valid_thru >= current_date) ");
        } else if (Boolean.FALSE.equals(command.getValid())) {
            qb.filter(" (c.valid_from > current_date or c.valid_thru < current_date) ");
        }
        
        if (command.getMemberPerson() != null) {
            qb.filter("exists (select p2.id from person p2 "
                    + "join user_ u on p2.id = u.person_id "
                    + "join committee_member cm2 on p2.id = cm2.person_id "
                    + "join committee c2 on cm2.committee_id = c2.id "
                    + "where u.id = " + command.getMemberPerson() + " and c2.id = c.id)");
        }
        
        qb.groupBy("c.id");
        qb.sort("c.name_et");
        List<?> data = qb.select(
                "c.id, c.name_et, array_to_string(array_agg(p.firstname || ' ' || p.lastname), ', ') as members", em)
                .getResultList();
        
        Set<AutocompleteResult> committees = StreamUtil.toMappedSet(r -> {
            String name = resultAsString(r, 1);
            String caption = (name != null ? name : "-") + " (" + resultAsString(r, 2) + ")";
            return new AutocompleteResult(resultAsLong(r, 0), caption, caption);
        }, data);
        List<AutocompleteResult> committeesList = new ArrayList<>();
        committeesList.addAll(committees);
        return committeesList;
    }

    public List<AutocompleteResult> committeeMembers(Long schoolId, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from person p")
                .sort("p.lastname", "p.firstname");

        qb.requiredCriteria("p.id in (select t.person_id"
            + " from teacher t "
            + " where t.school_id = :schoolId and t.is_active = true"
            + " union"
            + " select u.person_id"
            + " from user_ u"
            + " where u.school_id = :schoolId and (u.role_code = 'ROLL_A' or u.role_code = 'ROLL_J')"
            + " and (valid_from is null or valid_from <= now())"
            + " and (valid_thru is null or valid_thru >= now()))", "schoolId", schoolId);
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", lookup.getName());

        List<?> data = qb.select("p.id, p.firstname, p.lastname", em).setMaxResults(MAX_ITEM_COUNT).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    /**
     * Collects all the specialities by curriculum or curriculum version id.
     * If student or representative asks then studentId is used during search.
     * 
     * @param user HoisUserDetails
     * @param lookup search criteria
     * @return list of specialities with {@link CurriculumSpeciality} id/name(estonian, english, russian)/{@link CurriculumVersion} id}
     */
    public Set<SpecialityAutocompleteResult> specialities(HoisUserDetails user, SpecialitiesAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_speciality cs " + 
                "join curriculum c on c.id = cs.curriculum_id " + 
                "left join curriculum_version_speciality cvs on cvs.curriculum_speciality_id = cs.id " +
                "left join curriculum_version cv on cv.id = cvs.curriculum_version_id ").groupBy("cs.id, c.id");
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("c.id = :cId", "cId", lookup.getCurriculum());
        qb.optionalCriteria("cv.id = :cvId", "cvId", lookup.getCurriculumVersion());
        if (user.isStudent() || user.isRepresentative()) {
            qb.requiredCriteria("cv.id in (select s.curriculum_version_id from student s where s.id = :studentId)", "studentId", user.getStudentId());
        }
        List<?> results = qb.select("cs.id, cs.name_et, cs.name_en, c.id as cid, string_agg(cv.id\\:\\:character varying, ';') as versions", em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            String versions = resultAsString(r, 4);
            return new SpecialityAutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsLong(r, 3),
                    versions == null ? null : Arrays.stream(versions.split(";")).map(Long::parseLong).collect(Collectors.toSet()));
        }, results);
    }

    public List<AutocompleteResult> enterpriseLocations(HoisUserDetails user, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise e "
                + "join enterprise_school es on es.enterprise_id = e.id "
                + "left join enterprise_school_location esl on esl.enterprise_school_id = es.id ");
        qb.requiredCriteria("e.id = :esId", "esId", lookup.getId());
        qb.requiredCriteria("es.school_id = :schoolId", "schoolId", user.getSchoolId());
        List<?> data = qb.select("distinct esl.address as eslAddress, es.address as esAddress, es.id", em).getResultList();
        List<AutocompleteResult> eslAddress = StreamUtil.toMappedList(r -> {
            AutocompleteResult locations = new AutocompleteResult(null, resultAsString(r, 0), resultAsString(r, 0));
            return locations;
        }, data);
        List<AutocompleteResult> esAddress = StreamUtil.toMappedList(r -> {
            AutocompleteResult locations = new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 1));
            return locations;
        }, data);
        eslAddress.addAll(esAddress);
        List<AutocompleteResult> filteredAddresses = eslAddress.stream().filter(p -> p.getNameEt() != null)
                .filter(StreamUtil.distinctByKey(AutocompleteResult::getNameEt)).collect(Collectors.toList());
        return filteredAddresses;
    }

    public List<SupervisorDto> enterpriseSupervisors(HoisUserDetails user, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise e "
                + "join enterprise_school es on es.enterprise_id = e.id "
                + "left join enterprise_school_person esp on esp.enterprise_school_id = es.id ");
        qb.requiredCriteria("e.id = :esId", "esId", lookup.getId());
        qb.requiredCriteria("es.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("esp.is_supervisor = true");
        List<?> data = qb.select("concat(esp.firstname, ' ', esp.lastname), esp.email, esp.phone", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            SupervisorDto supervisor = new SupervisorDto();
            supervisor.setSupervisorName(resultAsString(r, 0));
            supervisor.setSupervisorEmail(resultAsString(r, 1));
            supervisor.setSupervisorPhone(resultAsString(r, 2));
            return supervisor;
        }, data);
    }
    
    public List<SupervisorDto> enterpriseContacts(HoisUserDetails user, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise e "
                + "join enterprise_school es on es.enterprise_id = e.id "
                + "left join enterprise_school_person esp on esp.enterprise_school_id = es.id ");
        qb.requiredCriteria("e.id = :esId", "esId", lookup.getId());
        qb.requiredCriteria("es.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("esp.is_contact = true");
        List<?> data = qb.select("concat(esp.firstname, ' ', esp.lastname), esp.email, esp.phone", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            SupervisorDto supervisor = new SupervisorDto();
            supervisor.setSupervisorName(resultAsString(r, 0));
            supervisor.setSupervisorEmail(resultAsString(r, 1));
            supervisor.setSupervisorPhone(resultAsString(r, 2));
            return supervisor;
        }, data);
    }

    public List<AutocompleteResult> practiceEvaluation(HoisUserDetails user,
            PracticeEvaluationAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_evaluation pe ");
        qb.requiredCriteria("pe.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("pe.is_active = :isActive", "isActive", lookup.getActive());
        qb.optionalCriteria("pe.target_code = :targetCode", "targetCode", lookup.getTargetCode());
        List<?> data = qb.select("pe.id, pe.name_et", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1));
        }, data);
    }

    public List<AutocompleteResult> polls(HoisUserDetails user, PollAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from poll p ");
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("p.type_code = :type", "type", lookup.getType());
        qb.requiredCriteria("p.valid_thru < :validThru", "validThru", LocalDate.now());
        qb.filter("exists( select 1 from response r " + 
                "join response_object ro on ro.response_id = r.id " +
                "and (r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' " +
                "or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "') " + 
                "where r.poll_id = p.id)");
        List<?> data = qb.select("p.id, p.name_et, p.name_en", em).getResultList();
        return StreamUtil.toMappedList(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), data);
    }

    public List<AutocompleteResult> questions(HoisUserDetails user, PollQuestionAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from (select q.id, q.name_et, q.name_en, string_agg(p.id\\:\\:character varying, ', ') as pollIds from question q "
                + "join poll_theme_question ptq on ptq.question_id = q.id "
                + "join poll_theme pt on ptq.poll_theme_id = pt.id "
                + "join poll p on pt.poll_id = p.id "
                + "where q.school_id = " + user.getSchoolId()
                + " and p.id in :polls"
                + " group by q.id, q.name_et, q.name_en) questions");
        qb.parameter("polls", lookup.getPolls());
        List<?> data = qb.select("questions.id, questions.name_et, questions.name_en, questions.pollIds", em).getResultList();
        List<PollResultsQuestionDto> mappedQuestions = StreamUtil.toMappedList(r -> {
            PollResultsQuestionDto dto = new PollResultsQuestionDto();
            String pollsString = resultAsString(r, 3);
            if (pollsString != null) {
                List<Long> polls = Arrays.stream(pollsString.split(", ")).map(Long::parseLong).collect(Collectors.toList());
                dto.setPolls(polls);
            }
            dto.setQuestion(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)));
            return dto;
        }, data);
        List<AutocompleteResult> fittingQuestions = mappedQuestions.stream()
                .filter(p -> p.getPolls().containsAll(lookup.getPolls()))
                .map(p -> p.getQuestion()).collect(Collectors.toList());
        return fittingQuestions;
    }

    public List<LiteralResult> subjectStudyPeriods(Long schoolId, SubjectStudyPeriodCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                + "join subject s on s.id = ssp.subject_id "
                + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "left join teacher t on t.id = sspt.teacher_id "
                + "left join person p on p.id = t.person_id");
        qb.requiredCriteria("s.school_id = :schoolId and t.school_id = s.school_id", "schoolId", schoolId);
        qb.optionalContains("s.name_et", "subjectName", lookup.getName());
        qb.optionalCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", lookup.getStudyPeriod());
        qb.groupBy("ssp.id, s.name_et, s.name_en, s.code");
        List<?> data = qb.select("ssp.id, s.name_et, s.name_en, s.code, string_agg(distinct concat(p.firstname, ' ', p.lastname), ', ') as teachers", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String teachers = resultAsString(r, 4);
            String code = resultAsString(r, 3);
            String nameEt = PollService.getSubjectNameWithTeacher(resultAsString(r, 1), code, teachers);
            String nameEn = PollService.getSubjectNameWithTeacher(resultAsString(r, 2), code, teachers);
            return new LiteralResult(resultAsLong(r, 0), nameEt, nameEn, resultAsString(r, 1));
        }, data);
    }
    
}
