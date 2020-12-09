package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.getOrDefault;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacherCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.enums.Coefficient;
import ee.hitsa.ois.enums.GroupProportion;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.SubjectStudyPeriodPlanService;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSubgroupDto;
import org.springframework.beans.BeanUtils;
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
        Map<Long, Map<Long, Integer>> teacherPlannedLoads = subjectStudyPeriodCapacitiesService
                .subjectStudyPeriodTeacherPlannedLoads(ssps);

        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            Map<Long, Integer> spPlannedLoads = teacherPlannedLoads.get(EntityUtil.getId(ssp.getStudyPeriod()));
            dto.setTeachers(StreamUtil.toMappedList(
                    t -> SubjectStudyPeriodTeacherDto.of(t,
                            spPlannedLoads != null ? spPlannedLoads.get(EntityUtil.getId(t.getTeacher())) : null),
                    ssp.getTeachers()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            dto.setCapacityDiff(ssp.getCapacityDiff());
            dto.setStudentGroupObjects(ssp.getStudentGroups().stream()
                    .map(SubjectStudyPeriodStudentGroup::getStudentGroup)
                    .sorted(Comparator.comparing(StudentGroup::getCode))
                    .map(AutocompleteResult::of)
                    .collect(Collectors.toList()));
            dto.setCoefficient(getOrDefault(EntityUtil.getNullableCode(ssp.getCoefficient()),
                    Coefficient.KOEFITSIENT_K1.name()));
            dto.setSubgroups(StreamUtil.toMappedSet(SubjectStudyPeriodSubgroupDto::of, ssp.getSubgroups()));
            return dto;
        }, ssps);
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodDtos);
    }
    
    public void setSubjectStudyPeriodPlansToStudentGroupContainer(SubjectStudyPeriodDtoContainer container) {
        if (container.getSubjectStudyPeriodDtos().isEmpty()) {
            container.setSubjectStudyPeriodPlans(Collections.emptyList());
            return;
        }
        List<?> result = em.createNativeQuery("select sspp.plan_id, ssp.id" +
                " from subject_study_period ssp" +
                " join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP +
                ") sspp on ssp.id = sspp.ssp_id and sspp.priority != 999" +
                " where ssp.id in :sspIds" +
                " order by ssp.id")
                .setParameter("sspIds", StreamUtil.toMappedList(SubjectStudyPeriodDto::getId,
                        container.getSubjectStudyPeriodDtos()))
                .getResultList();
        Map<Long, Long> planIds = result.stream()
                .collect(Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsLong(r, 0),
                        (o, n) -> o, LinkedHashMap::new));

        if (!planIds.isEmpty()) {
            em.createQuery("select sspp from SubjectStudyPeriodPlan sspp" +
                    " left join fetch sspp.capacities" +
                    " where sspp.id in :planIds", SubjectStudyPeriodPlan.class)
                    .setParameter("planIds", planIds.values())
                    .getResultList();
        }

        container.setSubjectStudyPeriodPlans(planIds.entrySet().stream().map(planId -> {
            SubjectStudyPeriodPlan plan = em.getReference(SubjectStudyPeriodPlan.class, planId.getValue());
            SubjectStudyPeriodPlanDto dto = new SubjectStudyPeriodPlanDto();
            dto.setId(EntityUtil.getId(plan));
            dto.setSubject(EntityUtil.getId(plan.getSubject()));
            dto.setCapacities(StreamUtil.toMappedSet(SubjectStudyPeriodPlanCapacityDto::of, plan.getCapacities()));

            dto.setSspId(planId.getKey());
            return dto;
        }).collect(Collectors.toList()));
    }

    public List<StudentGroupSearchDto> getStudentGroupsList(Long schoolId, Long studyPeriodId) {
        return getStudentGroupsList(schoolId, studyPeriodId, null);
    }
    
    public List<StudentGroupSearchDto> getStudentGroupsList(Long schoolId, Long studyPeriodId, String name) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_group sg join curriculum c on c.id = sg.curriculum_id "
                + "left join curriculum_version cv on cv.id = sg.curriculum_version_id ");

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("c.is_higher = true");
        qb.filter("(sg.valid_from is null or sg.valid_from <= current_date)");
        qb.filter("(sg.valid_thru is null or sg.valid_thru >= current_date)");
        qb.optionalContains("sg.code", "codeName", name);

        qb.optionalCriteria("not exists " 
                        + "(select * from subject_study_period_student_group ssp_sg "
                        + "join subject_study_period ssp on ssp.id = ssp_sg.subject_study_period_id "
                        + "where ssp.study_period_id = :studyPeriodId " + "and ssp_sg.student_group_id = sg.id )",
                          "studyPeriodId", studyPeriodId);
        qb.parameter("studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);

        qb.sort("sg.code");
        List<?> data = qb.select("sg.id, sg.code, sg.course, c.id as curricId, cv.admission_year, " +
                "(select count(*) from student s where s.student_group_id=sg.id and s.status_code in (:studentStatus)) as student_count", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setCourse(resultAsInteger(r, 2));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 3), null, null));
            dto.setCurriculumVersionAdmissinYear(resultAsShort(r, 4));
            dto.setStudentCount(resultAsLong(r, 5));
            return dto;
        }, data);
    }

    public List<CurriculumSearchDto> getCurricula(Long schoolId) {
        List<Curriculum> curriculums = em.createQuery(
                "select c from Curriculum c "
                + "where c.school.id = ?1 and c.status.code = ?2 and c.higher = true "
                + "order by c.nameEt, c.nameEn, c.code", Curriculum.class)
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
                        .anyMatch(g -> group.getId().equals(EntityUtil.getId(g)));
                    
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

                    subject.getSubjectStudyPeriodPlans().stream()
                        .filter(p -> p.getStudyPeriod() == null
                            && (p.getCurriculums().isEmpty() || p.getCurriculums().stream()
                                .anyMatch(pc -> EntityUtil.getId(pc.getCurriculum())
                                        .equals(EntityUtil.getNullableId(group.getCurriculum()))))
                            && (p.getStudyForms().isEmpty() || p.getStudyForms().stream()
                                .anyMatch(pf -> EntityUtil.getCode(pf.getStudyForm())
                                        .equals(EntityUtil.getNullableCode(group.getStudyForm())))))
                        .min(Comparator.comparingInt(p -> {
                            int order = 0;
                            if (p.getCurriculums().stream()
                                    .noneMatch(pc -> EntityUtil.getId(pc.getCurriculum())
                                            .equals(EntityUtil.getNullableId(group.getCurriculum())))) {
                                order += 2;
                            }
                            if (p.getStudyForms().stream()
                                    .noneMatch(pf -> EntityUtil.getCode(pf.getStudyForm())
                                            .equals(EntityUtil.getNullableCode(group.getStudyForm())))) {
                                order += 1;
                            }
                            return order;
                        }))
                        .ifPresent(subjectStudyPeriodPlan ->
                                dto.setPlan(SubjectStudyPeriodPlanDto.of(subjectStudyPeriodPlan)));
                });
            return mappedSubjects;
        }
        return Collections.emptyMap();
    }

    public int getCurrentSemesterForGroupByPeriod(StudentGroup group, StudyPeriod period) {
        Short year = Optional.of(group)
                .map(StudentGroup::getCurriculumVersion)
                .map(CurriculumVersion::getAdmissionYear)
                .orElse(null);
        if (year == null) {
            // TODO what to do? no cv -> no year
            return -1;
        }
        int periodYearStart = period.getStudyYear().getStartDate().getYear();

        int yearDiff = periodYearStart - year.intValue();
        int semester = yearDiff * 2 + (EntityUtil.getCode(period.getType()).equals("OPPEPERIOOD_S")
                ? 1
                : EntityUtil.getCode(period.getType()).equals("OPPEPERIOOD_K")
                    ? 2
                    : -999);

        if (semester < 1) {
            return 1;
        }
        int allSemesters = Optional.of(group)
                .map(StudentGroup::getCurriculum)
                .map(Curriculum::getStudyPeriod)
                .map(sp -> sp / 6)
                .orElse(1);

        return Math.min(semester, allSemesters);
    }

    public void connect(SubjectStudyPeriod ssp, StudentGroup group) {
        SubjectStudyPeriodStudentGroup entity = new SubjectStudyPeriodStudentGroup();
        entity.setSubjectStudyPeriod(ssp);
        entity.setStudentGroup(group);
        EntityUtil.save(entity, em);
    }

    public void copyPlan(Long schoolId, StudyPeriod period, StudentGroup group, StudyPeriod sourcePeriod, StudentGroup sourceGroup) {
        // check that it does not exists before
        // check school
        AssertionFailedException.throwIf(!EntityUtil.getId(period.getStudyYear().getSchool()).equals(schoolId),
                "User and target period have different schools!");
        AssertionFailedException.throwIf(!EntityUtil.getId(group.getSchool()).equals(schoolId),
                "User and target group have different schools!");
        AssertionFailedException.throwIf(!EntityUtil.getId(sourcePeriod.getStudyYear().getSchool()).equals(schoolId),
                "User and source period have different schools!");
        AssertionFailedException.throwIf(!EntityUtil.getId(sourceGroup.getSchool()).equals(schoolId),
                "User and source group have different schools!");
        // fetch everything

        // curriculum program
        Map<Short, List<CurriculumProgramDto>> curriculumProgram = getCurriculumProgram(group, period);
        int currentSemester = getCurrentSemesterForGroupByPeriod(group, period);
        if (currentSemester == -1) {
            // There is not cv for this group so cannot compare curriculum program and copy
            return;
        }
        Set<Subject> allowedSubjects = new HashSet<>();
        if (!curriculumProgram.containsKey((short) currentSemester)) {
            return;
        }

        curriculumProgram.get((short) currentSemester)
                .forEach(cpd -> allowedSubjects.add(em.getReference(Subject.class, cpd.getSubject().getId())));
//        Set<Subject> notUpdatedSubjects = new HashSet<>(allowedSubjects);

        // data to be copied
        List<SubjectStudyPeriodStudentGroup> sspsgToCopy = sourceGroup.getSubjectStudyPeriods().stream()
                .filter(sspsg -> sspsg.getSubjectStudyPeriod().getStudyPeriod().equals(sourcePeriod))
                .collect(Collectors.toList());
        ValidationFailedException.throwIf(sspsgToCopy.isEmpty(), "There is nothing to be copied");

        sspsgToCopy.forEach(sspsg -> {
            // copy params
            SubjectStudyPeriod ssp = sspsg.getSubjectStudyPeriod();
            if (!allowedSubjects.contains(ssp.getSubject())) {
                return;
            }
//            notUpdatedSubjects.remove(ssp.getSubject());

            SubjectStudyPeriod nssp = new SubjectStudyPeriod();

            BeanUtils.copyProperties(ssp, nssp, "id", "version",
                    "inserted", "insertedBy", "changed", "changedBy",
                    // Period should be new
                    "studyPeriod",
                    // ignored
                    "moodleCourseId",
                    // OneToMany
                    "teachers", "studentGroups", "capacities", "midtermTasks",
                    "declarationSubjects", "protocols", "subgroups");
            nssp.setStudyPeriod(period);
            // capacities
            nssp.setCapacities(ssp.getCapacities().stream().map(sspc -> {
                SubjectStudyPeriodCapacity nsspc = new SubjectStudyPeriodCapacity();
                nsspc.setSubjectStudyPeriod(nssp);
                nsspc.setCapacityType(sspc.getCapacityType());
                nsspc.setHours(sspc.getHours());
                return nsspc;
            }).collect(Collectors.toList()));
            Map<String, SubjectStudyPeriodCapacity> sspCapacitiesMap = StreamUtil
                    .toMap(r -> EntityUtil.getCode(r.getCapacityType()), nssp.getCapacities());
            // copy teachers
            nssp.setTeachers(ssp.getTeachers().stream().map(sspt -> {
                SubjectStudyPeriodTeacher nsspt = new SubjectStudyPeriodTeacher();
                nsspt.setSubjectStudyPeriod(nssp);
                nsspt.setTeacher(sspt.getTeacher());
                nsspt.setIsSignatory(sspt.getIsSignatory());
                // copy capacities
                if (Boolean.TRUE.equals(nssp.getCapacityDiff())) {
                    nsspt.setCapacities(StreamUtil.nullSafeSet(sspt.getCapacities()).stream()
                    .filter(ssptc -> ssptc.getSubgroup() == null)
                    .map(ssptc -> {
                        SubjectStudyPeriodTeacherCapacity nssptc = new SubjectStudyPeriodTeacherCapacity();
                        nssptc.setHours(ssptc.getHours());
                        nssptc.setSubjectStudyPeriodTeacher(nsspt);
                        // copy capacity type
                        SubjectStudyPeriodCapacity capacity = sspCapacitiesMap.get(
                                EntityUtil.getCode(ssptc.getSubjectStudyPeriodCapacity().getCapacityType()));
                        nssptc.setSubjectStudyPeriodCapacity(capacity);
                        capacity.getTeacherCapacities().add(nssptc);
                        return nssptc;
                    }).collect(Collectors.toSet()));
                }
                return nsspt;
            }).collect(Collectors.toList()));

            SubjectStudyPeriodStudentGroup nsspsg = new SubjectStudyPeriodStudentGroup();
            nsspsg.setStudentGroup(group);
            nsspsg.setSubjectStudyPeriod(nssp);
            nssp.getStudentGroups().add(nsspsg);
            EntityUtil.save(nssp, em);
        });

//        if (!notUpdatedSubjects.isEmpty()) {
//            notUpdatedSubjects.forEach(subject -> {
//                SubjectStudyPeriod ssp = new SubjectStudyPeriod();
//                ssp.setSubject(subject);
//                ssp.setStudyPeriod(period);
//                ssp.setCapacityDiff(Boolean.FALSE);
//                ssp.setGroupProportion(em.getReference(Classifier.class, GroupProportion.PAEVIK_GRUPI_JAOTUS_1.name()));
//
//                SubjectStudyPeriodStudentGroup sspsg = new SubjectStudyPeriodStudentGroup();
//                sspsg.setStudentGroup(group);
//                sspsg.setSubjectStudyPeriod(ssp);
//                ssp.getStudentGroups().add(sspsg);
//
//                EntityUtil.save(ssp, em);
//            });
//        }
    }
}
