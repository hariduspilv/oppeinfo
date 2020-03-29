package ee.hitsa.ois.service.timetable;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.timetable.TimetableImportDto;

public abstract class AbstractTimetableImport<DataType, ItemType, ReturnType> {

    protected abstract EntityManager getEntityManager();

    protected abstract HoisUserDetails getUser();
    
    protected abstract void init(TimetableImportDto dto, DataType data);

    private final void deleteExistingData(TimetableImportDto dto) {
        EntityUtil.setUsername(getUser().getUsername(), getEntityManager());
        deleteExistingDataInternal(dto);
    }
    
    private final void deleteExistingDataInternal(TimetableImportDto dto) {
        // Delete UNTIS data
        try {
            Timetable timetable = getEntityManager().createQuery("select tt from Timetable tt "
                    + "where tt.school.id = ?1 "
                    + "and tt.studyPeriod.id = ?2 "
                    + "and tt.startDate = ?3 "
                    + "and tt.endDate = ?4 " 
                    + "and tt.isHigher = ?5", Timetable.class)
                    .setParameter(1, getUser().getSchoolId())
                    .setParameter(2, dto.getStudyPeriod())
                    .setParameter(3, dto.getStartDate())
                    .setParameter(4, dto.getEndDate())
                    .setParameter(5, dto.getIsHigher())
                    .getSingleResult();
            // Delete existing imported timetable events and remove them from java object
            Iterator<TimetableObject> timetableIterator = timetable.getTimetableObjects().iterator();
            while (timetableIterator.hasNext()) {
                TimetableObject timetableObject = timetableIterator.next();
                Iterator<TimetableEvent> timetableEventIterator = timetableObject.getTimetableEvents().iterator();
                while (timetableEventIterator.hasNext()) {
                    TimetableEvent timetableEvent = timetableEventIterator.next();
                    if (timetableEvent.getIsImported() != null && timetableEvent.getIsImported().booleanValue()) {
                        EntityUtil.deleteEntity(timetableEvent, getEntityManager());
                        timetableEventIterator.remove();
                    }
                }
                if (timetableObject.getTimetableEvents().isEmpty()) {
                    timetableIterator.remove();
                }
            }
            EntityUtil.save(timetable, getEntityManager());
        } catch (@SuppressWarnings("unused") NoResultException nre) {
            // If there is no timetable then there is nothing to delete
        }
        // Delete ASC data
        List<TimetableEvent> importedEvents = getEntityManager().createQuery(
                "select tte from TimetableEvent tte where tte.isImported = true and tte.start between ?1 and ?2 and tte.school.id = ?3",
                TimetableEvent.class).setParameter(1, dto.getStartDate().atTime(LocalTime.MIN))
                .setParameter(2, dto.getEndDate().atTime(LocalTime.MAX)).setParameter(3, getUser().getSchoolId())
                .getResultList();

        importedEvents.forEach(e -> EntityUtil.deleteEntity(e, getEntityManager()));
    }

    protected abstract Set<ReturnType> processDataCore(School school, TimetableImportDto dto,
            DataType data);

    public final Set<ReturnType> processData(School school, TimetableImportDto dto, DataType data) {
        init(dto, data);
        deleteExistingData(dto);
        return processDataCore(school, dto, data);
    }

    protected abstract void processItem(School school, TimetableImportDto dto, ItemType item);
}
