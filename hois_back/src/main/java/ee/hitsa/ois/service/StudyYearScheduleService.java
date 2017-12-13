package ee.hitsa.ois.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.StudyYearSchedule;
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.StudyYearScheduleRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.StudyYearScheduleDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@Service
@Transactional
public class StudyYearScheduleService {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearScheduleRepository studyYearScheduleRepository;

    public Set<StudyYearScheduleDto> getSet(Long schoolId, StudyYearScheduleDtoContainer schedulesCmd) {
        JpaQueryBuilder<StudyYearSchedule> qb = new JpaQueryBuilder<>(StudyYearSchedule.class, "sys");

        qb.requiredCriteria("sys.school.id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sys.studyPeriod.id in (:studyPeriodIds)", "studyPeriodIds", schedulesCmd.getStudyPeriods());
        qb.optionalCriteria("sys.studentGroup.id in (:studentGroupIds)", "studentGroupIds", schedulesCmd.getStudentGroups());

        return StreamUtil.toMappedSet(StudyYearScheduleDto::of, qb.select(em).getResultList());
    }

    public void update(Long schoolId, StudyYearScheduleDtoContainer schedulesCmd) {
        Set<Long> oldSchedulesDtosIds = schedulesCmd.getStudyYearSchedules().stream()
                .filter(d -> d.getId() != null).map(StudyYearScheduleDto::getId).collect(Collectors.toSet());
        delete(schoolId, schedulesCmd, oldSchedulesDtosIds);

        List<StudyYearScheduleDto> newSchedulesDtos = schedulesCmd.getStudyYearSchedules()
                .stream().filter(s -> s.getId() == null).collect(Collectors.toList());

        if(!newSchedulesDtos.isEmpty()) {
            School school = em.getReference(School.class, schoolId);
            List<StudyYearSchedule> newSchedules = StreamUtil.toMappedList(dto -> {
                AssertionFailedException.throwIf(!CollectionUtils.isEmpty(schedulesCmd.getStudentGroups()) &&
                        !schedulesCmd.getStudentGroups().contains(dto.getStudentGroup()),
                        "Update command does not contain dto's studentGroup!");
                AssertionFailedException.throwIf(!schedulesCmd.getStudyPeriods().contains(dto.getStudyPeriod()),
                        "Update command does not contain dto's studyPeriod!");

                StudyYearSchedule schedule = getFromDto(dto, school);
                return schedule;
            }, newSchedulesDtos);

            studyYearScheduleRepository.save(newSchedules);
        }
    }

    private void delete(Long schoolId, StudyYearScheduleDtoContainer schedulesCmd, Set<Long> oldSchedulesDtosIds) {
        JpaQueryBuilder<StudyYearSchedule> qb = new JpaQueryBuilder<>(StudyYearSchedule.class, "sys");

        qb.requiredCriteria("sys.school.id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sys.studyPeriod.id in (:studyPeriodIds)", "studyPeriodIds", schedulesCmd.getStudyPeriods());
        qb.optionalCriteria("sys.studentGroup.id in (:studentGroupIds)", "studentGroupIds", schedulesCmd.getStudentGroups());
        qb.optionalCriteria("sys.id not in (:oldScheduleIds)", "oldScheduleIds", oldSchedulesDtosIds);

        List<StudyYearSchedule> deletedItems = qb.select(em).getResultList();
        studyYearScheduleRepository.delete(deletedItems);
    }

    private StudyYearSchedule getFromDto(StudyYearScheduleDto dto, School school) {
        StudyYearSchedule schedule = new StudyYearSchedule();
        schedule.setSchool(school);

        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, dto.getStudyPeriod());
        AssertionFailedException.throwIf(!EntityUtil.getId(studyPeriod.getStudyYear().getSchool()).equals(school.getId()),
        "Wrong studyPeriod's school!");
        schedule.setStudyPeriod(studyPeriod);

        StudentGroup sg = em.getReference(StudentGroup.class, dto.getStudentGroup());
        AssertionFailedException.throwIf(!EntityUtil.getId(sg.getSchool()).equals(school.getId()),
        "Wrong studentGroups's school!");
        schedule.setStudentGroup(sg);

        StudyYearScheduleLegend legend = em.getReference(StudyYearScheduleLegend.class, dto.getStudyYearScheduleLegend());
        AssertionFailedException.throwIf(!EntityUtil.getId(legend.getSchool()).equals(school.getId()),
        "Wrong legend's school!");
        schedule.setStudyYearScheduleLegend(legend);

        schedule.setWeekNr(dto.getWeekNr());
        schedule.setAddInfo(dto.getAddInfo());
        return schedule;
    }

    public List<StudentGroupSearchDto> getStudentGroups(Long schoolId) {
        List<StudentGroup> data = em.createQuery("select sg from StudentGroup sg where sg.school.id = ?1", StudentGroup.class)
                .setParameter(1, schoolId).getResultList();
        return StreamUtil.toMappedList(sg -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(sg.getId());
            dto.setCode(sg.getCode());
            dto.setSchoolDepartments(StreamUtil.toMappedList(d -> EntityUtil.getId(d.getSchoolDepartment()), sg.getCurriculum().getDepartments()));
            return dto;
        }, data.stream().filter(sg -> !sg.getCurriculum().getDepartments().isEmpty()));
    }

    public List<StudyYearDto> getStudyYearsWithStudyPeriods(Long schoolId) {
        List<StudyYear> data = em.createQuery("select sy from StudyYear sy where sy.school.id = ?1", StudyYear.class)
                .setParameter(1, schoolId).getResultList();
        return StreamUtil.toMappedList(sy -> {
            StudyYearDto dto = StudyYearDto.of(sy);
            dto.getStudyPeriodEvents().clear();
            return dto;
        }, data);
    }
}
