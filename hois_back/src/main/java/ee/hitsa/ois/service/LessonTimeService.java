package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.domain.timetable.LessonTimeBuilding;
import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;
import ee.hitsa.ois.enums.Day;
import ee.hitsa.ois.repository.LessonTimeBuildingGroupRepository;
import ee.hitsa.ois.repository.LessonTimeRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonTimeSearchCommand;
import ee.hitsa.ois.web.dto.timetable.LessonTimeBuildingGroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeGroupsDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeSearchDto;

@Transactional
@Service
public class LessonTimeService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private LessonTimeRepository lessonTimeRepository;
    @Autowired
    private LessonTimeBuildingGroupRepository lessonTimeBuildingGroupRepository;
    @Autowired
    private EntityManager em;

    public Page<LessonTimeSearchDto> search(Long schoolId, LessonTimeSearchCommand criteria, Pageable pageable) {
        return lessonTimeRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            if (criteria.getFrom() != null) {
                filters.add(cb.and(
                        cb.lessThanOrEqualTo(root.get("lessonTimeBuildingGroup").get("validFrom"),
                                criteria.getFrom().toLocalDate()),
                        cb.or(cb.greaterThanOrEqualTo(root.get("lessonTimeBuildingGroup").get("validThru"),
                                criteria.getFrom().toLocalDate()),
                                cb.isNull(root.get("lessonTimeBuildingGroup").get("validThru")))));
            }
            if (criteria.getThru() != null) {
                filters.add(cb.and(
                        cb.or(cb.greaterThanOrEqualTo(root.get("lessonTimeBuildingGroup").get("validThru"),
                                criteria.getThru().toLocalDate()),
                                cb.isNull(root.get("lessonTimeBuildingGroup").get("validThru"))),
                        cb.lessThanOrEqualTo(root.get("lessonTimeBuildingGroup").get("validFrom"),
                                criteria.getThru().toLocalDate())));
            }
            if (!CollectionUtils.isEmpty(criteria.getDay())) {
                List<Predicate> days = StreamUtil.toMappedList(day -> cb.equal(root.get(getDayProperty(day)), Boolean.TRUE), criteria.getDay());
                filters.add(cb.or(days.toArray(new Predicate[days.size()])));
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_time_building_group ltbg "
                + "inner join lesson_time lt on lt.lesson_time_building_group_id = ltbg.id").sort(new Sort(Direction.DESC, "valid_from"));

        qb.requiredCriteria("valid_from <= :validFrom", "validFrom", LocalDate.now());
        qb.requiredCriteria("school_id <= :schoolId", "schoolId", schoolId);
 
        List<?> data = qb.select("valid_from", em).setMaxResults(1).getResultList();
        if(data.isEmpty()) {
            return null;
        }
        return resultAsLocalDate(data.get(0), 0);
    }

    public LessonTime create(HoisUserDetails user, LessonTimeGroupsDto newLessonTimeGroupsDto) {
        LocalDate validFrom = newLessonTimeGroupsDto.getValidFrom();
        LessonTimeBuildingGroup createdGroup = null;
        for (LessonTimeBuildingGroupDto groupDto : newLessonTimeGroupsDto.getLessonTimeBuildingGroups()) {
            createdGroup = createGroup(groupDto, user.getSchoolId(), validFrom);
        }

        if (createdGroup == null) {
            return null;
        }

        updatePreviousGroupsValidThru(createdGroup, user.getSchoolId());
        return createdGroup.getLessonTimes().stream().findFirst().orElse(null);
    }

    public LessonTime save(HoisUserDetails user, LessonTimeGroupsDto updatedLessonTimeGroupsDto) {
        LocalDate validFrom = updatedLessonTimeGroupsDto.getValidFrom();
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

        return savedGroup.getLessonTimes().stream().findFirst().orElse(null);
    }

    public Map<String, LocalDate> validFromRange(Long schoolId, Long lessonTimeId) {
        LessonTime lessonTime = EntityUtil.getOptionalOne(LessonTime.class, lessonTimeId, em);
        LocalDate currentValidFrom = lessonTime != null ? lessonTime.getLessonTimeBuildingGroup().getValidFrom() : null;
        LocalDate currentValidThru = lessonTime != null ? lessonTime.getLessonTimeBuildingGroup().getValidThru() : null;

        Map<String, LocalDate> range = new HashMap<>();
        range.put("minValidFrom", minValidFrom(schoolId, currentValidFrom));
        range.put("maxValidFrom", currentValidThru);
        return range;
    }

    private LocalDate minValidFrom(Long schoolId, LocalDate currentValidFrom) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_time_building_group ltbg "
                + "join lesson_time lt on lt.lesson_time_building_group_id = ltbg.id");
        qb.requiredCriteria("lt.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("ltbg.valid_thru < :currentValidFrom", "currentValidFrom", currentValidFrom);

        qb.sort("ltbg.valid_thru desc");
        List<?> data = qb.select("case when ltbg.valid_thru is not null then ltbg.valid_thru else ltbg.valid_from end", em)
                .setMaxResults(1).getResultList();

        if (!data.isEmpty()) {
            LocalDate previousValidFrom = resultAsLocalDate(data.get(0), 0);
            return previousValidFrom.plusDays(1);
        }
        return null;
    }

    private void updatePreviousGroupsValidThru(LessonTimeBuildingGroup savedGroup, Long schoolId) {
        List<LessonTimeBuildingGroup> previousGroups = em
                .createQuery("select ltbg from LessonTimeBuildingGroup ltbg join ltbg.lessonTimes lt "
                        + "where lt.school.id = :schoolId and ltbg.validFrom < :validFrom and ltbg.validThru is null",
                        LessonTimeBuildingGroup.class)
                .setParameter("schoolId", schoolId).setParameter("validFrom", savedGroup.getValidFrom())
                .getResultList();

        LocalDate newValidThru = savedGroup.getValidFrom().minusDays(1);
        for (LessonTimeBuildingGroup previousGroup : previousGroups) {
            previousGroup.setValidThru(newValidThru);
            previousGroup = EntityUtil.save(previousGroup, em);
            log.info("lesson time building group {} valid thru updated, new value is {}", previousGroup.getId(),
                    previousGroup.getValidThru().toString());
        }
    }

    private void deleteGroups(LessonTimeGroupsDto newLessonTimeGroupsDto, LocalDate validFrom, Long schoolId) {
        Set<LessonTimeBuildingGroup> storedLessonTimeGroupsDto = lessonTimeBuildingGroups(validFrom, schoolId);
        Set<Long> ids = StreamUtil.toMappedSet(LessonTimeBuildingGroupDto::getId, newLessonTimeGroupsDto.getLessonTimeBuildingGroups());
        List<LessonTimeBuildingGroup> deleted = StreamUtil.toFilteredList(it -> !ids.contains(it.getId()), storedLessonTimeGroupsDto);
        lessonTimeBuildingGroupRepository.delete(deleted);
    }

    private LessonTimeBuildingGroup updateGroup(LessonTimeBuildingGroupDto groupDto, Long schoolId, LocalDate validFrom) {
        LessonTimeBuildingGroup group = em.getReference(LessonTimeBuildingGroup.class, groupDto.getId());
        group.setValidFrom(validFrom);
        updateLessonTimes(groupDto, schoolId, group);
        updateBuildings(groupDto, group);
        return EntityUtil.save(group, em);
    }

    private LessonTimeBuildingGroup createGroup(LessonTimeBuildingGroupDto groupDto, Long schoolId, LocalDate validFrom) {
        LessonTimeBuildingGroup group = new LessonTimeBuildingGroup();
        group.setValidFrom(validFrom);
        updateLessonTimes(groupDto, schoolId, group);
        updateBuildings(groupDto, group);
        return EntityUtil.save(group, em);
    }

    private void updateBuildings(LessonTimeBuildingGroupDto groupDto, LessonTimeBuildingGroup group) {
        EntityUtil.bindEntityCollection(group.getBuildings(), LessonTimeBuilding::getId, groupDto.getBuildings(),
                dto -> {
                    return group.getBuildings().stream().filter(ltb -> EntityUtil.getId(ltb.getBuilding()).equals(dto.getId())).map(LessonTimeBuilding::getId).findFirst().orElse(null);
                },
                building -> {
                    LessonTimeBuilding lessonTimeBuilding = new LessonTimeBuilding();
                    lessonTimeBuilding.setBuilding(em.getReference(Building.class, building.getId()));
                    lessonTimeBuilding.setLessonTimeBuildingGroup(group);
                    return lessonTimeBuilding;
                }, null);
    }

    private void updateLessonTimes(LessonTimeBuildingGroupDto groupDto, Long schoolId, LessonTimeBuildingGroup group) {
        School school = em.getReference(School.class, schoolId);
        EntityUtil.bindEntityCollection(group.getLessonTimes(), LessonTime::getId, groupDto.getLessonTimes(), LessonTimeDto::getId,
                lessonTimeDto -> {
                    LessonTime lessonTime = EntityUtil.bindToEntity(lessonTimeDto, new LessonTime());
                    lessonTime.setSchool(school);
                    lessonTime.setLessonTimeBuildingGroup(group);
                    return lessonTime;
                }, (updated, stored) -> EntityUtil.bindToEntity(updated, stored));
    }

    private Set<LessonTimeBuildingGroup> lessonTimeBuildingGroups(LocalDate validFrom, Long schoolId) {
        return em.createQuery("select lt from LessonTime lt where lt.school.id = ?1 and lt.lessonTimeBuildingGroup.validFrom = ?2", LessonTime.class)
                .setParameter(1, schoolId).setParameter(2, validFrom).getResultList()
                .stream().map(LessonTime::getLessonTimeBuildingGroup).collect(Collectors.toSet());
    }

    public LessonTimeGroupsDto getLessonTimeBuildingGroupsDto(LocalDate validFrom, Long schoolId) {
        Set<LessonTimeBuildingGroup> groups = lessonTimeBuildingGroups(validFrom, schoolId);
        return LessonTimeGroupsDto.of(groups);
    }

    private static String getDayProperty(String dayString) {
        return Day.valueOf(dayString).getProperty();
    }
}
