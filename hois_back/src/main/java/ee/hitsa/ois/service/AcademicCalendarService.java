package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;
import ee.hitsa.ois.web.dto.AcademicCalendarEventDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;

@Transactional
@Service
public class AcademicCalendarService {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearService studyYearService;

    private List<AcademicCalendarEventDto> getAcademicCalendarEvents(Long studyYearId, Long schoolId) {
        Query q = em.createNativeQuery("select syndmus.start as start_date, syndmus.end as end_date, name_et, name_en, event_type, event_type_code "
                + " from ( select spe.start, spe.end, spe.description_et as name_et, spe.description_en as name_en, null as event_type, spe.event_type_code"
                + " from study_year sy join study_period_event spe on sy.id = spe.study_year_id join classifier cl on spe.event_type_code = cl.code "
                + " where sy.id =?1 and sy.school_id =?2"
                + " union"
                + " select sp.start_date, null, sp.name_et, sp.name_en, 1 as event_type, null "
                + " from study_year sy join study_period sp on sy.id = sp.study_year_id where sy.id =?1 and sy.school_id =?2"
                + " union "
                + " select sp.end_date, null, sp.name_et, sp.name_en, 2 as event_type, null"
                + " from study_year sy join study_period sp on sy.id = sp.study_year_id where sy.id =?1 and sy.school_id =?2 ) syndmus"
                + " order by syndmus.start, syndmus.end, name_et");
        q.setParameter(1, studyYearId);
        q.setParameter(2, schoolId);

        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new AcademicCalendarEventDto((Object[])r), data);
    }

    /**
     * Get academic calendar for view.
     * @param schoolId
     * @return study year code and academic calendar events
     */
    public AcademicCalendarDto academicCalendar(Long schoolId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear == null) {
            studyYear = studyYearService.getNextStudyYear(schoolId);
            if (studyYear == null) {
                return null;
            }
        }
        return academicCalendar(schoolId, studyYear);
    }
    
    /**
     * Get academic calendar for view.
     * @param schoolId
     * @param studyYear
     * @return study year code and academic calendar events
     */
    public AcademicCalendarDto academicCalendar(Long schoolId, StudyYear studyYear) {
        School school = em.getReference(School.class, schoolId);
        AutocompleteResult schoolName = new AutocompleteResult(schoolId, school.getNameEt(), school.getNameEn());
        String yearCode = EntityUtil.getCode(studyYear.getYear());
        List<AcademicCalendarEventDto> events = getAcademicCalendarEvents(studyYear.getId(), schoolId);
        return new AcademicCalendarDto(schoolName, yearCode, events);
    }
    
    public List<StudyYearSearchDto> studyYears(Long schoolId) {
        List<?> data = em.createNativeQuery("select c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, 0 as count from"
                + " (select id from study_year sy where sy.school_id = :schoolId and"
                + " ((sy.start_date <= :now and sy.end_date >= :now) or (sy.start_date - interval '2 month') <= :now and sy.end_date >= :now)"
                + " union select first_value(id) over(order by end_date desc) from study_year where school_id = :schoolId and end_date < :now "
                + " union select first_value(id) over(order by end_date asc) from study_year where school_id = :schoolId and end_date > :now and start_date >:now) x"
                + " join study_year sy on sy.id = x.id"
                + " join classifier c on c.code = sy.year_code "
                + " where (exists(select sp.id from study_period sp where sp.study_year_id = sy.id) "
                + " or exists(select spe.id from study_period_event spe where spe.study_year_id = sy.id))"
                + " order by sy.start_date asc")
                .setParameter("schoolId", schoolId)
                .setParameter("now", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .getResultList();

        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }
}
