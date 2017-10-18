package ee.hitsa.ois.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;

public abstract class SubjectStudyPeriodUtil {
    
    
    public static List<SubjectStudyPeriod> filterSsps(Collection<SubjectStudyPeriod> ssps, Long studyPeriod) {
        return ssps.stream().filter(ssp -> EntityUtil.getId(ssp.getStudyPeriod()).equals(studyPeriod)).collect(Collectors.toList());
    }
    
    public static Long getHours(List<SubjectStudyPeriod> ssps) {
        long sum = 0;
        for (SubjectStudyPeriod ssp : ssps) {
            sum += ssp.getCapacities().stream().mapToLong(SubjectStudyPeriodCapacity::getHours).sum();
        }
        return Long.valueOf(sum);
    }
}
