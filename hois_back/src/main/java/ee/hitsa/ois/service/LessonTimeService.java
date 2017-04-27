package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.domain.timetable.LessonTimeBuilding;
import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;
import ee.hitsa.ois.enums.Day;
import ee.hitsa.ois.repository.BuildingRepository;
import ee.hitsa.ois.repository.LessonTimeBuildingGroupRepository;
import ee.hitsa.ois.repository.LessonTimeBuildingRepository;
import ee.hitsa.ois.repository.LessonTimeRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.timetable.LessonTimeSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.LessonTimeBuildingGroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeGroupsDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeSearchDto;

@Transactional
@Service
public class LessonTimeService {

    private static final Logger log = LoggerFactory.getLogger(LessonTimeService.class);

    private static final EnumMap<Day, String> DAY_MAPPING = new EnumMap<>(Day.class);

    static {
        DAY_MAPPING.put(Day.NADALAPAEV_E, "dayMon");
        DAY_MAPPING.put(Day.NADALAPAEV_T, "dayTue");
        DAY_MAPPING.put(Day.NADALAPAEV_K, "dayWed");
        DAY_MAPPING.put(Day.NADALAPAEV_N, "dayThu");
        DAY_MAPPING.put(Day.NADALAPAEV_R, "dayFri");
        DAY_MAPPING.put(Day.NADALAPAEV_L, "daySat");
        DAY_MAPPING.put(Day.NADALAPAEV_P, "daySun");
    }

    @Autowired
    private LessonTimeRepository lessonTimeRepository;
    @Autowired
    private LessonTimeBuildingRepository lessonTimeBuildingRepository;
    @Autowired
    private LessonTimeBuildingGroupRepository lessonTimeBuildingGroupRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private EntityManager em;

    public Page<LessonTimeSearchDto> search(Long schoolId, LessonTimeSearchCommand criteria, Pageable pageable) {
        return lessonTimeRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            if (criteria.getFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("lessonTimeBuildingGroup").get("validFrom"), criteria.getFrom().toLocalDate()));
            }
            if (criteria.getThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("lessonTimeBuildingGroup").get("validThru"), criteria.getThru().toLocalDate()));
            }
            if (!CollectionUtils.isEmpty(criteria.getDay())) {
                Predicate[] days = criteria.getDay().stream().map(day -> cb.equal(root.get(getDayProperty(day)), Boolean.TRUE)).collect(Collectors.toList()).toArray(new Predicate[0]);
                filters.add(cb.or(days));
            }
            if (!CollectionUtils.isEmpty(criteria.getBuilding())) {
                Subquery<Long> buildingQuery = query.subquery(Long.class);
                Root<LessonTimeBuilding> buildingRoot = buildingQuery.from(LessonTimeBuilding.class);
                buildingQuery = buildingQuery.select(buildingRoot.get("id"))
                        .where(cb.and(buildingRoot.get("building").get("id").in(criteria.getBuilding()), cb.equal(buildingRoot.get("lessonTimeBuildingGroup").get("id"), root.get("lessonTimeBuildingGroup").get("id"))));
                filters.add(cb.exists(buildingQuery));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(LessonTimeSearchDto::of);
    }

    public LocalDate currentPeriodStartDate(Long schoolId) {
        Pageable pageable = new PageRequest(0, 1, new Sort(Direction.DESC, "valid_from"));
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from lesson_time_building_group ltbg "
                + "inner join lesson_time lt on lt.lesson_time_building_group_id = ltbg.id").sort(pageable);

        qb.requiredCriteria("valid_from <= :validFrom", "validFrom", LocalDate.now());
        qb.requiredCriteria("school_id <= :schoolId", "schoolId", schoolId);

        Page<LocalDate> page = JpaQueryUtil.pagingResult(qb, "valid_from",  em, pageable).map(r -> {
            return JpaQueryUtil.resultAsLocalDate(r, 1);
        });

        if (page != null && !CollectionUtils.isEmpty(page.getContent())) {
            return page.getContent().get(0);
        }
        return null;

    }

    public LessonTime create(HoisUserDetails user, LessonTimeGroupsDto newLessonTimeGroupsDto) {
        LocalDate validFrom = newLessonTimeGroupsDto.getValidFrom();
        assertPeriodOverlapping(newLessonTimeGroupsDto, validFrom);
        LessonTimeBuildingGroup createdGroup = null;
        for (LessonTimeBuildingGroupDto groupDto : newLessonTimeGroupsDto.getLessonTimeBuildingGroups()) {
            createdGroup = createGroup(groupDto, user.getSchoolId(), validFrom);
        }

        if (createdGroup == null) {
            return null;
        }

        updatePreviousGroupValidThru(createdGroup, user.getSchoolId());
        return createdGroup.getLessonTimes().stream().findFirst().orElse(null);
    }

    public LessonTime save(HoisUserDetails user, LessonTimeGroupsDto updatedLessonTimeGroupsDto) {
        LocalDate validFrom = updatedLessonTimeGroupsDto.getValidFrom();
        assertPeriodOverlapping(updatedLessonTimeGroupsDto, validFrom);

        deleteGroups(updatedLessonTimeGroupsDto, validFrom, user.getSchoolId());

        LessonTimeBuildingGroup savedGroup = null;
        for (LessonTimeBuildingGroupDto groupDto : updatedLessonTimeGroupsDto.getLessonTimeBuildingGroups()) {
            if (groupDto.getId() == null) {
                savedGroup = createGroup(groupDto, user.getSchoolId(), validFrom);
            } else {
                savedGroup = updateGroup(groupDto, user.getSchoolId(), validFrom);
            }
        }

        if (savedGroup == null) {
            return null;
        }

        updatePreviousGroupValidThru(savedGroup, user.getSchoolId());
        return savedGroup.getLessonTimes().stream().findFirst().orElse(null);
    }

    /**
     * Tundide aegade kehtivuse ajavahemikud ühe hoone lõikes ei tohi kattuda
     */
    private void assertPeriodOverlapping(LessonTimeGroupsDto lessonTimeGroupsDto, LocalDate validFrom) {
        Set<Long> selectedBuildings = lessonTimeGroupsDto.getLessonTimeBuildingGroups().stream().flatMap(group -> group.getBuildings().stream().map(AutocompleteResult::getId)).collect(Collectors.toSet());
        LocalDate latestValidFromForBuildings = previousValidFrom(selectedBuildings, validFrom);
        if (latestValidFromForBuildings != null && validFrom.isBefore(latestValidFromForBuildings)) {
            throw new ValidationFailedException("timetable.lessonTime.messages.validfromIsEarlierThanLatestValidFrom");
        }
    }

    public LocalDate minValidFrom(Set<Long> buildings, Long lessonTimeId) {
        if (lessonTimeId != null) {
            LessonTime lessonTime = lessonTimeRepository.getOne(lessonTimeId);
            Set<Long> savedBuildings = lessonTime.getLessonTimeBuildingGroup().getBuildings().stream().map(ltb -> ltb.getBuilding().getId()).collect(Collectors.toSet());
            if (savedBuildings.containsAll(buildings)) {
                return previousValidFrom(buildings, lessonTime.getLessonTimeBuildingGroup().getValidFrom().minusDays(1));
            }
        }
        LocalDate previousValidFrom = previousValidFrom(buildings, null);
        return previousValidFrom != null ? previousValidFrom.plusDays(1) : null;
    }

    public LocalDate previousValidFrom(Set<Long> buildingIds, LocalDate before) {
        LessonTimeBuilding lessonTimeBuilding = null;
        if (before == null) {
            lessonTimeBuilding = lessonTimeBuildingRepository.findFirstByBuildingIdInOrderByLessonTimeBuildingGroupValidFromDesc(buildingIds);
        } else {
            lessonTimeBuilding = lessonTimeBuildingRepository.findFirstByBuildingIdInAndLessonTimeBuildingGroupValidFromLessThanOrderByLessonTimeBuildingGroupValidFromDesc(buildingIds, before);
        }
        return lessonTimeBuilding != null ? lessonTimeBuilding.getLessonTimeBuildingGroup().getValidFrom() : null;
    }

    private void updatePreviousGroupValidThru(LessonTimeBuildingGroup savedGroup, Long schoolId) {
        LessonTimeBuildingGroup previousGroup = getPreviousGroup(savedGroup.getBuildings(), savedGroup.getValidFrom(), schoolId);
        if (previousGroup != null) {
            previousGroup.setValidThru(savedGroup.getValidFrom().minusDays(1));
            lessonTimeBuildingGroupRepository.save(previousGroup);
            log.info(String.format("lesson time building group %d valid thru updated, new value is %s", previousGroup.getId(), previousGroup.getValidThru().toString()));
        }
    }

    private LessonTimeBuildingGroup getPreviousGroup(Set<LessonTimeBuilding> buildings, LocalDate validFrom, Long schoolId) {
        List<String> buildingIds = buildings.stream().map(ltb -> ltb.getBuilding().getId().toString()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(buildingIds)) {
            return null;
        }

        // TODO: try to get rid of array comparison
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from (select * from (select ltbg.id, ltbg.valid_from, array_agg(DISTINCT building_id) as buildings from lesson_time_building_group ltbg "+
                "inner join lesson_time_building ltb on ltb.lesson_time_building_group_id = ltbg.id "+
                "inner join lesson_time lt on lt.lesson_time_building_group_id = ltbg.id "+
                "where lt.school_id = " + schoolId + " " +
                "group by ltbg.id) as buildings_query "
                + "where buildings_query.buildings = '{" + String.join(",", buildingIds) + "}' " //this is Array comparison
                + ") as from_query ").sort(new Sort(Direction.DESC, "from_query.valid_from"));

        qb.requiredCriteria("from_query.valid_from < :validFrom", "validFrom", validFrom);

        List<?> data = qb.select("from_query.id",  em).setMaxResults(1).getResultList();
        if (!data.isEmpty()) {
            return lessonTimeBuildingGroupRepository.getOne(Long.valueOf(((Number)data.get(0)).longValue()));
        }
        return null;
    }

    private void deleteGroups(LessonTimeGroupsDto newLessonTimeGroupsDto, LocalDate validFrom, Long schoolId) {
        Set<LessonTimeBuildingGroup> storedLessonTimeGroupsDto = getLessonTimeBuildingGroups(validFrom, schoolId);
        List<Long> ids = newLessonTimeGroupsDto.getLessonTimeBuildingGroups().stream().map(LessonTimeBuildingGroupDto::getId)
                .collect(Collectors.toList());
        List<LessonTimeBuildingGroup> deleted = storedLessonTimeGroupsDto.stream().filter(it -> !ids.contains(it.getId()))
                .collect(Collectors.toList());

        lessonTimeBuildingGroupRepository.delete(deleted);
    }

    private LessonTimeBuildingGroup updateGroup(LessonTimeBuildingGroupDto groupDto, Long schoolId, LocalDate validFrom) {
        LessonTimeBuildingGroup group = lessonTimeBuildingGroupRepository.getOne(groupDto.getId());
        group.setValidFrom(validFrom);
        updateLessonTimes(groupDto, schoolId, group);
        updateBuildings(groupDto, group);
        return lessonTimeBuildingGroupRepository.save(group);
    }

    private LessonTimeBuildingGroup createGroup(LessonTimeBuildingGroupDto groupDto, Long schoolId, LocalDate validFrom) {
        LessonTimeBuildingGroup group = new LessonTimeBuildingGroup();
        group.setValidFrom(validFrom);
        updateLessonTimes(groupDto, schoolId, group);
        updateBuildings(groupDto, group);
        return lessonTimeBuildingGroupRepository.save(group);
    }

    private void updateBuildings(LessonTimeBuildingGroupDto groupDto, LessonTimeBuildingGroup group) {
        EntityUtil.bindEntityCollection(group.getBuildings(), LessonTimeBuilding::getId, groupDto.getBuildings(),
                dto -> {
                    return group.getBuildings().stream().filter(ltb -> EntityUtil.getId(ltb.getBuilding()).equals(dto.getId())).map(LessonTimeBuilding::getId).findFirst().orElse(null);
                },
                building -> {
                    LessonTimeBuilding lessonTimeBuilding = new LessonTimeBuilding();
                    lessonTimeBuilding.setBuilding(buildingRepository.getOne(building.getId()));
                    lessonTimeBuilding.setLessonTimeBuildingGroup(group);
                    return lessonTimeBuilding;
                }, null);
    }

    private void updateLessonTimes(LessonTimeBuildingGroupDto groupDto, Long schoolId, LessonTimeBuildingGroup group) {
        EntityUtil.bindEntityCollection(group.getLessonTimes(), LessonTime::getId, groupDto.getLessonTimes(), LessonTimeDto::getId,
                lessonTimeDto -> {
                    LessonTime lessonTime = EntityUtil.bindToEntity(lessonTimeDto, new LessonTime());
                    lessonTime.setSchool(schoolRepository.getOne(schoolId));
                    lessonTime.setLessonTimeBuildingGroup(group);
                    return lessonTime;
                }, (updated, stored) -> EntityUtil.bindToEntity(updated, stored));
    }

    public Set<LessonTimeBuildingGroup> getLessonTimeBuildingGroups(LocalDate validFrom, Long schoolId) {
        return lessonTimeRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("lessonTimeBuildingGroup").get("validFrom"), validFrom));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().map(LessonTime::getLessonTimeBuildingGroup).collect(Collectors.toSet());
    }

    public LessonTimeGroupsDto getLessonTimeBuildingGroupsDto(LocalDate validFrom, Long schoolId) {
        Set<LessonTimeBuildingGroup> groups = getLessonTimeBuildingGroups(validFrom, schoolId);
        return LessonTimeGroupsDto.of(groups);
    }

    private static String getDayProperty(String dayString) {
        Day day = Day.valueOf(dayString);
        return DAY_MAPPING.get(day);
    }
}
