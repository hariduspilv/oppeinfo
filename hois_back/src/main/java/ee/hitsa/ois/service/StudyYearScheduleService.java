package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.StudyYearSchedule;
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.repository.StudyYearScheduleLegendRepository;
import ee.hitsa.ois.repository.StudyYearScheduleRepository;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.StudyYearScheduleDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@Service
@Transactional
public class StudyYearScheduleService {

    @Autowired
    private StudyYearScheduleRepository studyYearScheduleRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudyYearScheduleLegendRepository studyYearScheduleLegendRepository;
    @Autowired
    private StudyYearRepository studyYearRepository;

    public Set<StudyYearScheduleDto> getSet(Long schoolId, StudyYearScheduleDtoContainer container) {
        return studyYearScheduleRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(root.get("studyPeriod").get("id").in(container.getStudyPeriods()));
            if(!CollectionUtils.isEmpty(container.getStudentGroups())) {
                filters.add(root.get("studentGroup").get("id").in(container.getStudentGroups()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
      }).stream().map(StudyYearScheduleDto::of).collect(Collectors.toSet());
    }

    public List<StudyYearSchedule> update(StudyYearScheduleDtoContainer schedulesCmd, Long schoolId) {

        Set<Long> oldSchedulesDtosIds = schedulesCmd.getStudyYearSchedules().stream()
                .filter(d -> d.getId() != null).map(StudyYearScheduleDto::getId).collect(Collectors.toSet());
        delete(schoolId, schedulesCmd, oldSchedulesDtosIds);

        List<StudyYearScheduleDto> newSchedulesDtos = schedulesCmd.getStudyYearSchedules()
                .stream().filter(s -> s.getId() == null).collect(Collectors.toList());
        return save(schedulesCmd, newSchedulesDtos, schoolId);
    }

    private void delete(Long schoolId, StudyYearScheduleDtoContainer schedulesCmd, Set<Long> oldSchedulesDtosIds) {
        List<StudyYearSchedule> deletedItems = studyYearScheduleRepository.findAll((root, query, cb) -> {
              List<Predicate> filters = new ArrayList<>();
              filters.add(cb.equal(root.get("school").get("id"), schoolId));              
              filters.add(root.get("studyPeriod").get("id").in(schedulesCmd.getStudyPeriods()));
              if(!CollectionUtils.isEmpty(schedulesCmd.getStudentGroups())) {
                  filters.add(root.get("studentGroup").get("id").in(schedulesCmd.getStudentGroups()));
              }
              if(!oldSchedulesDtosIds.isEmpty()) {
                  filters.add(cb.not(root.get("id").in(oldSchedulesDtosIds)));
              }
              return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        studyYearScheduleRepository.delete(deletedItems);
    }

    private List<StudyYearSchedule> save(StudyYearScheduleDtoContainer schedulesCmd, List<StudyYearScheduleDto> newSchedulesDtos, Long schoolId) {
        if(CollectionUtils.isEmpty(newSchedulesDtos)) {
            return new ArrayList<>();
        }

        School school = schoolRepository.getOne(schoolId);
        List<StudyYearSchedule> newSchedules = StreamUtil.toMappedList(dto -> {
            AssertionFailedException.throwIf(!CollectionUtils.isEmpty(schedulesCmd.getStudentGroups()) &&
                    !schedulesCmd.getStudentGroups().contains(dto.getStudentGroup()),
                    "Update command does not contain dto's studentGroup!");
            AssertionFailedException.throwIf(!schedulesCmd.getStudyPeriods().contains(dto.getStudyPeriod()),
                    "Update command does not contain dto's studyPeriod!");

            StudyYearSchedule schedule = getFromDto(dto, school);
            return schedule;
        }, newSchedulesDtos);

        return studyYearScheduleRepository.save(newSchedules);
    }

    private StudyYearSchedule getFromDto(StudyYearScheduleDto dto, School school) {
        StudyYearSchedule schedule = new StudyYearSchedule();
        
        schedule.setSchool(school);
        
        StudyPeriod studyPeriod = studyPeriodRepository.getOne(dto.getStudyPeriod());
        AssertionFailedException.throwIf(!EntityUtil.getId(studyPeriod.getStudyYear().getSchool()).equals(school.getId()),
        "Wrong studyPeriod's school!");
        schedule.setStudyPeriod(studyPeriod);
        
        StudentGroup sg = studentGroupRepository.getOne(dto.getStudentGroup());
        AssertionFailedException.throwIf(!EntityUtil.getId(sg.getSchool()).equals(school.getId()),
        "Wrong studentGroups's school!");
        schedule.setStudentGroup(sg);
        
        StudyYearScheduleLegend legend = studyYearScheduleLegendRepository
                .getOne(dto.getStudyYearScheduleLegend());
        AssertionFailedException.throwIf(!EntityUtil.getId(legend.getSchool()).equals(school.getId()),
        "Wrong legend's school!");
        schedule.setStudyYearScheduleLegend(legend);
        
        schedule.setWeekNr(dto.getWeekNr());
        return schedule;
    }

    public List<StudentGroupSearchDto> getStudentGroups(Long schoolId) {
        List<StudentGroup> data = studentGroupRepository.findAll((root, query, cb) -> cb.equal(root.get("school").get("id"), schoolId));
        return StreamUtil.toMappedList(sg -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(sg.getId());
            dto.setCode(sg.getCode());
            dto.setSchoolDepartments(StreamUtil.toMappedList(d -> EntityUtil.getId(d.getSchoolDepartment()), sg.getCurriculum().getDepartments()));
            return dto;
        }, data.stream().filter(sg -> !CollectionUtils.isEmpty(sg.getCurriculum().getDepartments())));
    }

    public List<StudyYearDto> getStudyYearsWithStudyPeriods(Long schoolId) {
        return StreamUtil.toMappedList(sy -> {
            StudyYearDto dto = StudyYearDto.of(sy);
            dto.getStudyPeriodEvents().clear();
            return dto;
        }, studyYearRepository.findAll((root, query, cb) -> cb.equal(root.get("school").get("id"), schoolId)));
    }
}
