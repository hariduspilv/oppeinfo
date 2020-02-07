package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.repository.SubjectStudyPeriodPlanRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.CurriculumProgramDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodStudentGroupService {

    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodPlanRepository subjectStudyPeriodPlanRepository;
    @Autowired
    private SubjectStudyPeriodCapacitiesService subjectStudyPeriodCapacitiesService;
    @Autowired
    private XlsService xlsService;

    public void setSubjectStudyPeriodsToStudentGroupsContainer(Long schoolId,
            SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));
            filters.add(cb.equal(root.get("studyPeriod").get("studyYear").get("school").get("id"), schoolId));

            Subquery<Long> studentGroupSubquery = query.subquery(Long.class);
            Root<SubjectStudyPeriodStudentGroup> targetRoot = studentGroupSubquery
                    .from(SubjectStudyPeriodStudentGroup.class);
            studentGroupSubquery = studentGroupSubquery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                    .where(cb.equal(targetRoot.get("studentGroup").get("id"), container.getStudentGroup()));
            filters.add(root.get("id").in(studentGroupSubquery));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        Map<Long, Map<Long, Short>> teacherPlannedLoads = subjectStudyPeriodCapacitiesService
                .subjectStudyPeriodTeacherPlannedLoads(ssps);

        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            Map<Long, Short> spPlannedLoads = teacherPlannedLoads.get(EntityUtil.getId(ssp.getStudyPeriod()));
            dto.setTeachers(StreamUtil.toMappedList(
                    t -> SubjectStudyPeriodTeacherDto.of(t,
                            spPlannedLoads != null ? spPlannedLoads.get(EntityUtil.getId(t.getTeacher())) : null),
                    ssp.getTeachers()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            dto.setCapacityDiff(ssp.getCapacityDiff());
            return dto;
        }, ssps);
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodDtos);
    }
    
    public void setSubjectStudyPeriodPlansToStudentGroupContainer(SubjectStudyPeriodDtoContainer container) {
        StudentGroup sg = em.getReference(StudentGroup.class, container.getStudentGroup());

        List<SubjectStudyPeriodPlan> plans = subjectStudyPeriodPlanRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));

            List<Long> subjectIds = StreamUtil.toMappedList(s -> s.getSubject(), container.getSubjectStudyPeriodDtos());
            if (!subjectIds.isEmpty()) {
                filters.add(root.get("subject").get("id").in(subjectIds));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        /*
         * Only those subjectStudyPeriodPlans should be considered, which are
         * valid within curriculum/studyForm of specific studentGroup.
         * 
         * In case subjectStudyPeriodPlan have no curriculum / studyForm, it is
         * considered to be valid in all curriculums / studyForms
         * 
         * TODO: do in with CriteriaBuilder!
         */
        plans = plans.stream().filter(p -> {
            List<Long> curriculums = StreamUtil.toMappedList(c -> EntityUtil.getId(c.getCurriculum()), p.getCurriculums());
            return curriculums.isEmpty() || curriculums.contains(EntityUtil.getId(sg.getCurriculum()));
        }).filter(p -> {
            List<String> studyForms = StreamUtil.toMappedList(sf -> EntityUtil.getCode(sf.getStudyForm()), p.getStudyForms());
            return studyForms.isEmpty() || studyForms.contains(EntityUtil.getCode(sg.getStudyForm()));
        }).collect(Collectors.toList());

        container.setSubjectStudyPeriodPlans(StreamUtil.toMappedList(plan -> {
            SubjectStudyPeriodPlanDto dto = new SubjectStudyPeriodPlanDto();
            dto.setId(EntityUtil.getId(plan));
            dto.setSubject(EntityUtil.getId(plan.getSubject()));
            dto.setCapacities(StreamUtil.toMappedSet(SubjectStudyPeriodPlanCapacityDto::of, plan.getCapacities()));
            return dto;
        }, plans));
    }
    
    public List<StudentGroupSearchDto> getStudentGroupsList(Long schoolId, Long studyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_group sg join curriculum c on c.id = sg.curriculum_id "
                + "left join curriculum_version cv on cv.id = sg.curriculum_version_id ");

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("c.is_higher = true");
        qb.filter("(sg.valid_from is null or sg.valid_from <= current_date)");
        qb.filter("(sg.valid_thru is null or sg.valid_thru >= current_date)");

        qb.optionalCriteria("not exists " 
                        + "(select * from subject_study_period_student_group ssp_sg "
                        + "join subject_study_period ssp on ssp.id = ssp_sg.subject_study_period_id "
                        + "where ssp.study_period_id = :studyPeriodId " + "and ssp_sg.student_group_id = sg.id )",
                          "studyPeriodId", studyPeriodId);

        qb.sort("sg.code");
        List<?> data = qb.select("sg.id, sg.code, sg.course, c.id as curricId, cv.admission_year", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setCourse(resultAsInteger(r, 2));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 3), null, null));
            dto.setCurriculumVersionAdmissinYear(resultAsShort(r, 4));
            return dto;
        }, data);
    }

    public List<CurriculumSearchDto> getCurricula(Long schoolId) {
        List<Curriculum> curriculums = em.createQuery(
                "select c from Curriculum c where c.school.id = ?1 and c.status.code = ?2 and c.higher = true", Curriculum.class)
            .setParameter(1, schoolId)
            .setParameter(2, CurriculumStatus.OPPEKAVA_STAATUS_K.name())
            .getResultList();

        return StreamUtil.toMappedList(c -> {
            CurriculumSearchDto dto = new CurriculumSearchDto();
            dto.setId(c.getId());
            dto.setNameEt(c.getNameEt());
            dto.setNameEn(c.getNameEn());
            dto.setCode(c.getCode());
            dto.setDepartments(StreamUtil.toMappedList(d -> EntityUtil.getId(d.getSchoolDepartment()), c.getDepartments()));
            return dto;
        }, curriculums);
    }

    public byte[] subjectStudyPeriodStudentGroupAsExcel(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        setSubjectStudyPeriodsToStudentGroupsContainer(schoolId, container);
        setSubjectStudyPeriodPlansToStudentGroupContainer(container);
        subjectStudyPeriodCapacitiesService.setSubjects(container);

        List<Classifier> capacities = subjectStudyPeriodCapacitiesService.capacityClassifiers(schoolId, container);
        List<String> capacityCodes = StreamUtil.toMappedList(c -> EntityUtil.getCode(c), capacities);

        List<Map<String, Object>> subjects = new ArrayList<>();
        for (AutocompleteResult s : container.getSubjects()) {
            subjects.add(excelSubject(s, container, capacityCodes));
        }

        Map<String, Object> data = new HashMap<>();
        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, container.getStudyPeriod());
        StudentGroup studentGroup = em.getReference(StudentGroup.class, container.getStudentGroup());
        Curriculum curriculum = studentGroup.getCurriculum();

        data.put("studyYear", AutocompleteResult.of(studyPeriod.getStudyYear()));
        data.put("studyPeriod", AutocompleteResult.of(studyPeriod));
        data.put("studentGroup", AutocompleteResult.of(studentGroup));
        data.put("course", studentGroup.getCourse());
        data.put("curriculum", AutocompleteResult.of(curriculum));
        data.put("studyPeriodYears", Integer.valueOf(curriculum.getStudyPeriod().intValue() / 12));
        data.put("studyPeriodMonths", Integer.valueOf(curriculum.getStudyPeriod().intValue() % 12));
        data.put("capacities", capacities);
        data.put("subjects", subjects);
        data.put("totals", subjectStudyPeriodCapacitiesService.subjectPeriodTotals(subjects, capacityCodes));

        return xlsService.generate("subjectstudyperiodstudentgroup.xls", data);
    }

    private Map<String, Object> excelSubject(AutocompleteResult subjectDto, SubjectStudyPeriodDtoContainer container,
            List<String> capacityCodes) {
        Map<String, Object> subject = new HashMap<>();
        Map<String, Short> subjectCapacityHours = subjectStudyPeriodCapacitiesService
                .subjectCapacityHours(subjectDto.getId(), container, capacityCodes);

        List<Map<String, Object>> periods = new ArrayList<>();
        Map<String, Short> periodTotals = subjectStudyPeriodCapacitiesService.emptyOrderedCapacityHours(capacityCodes);

        List<SubjectStudyPeriodDto> periodDtos = StreamUtil.toFilteredList(
                sp -> sp.getSubject().equals(subjectDto.getId()), container.getSubjectStudyPeriodDtos());
        for (SubjectStudyPeriodDto periodDto : periodDtos) {
            Map<String, Object> period = subjectStudyPeriodCapacitiesService.periodExcel(periodDto, periodTotals,
                    capacityCodes);
            String name = periodDto.getTeachers().stream().map(t -> t.getName()).collect(Collectors.joining(", "));
            period.put("studentGroups", new AutocompleteResult(null, name, name));

            periods.add(period);
        }

        subject.put("subject", subjectDto);
        subject.put("hours", subjectCapacityHours);
        subject.put("subjectPeriods", periods);
        subject.put("totals", periodTotals);
        return subject;
    }

    public Map<Short, List<CurriculumProgramDto>> getCurriculumProgram(StudentGroup group, StudyPeriod period) {
        CurriculumVersion cv = group.getCurriculumVersion();
        if (cv != null) {
            Map<Short, List<CurriculumProgramDto>> mappedSubjects = new HashMap<>();
            cv.getModules().stream().flatMap(mod -> mod.getSubjects().stream())
                .filter(modSubject -> modSubject.getStudyYearNumber() != null)
                .forEach(modSubject -> {
                    short semester = (short) ((modSubject.getStudyYearNumber().intValue() - 1) * 2);
                    Subject subject = modSubject.getSubject();
                    
                    CurriculumProgramDto dto = new CurriculumProgramDto();
                    
                    boolean used = false;
                    semester++;
                    if (Boolean.TRUE.equals(modSubject.getAutumn())) {
                        Short autumnSemester = Short.valueOf(semester);
                        if (!mappedSubjects.containsKey(autumnSemester)) {
                            mappedSubjects.put(autumnSemester, new ArrayList<>());
                        }
                        mappedSubjects.get(autumnSemester).add(dto);
                        used = true;
                    }
                    semester++;
                    if (Boolean.TRUE.equals(modSubject.getSpring())) {
                        Short springSemester = Short.valueOf(semester);
                        if (!mappedSubjects.containsKey(springSemester)) {
                            mappedSubjects.put(springSemester, new ArrayList<>());
                        }
                        mappedSubjects.get(springSemester).add(dto);
                        used = true;
                    }
                    
                    // No need to get and check other items as we are not adding it
                    if (!used) {
                        return;
                    }
                    
                    dto.setCode(subject.getCode());
                    dto.setSubject(new AutocompleteResult(subject.getId(), subject));
                    dto.setCredits(subject.getCredits());
                    
                    boolean present = subject.getSubjectStudyPeriods().stream()
                        // We look at ssp only before or in this period.
                        .filter(ssp -> ssp.getStudyPeriod().getStartDate().compareTo(period.getStartDate()) < 1)
                        .flatMap(ssp -> ssp.getStudentGroups().stream())
                        .map(SubjectStudyPeriodStudentGroup::getStudentGroup)
                        // group should be the same
                        .filter(g -> group.getId().equals(EntityUtil.getId(g)))
                        .findAny().isPresent();
                    
                    dto.setAlreadyExistsForGroup(Boolean.valueOf(present));
                    
                    // Set subject study periods which can be connected to this group
                    dto.setSubjectStudyPeriods(subject.getSubjectStudyPeriods().stream()
                        .filter(ssp -> ssp.getStudyPeriod().equals(period))
                        .map(ssp -> {
                            List<String> teachers = ssp.getTeachers().stream()
                                    .map(sspt -> PersonUtil.fullname(sspt.getTeacher().getPerson()))
                                    .collect(Collectors.toList());
                            List<String> groups = ssp.getStudentGroups().stream()
                                    .map(sspg -> sspg.getStudentGroup().getCode())
                                    .collect(Collectors.toList());
                            StringBuilder nameBuilder = new StringBuilder(String.join(", ", teachers));
                            if (!groups.isEmpty()) {
                                if (nameBuilder.length() > 0) {
                                    nameBuilder.append(" ");
                                }
                                nameBuilder.append("(");
                                nameBuilder.append(String.join(", ", groups));
                                nameBuilder.append(")");
                            }
                            String name = nameBuilder.toString();
                            return new AutocompleteResult(ssp.getId(), name, name);
                        })
                        .collect(Collectors.toList()));

                    Optional<SubjectStudyPeriodPlan> optPlan = subject.getSubjectStudyPeriodPlans().stream().filter(sspp -> sspp.getStudyPeriod().equals(period)).findAny();
                    if (optPlan.isPresent()) {
                        dto.setPlan(SubjectStudyPeriodPlanDto.of(optPlan.get()));
                    }
                });
            return mappedSubjects;
        }
        return Collections.emptyMap();
    }

    public void connect(SubjectStudyPeriod ssp, StudentGroup group) {
        SubjectStudyPeriodStudentGroup entity = new SubjectStudyPeriodStudentGroup();
        entity.setSubjectStudyPeriod(ssp);
        entity.setStudentGroup(group);
        EntityUtil.save(entity, em);
    }

}
