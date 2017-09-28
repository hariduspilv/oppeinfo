package ee.hitsa.ois.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;
import ee.hitsa.ois.web.dto.AcademicCalendarEventDto;

@Transactional
@Service
public class AcademicCalendarService {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearService studyYearService;

    private List<AcademicCalendarEventDto> getAcademicCalendarEvents(Long studyYearId, Long schoolId) {
        Query q = em.createNativeQuery("select syndmus.start as start_date, syndmus.end as end_date, description_et, description_en, event_type "
                + " from ( select spe.start, spe.end, cl.name_et as description_et, cl.name_en as description_en, null as event_type from study_year sy "
                + " join study_period_event spe on sy.id = spe.study_year_id join classifier cl on spe.event_type_code = cl.code "
                + " where sy.id =?1 and sy.school_id =?2"
                + " union"
                + " select sp.start_date, null, sp.name_et, sp.name_en, 1 as event_type "
                + " from study_year sy join study_period sp on sy.id = sp.study_year_id where sy.id =?1 and sy.school_id =?2"
                + " union "
                + " select sp.end_date, null, sp.name_et, sp.name_en, 2 as event_type"
                + " from study_year sy join study_period sp on sy.id = sp.study_year_id where sy.id =?1 and sy.school_id =?2 ) syndmus"
                + " order by syndmus.start, syndmus.end, description_et");
        q.setParameter(1, studyYearId);
        q.setParameter(2, schoolId);

        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new AcademicCalendarEventDto((Object[])r), data);
    }

    public AcademicCalendarDto getAcademicCalendar(Long schoolId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear != null) {
            String yearCode = EntityUtil.getCode(studyYear.getYear());
            List<AcademicCalendarEventDto> events = getAcademicCalendarEvents(studyYear.getId(), schoolId);
            return new AcademicCalendarDto(yearCode, events);
        }
        return null;
    }
}
