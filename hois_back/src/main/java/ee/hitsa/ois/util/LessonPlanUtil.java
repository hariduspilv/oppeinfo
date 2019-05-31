package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalCapacityType;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.domain.timetable.JournalTeacherCapacity;
import ee.hitsa.ois.web.dto.WeekDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanTeacherLoadDto;

/**
 * Utility functions for working with lesson plans
 */
public abstract class LessonPlanUtil {

    public static LessonPlanCapacityMapper capacityMapper(StudyYear studyYear) {
        return new LessonPlanCapacityMapper(studyYear);
    }

    public static class LessonPlanCapacityMapper {
        private final List<StudyPeriod> orderedStudyPeriods;
        private final Map<Long, List<Short>> studyPeriodWeekNrs;
        private final Map<Long, Integer> studyPeriodOffsets = new HashMap<>();
        private final int weekNrsCount;
        private final List<Long> studyPeriodPos = new ArrayList<>();
        private final Map<Long, StudyPeriod> studyPeriodsById;

        LessonPlanCapacityMapper(StudyYear studyYear) {
            orderedStudyPeriods = studyYear.getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate)).collect(Collectors.toList());
            studyPeriodWeekNrs = StreamUtil.toMap(StudyPeriod::getId, StudyPeriod::getWeekNrs, orderedStudyPeriods);
            weekNrsCount = studyPeriodWeekNrs.values().stream().mapToInt(List::size).sum();

            // study period offset in week nr list
            int studyPeriodOffset = 0;
            for(StudyPeriod sp : orderedStudyPeriods) {
                Long key = sp.getId();
                studyPeriodOffsets.put(key, Integer.valueOf(studyPeriodOffset));
                studyPeriodOffset += studyPeriodWeekNrs.get(key).size();
            }
            
            for(StudyPeriod sp : orderedStudyPeriods) {
                studyPeriodPos.addAll(Collections.nCopies(studyPeriodWeekNrs.get(sp.getId()).size(), sp.getId()));
            }
            
            studyPeriodsById = StreamUtil.toMap(StudyPeriod::getId, orderedStudyPeriods);
        }

        public Map<String, List<Short>> mapJournalOutput(Journal journal) {
            Map<String, List<Short>> hours = StreamUtil.toMap(jct -> EntityUtil.getCode(jct.getCapacityType()),
                    k -> new ArrayList<>(Collections.nCopies(weekNrsCount, null)), journal.getJournalCapacityTypes());
            // put every JournalCapacity into it's position, determined by capacity type, study period and week nr
            for(JournalCapacity jc : journal.getJournalCapacities()) {
                Long key = EntityUtil.getId(jc.getStudyPeriod());
                Integer offset = studyPeriodOffsets.get(key);
                if(offset != null) {
                    int index = studyPeriodWeekNrs.get(key).indexOf(jc.getWeekNr());
                    if(index != -1) {
                        List<Short> capacityHours = hours.get(EntityUtil.getCode(jc.getJournalCapacityType().getCapacityType()));
                        if(capacityHours != null) {
                            capacityHours.set(index + offset.intValue(), jc.getHours());
                        }
                    }
                }
            }
            return hours;
        }

        public Map<String, List<Short>> mapTeacherOutput(Journal journal, JournalTeacher journalTeacher) {
            Map<String, List<Short>> hours = StreamUtil.toMap(jct -> EntityUtil.getCode(jct.getCapacityType()),
                    k -> new ArrayList<>(Collections.nCopies(weekNrsCount, null)), journal.getJournalCapacityTypes());
            // put every JournalCapacity into it's position, determined by capacity type, study period and week nr
            for(JournalTeacherCapacity jtc : journalTeacher.getJournalTeacherCapacities()) {
                Long key = EntityUtil.getId(jtc.getStudyPeriod());
                Integer offset = studyPeriodOffsets.get(key);
                if(offset != null) {
                    int index = studyPeriodWeekNrs.get(key).indexOf(jtc.getWeekNr());
                    if(index != -1) {
                        List<Short> capacityHours = hours.get(EntityUtil.getCode(jtc.getJournalCapacityType().getCapacityType()));
                        if(capacityHours != null) {
                            capacityHours.set(index + offset.intValue(), jtc.getHours());
                        }
                    }
                }
            }
            return hours;
        }

        public void mapJournalInput(Journal journal, Map<String, List<Short>> hours) {
            Map<String, Map<Long, Map<Short, JournalCapacity>>> existing = journal.getJournalCapacities().stream().collect(
                    Collectors.groupingBy(c -> EntityUtil.getCode(c.getJournalCapacityType().getCapacityType()),
                            Collectors.groupingBy(c -> EntityUtil.getId(c.getStudyPeriod()),
                                    Collectors.toMap(c -> c.getWeekNr(), c -> c))));
            List<JournalCapacity> newCapacities = new ArrayList<>();
            Map<String, JournalCapacityType> JournalCapacityTypes = StreamUtil
                    .toMap(r -> EntityUtil.getCode(r.getCapacityType()), journal.getJournalCapacityTypes());
            for(Map.Entry<String, List<Short>> me : hours.entrySet()) {
                String capacityType = me.getKey();
                Map<Long, Map<Short, JournalCapacity>> existingCapacityTypeHours = existing.get(capacityType);
                List<Short> capacityTypeHours = me.getValue();

                for(int i = 0, cnt = capacityTypeHours.size(); i < cnt; i++) {
                    Short weekNrHours = capacityTypeHours.get(i);
                    if(weekNrHours == null) {
                        // no value, don't store
                        continue;
                    }

                    // map List index into StudyPeriod and weekNr
                    Long studyPeriodId = studyPeriodPos.get(i);
                    Short weekNr = studyPeriodWeekNrs.get(studyPeriodId).get(i - studyPeriodOffsets.get(studyPeriodId).intValue());
                    JournalCapacity journalCapacity = null;
                    if(existingCapacityTypeHours != null) {
                        journalCapacity = existingCapacityTypeHours.computeIfAbsent(studyPeriodId, key -> Collections.emptyMap()).get(weekNr);
                    }
                    if(journalCapacity == null) {
                        journalCapacity = new JournalCapacity();
                        journalCapacity.setJournal(journal);
                        journalCapacity.setStudyPeriod(studyPeriodsById.get(studyPeriodId));
                        journalCapacity.setJournalCapacityType(JournalCapacityTypes.get(capacityType));
                        journalCapacity.setWeekNr(weekNr);
                    }
                    journalCapacity.setHours(weekNrHours);
                    newCapacities.add(journalCapacity);
                }
            }
            List<JournalCapacity> capacities = journal.getJournalCapacities();
            if(capacities != null) {
                capacities.clear();
                capacities.addAll(newCapacities);
            } else {
                journal.setJournalCapacities(newCapacities);
            }
        }

        public void mapTeacherInput(Journal journal, JournalTeacher journalTeacher, Map<String, List<Short>> hours) {
            Map<String, Map<Long, Map<Short, JournalTeacherCapacity>>> existing = journalTeacher
                    .getJournalTeacherCapacities().stream().collect(
                            Collectors.groupingBy(c -> EntityUtil.getCode(c.getJournalCapacityType().getCapacityType()),
                                    Collectors.groupingBy(c -> EntityUtil.getId(c.getStudyPeriod()),
                                            Collectors.toMap(c -> c.getWeekNr(), c -> c))));

            List<JournalTeacherCapacity> newCapacities = new ArrayList<>();
            Map<String, JournalCapacityType> JournalCapacityTypes = StreamUtil
                    .toMap(r -> EntityUtil.getCode(r.getCapacityType()), journal.getJournalCapacityTypes());
            for (Map.Entry<String, List<Short>> me : hours.entrySet()) {
                String capacityType = me.getKey();
                Map<Long, Map<Short, JournalTeacherCapacity>> existingCapacityTypeHours = existing.get(capacityType);
                List<Short> capacityTypeHours = me.getValue();

                for (int i = 0, cnt = capacityTypeHours.size(); i < cnt; i++) {
                    Short weekNrHours = capacityTypeHours.get(i);
                    if (weekNrHours == null) {
                        // no value, don't store
                        continue;
                    }

                    // map List index into StudyPeriod and weekNr
                    Long studyPeriodId = studyPeriodPos.get(i);
                    Short weekNr = studyPeriodWeekNrs.get(studyPeriodId)
                            .get(i - studyPeriodOffsets.get(studyPeriodId).intValue());
                    JournalTeacherCapacity journalTeacherCapacity = null;
                    if (existingCapacityTypeHours != null) {
                        journalTeacherCapacity = existingCapacityTypeHours
                                .computeIfAbsent(studyPeriodId, key -> Collections.emptyMap()).get(weekNr);
                    }
                    if (journalTeacherCapacity == null) {
                        journalTeacherCapacity = new JournalTeacherCapacity();
                        journalTeacherCapacity.setJournalTeacher(journalTeacher);
                        journalTeacherCapacity.setStudyPeriod(studyPeriodsById.get(studyPeriodId));
                        journalTeacherCapacity.setJournalCapacityType(JournalCapacityTypes.get(capacityType));
                        journalTeacherCapacity.setWeekNr(weekNr);
                    }
                    journalTeacherCapacity.setHours(weekNrHours);
                    newCapacities.add(journalTeacherCapacity);
                }
            }
            List<JournalTeacherCapacity> capacities = journalTeacher.getJournalTeacherCapacities();
            if (capacities != null) {
                capacities.clear();
                capacities.addAll(newCapacities);
            } else {
                journalTeacher.setJournalTeacherCapacities(newCapacities);
            }
        }

        public Map<String, List<Long>> mapTeacherLoadsByCapacities(List<LessonPlanTeacherLoadDto> teacherLoads) {
            Set<String> capacities = StreamUtil.toMappedSet(l -> l.getCapacity(), teacherLoads);
            Map<String, List<Long>> weekStudyLoads = new HashMap<>();
            for (String capacity : capacities) {
                List<Long> loads = new ArrayList<>();
                loads.addAll(Collections.nCopies(weekNrsCount, null));
                weekStudyLoads.put(capacity, loads);
            }

            if (teacherLoads != null) {
                for (LessonPlanTeacherLoadDto load : teacherLoads) {
                    Long key = load.getStudyPeriod();
                    Integer offset = studyPeriodOffsets.get(key);
                    if (offset != null) {
                        int index = studyPeriodWeekNrs.get(key).indexOf(load.getWeekNr());
                        if (index != -1) {
                            weekStudyLoads.get(load.getCapacity()).set(index + offset.intValue(), load.getSum());
                        }
                    }
                }
            }
            return weekStudyLoads;
        }
    }

    public static List<WeekDto> weeks(List<Short> weekNrs, List<LocalDate> weekBeginningDates) {
        Map<Short, WeekDto> weekMap = new HashMap<>();
        for (int i = 0; i < weekNrs.size(); i++) {
            Short weekNr = weekNrs.get(i);
            if (!weekMap.containsKey(weekNr)) {
                LocalDate weekStart = weekBeginningDates.get(i);
                LocalDate weekEnd = weekStart.plusDays(6);
                weekMap.put(weekNr, new WeekDto(weekNr, weekStart, weekEnd));
            }
        }
        return new ArrayList<>(weekMap.values());
    }

    public static Map<Short, LocalDate> weekBeginningDateMap(List<Short> weekNrs, List<LocalDate> weekBeginningDates) {
        Map<Short, LocalDate> weekBeginningDateMap = new HashMap<>();
        for (int i = 0; i < weekNrs.size(); i++) {
            Short weekNr = weekNrs.get(i);
            if (!weekBeginningDateMap.containsKey(weekNr)) {
                weekBeginningDateMap.put(weekNr, weekBeginningDates.get(i));
            }
        }
        return weekBeginningDateMap;
    }
}
