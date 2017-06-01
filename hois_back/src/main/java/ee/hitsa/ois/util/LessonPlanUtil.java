package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalCapacityType;

public class LessonPlanUtil {

    public static LessonPlanCapacityMapper capacityMapper(StudyYear studyYear) {
        return new LessonPlanCapacityMapper(studyYear);
    }

    public static class LessonPlanCapacityMapper {
        private final List<StudyPeriod> orderedStudyPeriods;
        private final Map<Long, List<Integer>> studyPeriodWeekNrs;
        private final Map<Long, Integer> studyPeriodOffsets = new HashMap<>();
        private final int weekNrsCount;

        LessonPlanCapacityMapper(StudyYear studyYear) {
            orderedStudyPeriods = studyYear.getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate)).collect(Collectors.toList());
            studyPeriodWeekNrs = orderedStudyPeriods.stream().collect(Collectors.toMap(StudyPeriod::getId, StudyPeriod::getWeekNrs));
            weekNrsCount = studyPeriodWeekNrs.values().stream().mapToInt(List::size).sum();

            // study period offset in week nr list
            int studyPeriodOffset = 0;
            for(StudyPeriod sp : orderedStudyPeriods) {
                Long key = sp.getId();
                studyPeriodOffsets.put(key, Integer.valueOf(studyPeriodOffset));
                studyPeriodOffset += studyPeriodWeekNrs.get(key).size();
            }
        }

        public Map<String, List<Integer>> mapOutput(Journal journal) {
            Map<String, List<Integer>> hours = journal.getJournalCapacityTypes().stream().collect(Collectors.toMap(jct -> EntityUtil.getCode(jct.getCapacityType()), k -> new ArrayList<>(Collections.nCopies(weekNrsCount, null))));
            // put every JournalCapacity into it's position, determined by capacity type, study period and week nr
            for(JournalCapacity jc : journal.getJournalCapacities()) {
                Long key = EntityUtil.getId(jc.getStudyPeriod());
                Integer offset = studyPeriodOffsets.get(key);
                if(offset != null) {
                    int index = studyPeriodWeekNrs.get(key).indexOf(jc.getWeekNr());
                    if(index != -1) {
                        List<Integer> capacityHours = hours.get(EntityUtil.getCode(jc.getJournalCapacityType().getCapacityType()));
                        if(capacityHours != null) {
                            capacityHours.set(index + offset.intValue(), jc.getHours());
                        }
                    }
                }
            }
            return hours;
        }

        public void mapInput(Journal journal, Map<String, List<Integer>> hours) {
            Map<String, Map<Long, Map<Integer, JournalCapacity>>> existing = journal.getJournalCapacities().stream().collect(
                    Collectors.groupingBy(c -> EntityUtil.getCode(c.getJournalCapacityType().getCapacityType()),
                            Collectors.groupingBy(c -> EntityUtil.getId(c.getStudyPeriod()),
                                    Collectors.toMap(c -> c.getWeekNr(), c -> c))));
            List<JournalCapacity> newCapacities = new ArrayList<>();
            List<Long> studyPeriodPos = new ArrayList<>();
            for(StudyPeriod sp : orderedStudyPeriods) {
                studyPeriodPos.addAll(Collections.nCopies(studyPeriodWeekNrs.get(sp.getId()).size(), sp.getId()));
            }
            Map<Long, StudyPeriod> studyPeriodsById = StreamUtil.toMap(StudyPeriod::getId, orderedStudyPeriods);
            Map<String, JournalCapacityType> JournalCapacityTypes = StreamUtil.toMap(r -> EntityUtil.getCode(r.getCapacityType()), journal.getJournalCapacityTypes());
            for(Map.Entry<String, List<Integer>> me : hours.entrySet()) {
                String capacityType = me.getKey();
                Map<Long, Map<Integer, JournalCapacity>> existingCapacityTypeHours = existing.get(capacityType);
                List<Integer> capacityTypeHours = me.getValue();

                for(int i = 0, cnt = capacityTypeHours.size(); i < cnt; i++) {
                    Integer weekNrHours = capacityTypeHours.get(i);
                    if(weekNrHours == null) {
                        // no value, don't store
                        continue;
                    }

                    // map List index into StudyPeriod and weekNr
                    Long studyPeriodId = studyPeriodPos.get(i);
                    Integer weekNr = studyPeriodWeekNrs.get(studyPeriodId).get(i - studyPeriodOffsets.get(studyPeriodId).intValue());
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
    }
}
