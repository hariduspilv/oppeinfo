package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.timetable.LessonTimeBuilding;
import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;
import ee.hitsa.ois.enums.Day;
import ee.hitsa.ois.repository.LessonTimeBuildingGroupRepository;
import ee.hitsa.ois.repository.LessonTimeRepository;
import ee.hitsa.ois.web.commandobject.timetable.LessonTimeSearchCommand;
import ee.hitsa.ois.web.dto.timetable.LessonTimeSearchDto;

@Transactional
@Service
public class LessonTimeService {

    @Autowired
    private LessonTimeRepository lessonTimeRepository;

    @Autowired
    private LessonTimeBuildingGroupRepository lessonTimeBuildingGroupRepository;

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

    private static String getDayProperty(String dayString) {
        Day day = Day.valueOf(dayString);
        return DAY_MAPPING.get(day);
    }

    public LocalDate currentPeriodStartDate() {
        LessonTimeBuildingGroup group = lessonTimeBuildingGroupRepository.findFirstByValidFromLessThanEqualOrderByValidFromDesc(LocalDate.now());
        if (group != null) {
            return group.getValidFrom();
        }
        return null;
    }

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
}
