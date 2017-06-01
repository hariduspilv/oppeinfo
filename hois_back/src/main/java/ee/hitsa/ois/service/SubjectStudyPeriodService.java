package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodPlanRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodTeacherForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherDto;
import ee.hitsa.ois.web.dto.TeacherSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodService {

    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private SubjectStudyPeriodPlanRepository subjectStudyPeriodPlanRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private ClassifierRepository classifierRepository;

    // SubjectStudyPeriods

    public Page<SubjectStudyPeriodSearchDto> search(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        final String FROM = "from subject_study_period ssp " 
                + "inner join subject s on s.id = ssp.subject_id "
                + "inner join study_period sp on ssp.study_period_id = sp.id ";
        final String SELECT = 
                  "ssp.id as subjectStudyPeriodId, " 
                + "sp.name_et as spNameEt, sp.name_en as spNameEn, "
                + "s.id as subjectId, s.name_et as subNameEt, s.name_en as subNameEn, s.code, "
                + "(select string_agg(p2.firstname || ' ' || p2.lastname, ';') " + "from subject_study_period ssp2 "
                + "left join subject_study_period_teacher sspt2 on sspt2.subject_study_period_id = ssp2.id "
                + "left join teacher t2 on t2.id = sspt2.teacher_id " + "left join person p2 on p2.id = t2.person_id "
                + "where ssp2.id = ssp.id ) as names";

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM).sort(pageable);
        if (StringUtils.hasText(criteria.getTeachersFullname())) {
            qb.requiredCriteria("exists"
                    + "(select sspt.id " 
                    + "from subject_study_period_teacher sspt "
                    + "inner join teacher t on t.id = sspt.teacher_id " + "inner join person p on p.id = t.person_id "
                    + "where upper(p.firstname || ' ' || p.lastname) like :teachersName "
                    + "and sspt.subject_study_period_id = ssp.id)", "teachersName",
                    JpaQueryUtil.toContains(criteria.getTeachersFullname()));
        }
        qb.optionalContains(Arrays.asList("s.name_et", "s.name_en", "s.code", "s.name_et || '/' || s.code",
                "s.name_en || '/' || s.code"), "subjectNameAndCode", criteria.getSubjectNameAndCode());
        qb.optionalCriteria("sp.id in (:studyPeriods)", "studyPeriods", criteria.getStudyPeriods());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        return results.map(r -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setId(resultAsLong(r, 0));
            AutocompleteResult studyPeriod = new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 2));
            String subjectCode = resultAsString(r, 6);
            String subjectNameEtCode = resultAsString(r, 4) + "/" + subjectCode;
            String subjectNameEnCode = resultAsString(r, 5) + "/" + subjectCode;
            AutocompleteResult subject = new AutocompleteResult(resultAsLong(r, 3), subjectNameEtCode,
                    subjectNameEnCode);
            dto.setStudyPeriod(studyPeriod);
            String s = resultAsString(r, 7);
            if (s != null) {
                dto.setTeachers(Arrays.asList(s.split(";")).stream().collect(Collectors.toSet()));
            }
            dto.setSubject(subject);
            return dto;
        });
    }

    public SubjectStudyPeriod create(SubjectStudyPeriodForm form) {
        SubjectStudyPeriod subjectStudyPeriod = new SubjectStudyPeriod();
        subjectStudyPeriod.setSubject(subjectRepository.getOne(form.getSubject()));
        subjectStudyPeriod.setStudyPeriod(studyPeriodRepository.getOne(form.getStudyPeriod()));
        return update(subjectStudyPeriod, form);
    }

    public SubjectStudyPeriod update(SubjectStudyPeriod subjectStudyPeriod, SubjectStudyPeriodForm form) {
        EntityUtil.bindToEntity(form, subjectStudyPeriod, classifierRepository, "subject", "studyPeriod", "teachers",
                "studentGroups");
        updateSubjectStudyPeriodTeachers(subjectStudyPeriod, form);
        updateStudentGroups(subjectStudyPeriod, form.getStudentGroups());
        return subjectStudyPeriodRepository.save(subjectStudyPeriod);
    }

    public void updateSubjectStudyPeriodTeachers(SubjectStudyPeriod subjectStudyPeriod, SubjectStudyPeriodForm form) {
        Map<Long, SubjectStudyPeriodTeacher> oldTeachersMap = StreamUtil.toMap(t -> EntityUtil.getId(t.getTeacher()),
                subjectStudyPeriod.getTeachers());
        List<SubjectStudyPeriodTeacher> newTeachers = new ArrayList<>();
        for (SubjectStudyPeriodTeacherForm t : form.getTeachers()) {
            SubjectStudyPeriodTeacher teacher = oldTeachersMap.get(t.getTeacherId());
            if (teacher == null) {
                teacher = new SubjectStudyPeriodTeacher();
                teacher.setSubjectStudyPeriod(subjectStudyPeriod);
                teacher.setTeacher(teacherRepository.getOne(t.getTeacherId()));
            }
            teacher.setIsSignatory(t.getIsSignatory());
            newTeachers.add(teacher);
        }
        subjectStudyPeriod.setTeachers(newTeachers);
    }

    private void updateStudentGroups(SubjectStudyPeriod subjectStudyPeriod, List<Long> newStudentGroups) {
        EntityUtil.bindEntityCollection(subjectStudyPeriod.getStudentGroups(),
                s -> EntityUtil.getId(s.getStudentGroup()), newStudentGroups, newStudentGroupId -> {
                    SubjectStudyPeriodStudentGroup sg = new SubjectStudyPeriodStudentGroup();
                    sg.setSubjectStudyPeriod(subjectStudyPeriod);
                    sg.setStudentGroup(studentGroupRepository.getOne(newStudentGroupId));
                    return sg;
                });
    }

    public void delete(SubjectStudyPeriod subjectStudyPeriod) {
        EntityUtil.deleteEntity(subjectStudyPeriodRepository, subjectStudyPeriod);
    }

    // StudentGroups

    public Page<StudentGroupSearchDto> searchByStudentGroup(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {

        return studentGroupRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            if (criteria.getStudentGroup() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getStudentGroup()));
            }
            if (criteria.getCurriculum() != null) {
                filters.add(cb.equal(root.get("curriculum").get("id"), criteria.getCurriculum()));
            }
            Long department = criteria.getDepartment();
            if (department != null) {
                Subquery<Long> departmentQuery = query.subquery(Long.class);
                Root<CurriculumDepartment> curriculumDepartmentRoot = departmentQuery.from(CurriculumDepartment.class);
                departmentQuery = departmentQuery.select(curriculumDepartmentRoot.get("curriculum").get("id")).where(
                        curriculumDepartmentRoot.get("schoolDepartment").get("id").in(Arrays.asList(department)));
                filters.add(root.get("curriculum").get("id").in(departmentQuery));
            }
            filters.add(cb.equal(root.get("curriculum").get("higher"), Boolean.TRUE));

            /*
             * Only valid student groups should be shown
             */
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), LocalDate.now()),
                    cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), LocalDate.now()),
                    cb.isNull(root.get("validThru"))));

            /*
             * Search should show only those studentGroups, which have any
             * connections with subject_study_period_student_group table with
             * specific studyPeriod
             */
            Subquery<Long> sspStudentGroupQuery = query.subquery(Long.class);
            Root<SubjectStudyPeriodStudentGroup> sspStudentGroupRoot = sspStudentGroupQuery
                    .from(SubjectStudyPeriodStudentGroup.class);
            sspStudentGroupQuery = sspStudentGroupQuery.select(sspStudentGroupRoot.get("studentGroup").get("id"))
                    .where(cb.equal(sspStudentGroupRoot.get("subjectStudyPeriod").get("studyPeriod").get("id"),
                            criteria.getStudyPeriod()));
            filters.add(root.get("id").in(sspStudentGroupQuery));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(sg -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(EntityUtil.getId(sg));
            dto.setCode(sg.getCode());
            dto.setCurriculum(AutocompleteResult.of(sg.getCurriculum()));            
            List<SubjectStudyPeriod> ssps = filterSsps
                    (StreamUtil.toMappedList(sspSg -> sspSg.getSubjectStudyPeriod(), sg.getSubjectStudyPeriods()), 
                            criteria.getStudyPeriod());
            dto.setHours(getHours(ssps));

            return dto;
        });
    }

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
        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            dto.setTeachers(StreamUtil.toMappedList(SubjectStudyPeriodTeacherDto::of, ssp.getTeachers()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            return dto;
        }, ssps);
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodDtos);
    }

    public void setSubjects(SubjectStudyPeriodDtoContainer container) {
        List<Long> subjectIds = StreamUtil.toMappedList(s -> s.getSubject(), container.getSubjectStudyPeriodDtos());
        List<Subject> subjects = subjectRepository.findAll(subjectIds);
        List<AutocompleteResult> dtos = StreamUtil.toMappedList(AutocompleteResult::of, subjects);
        container.setSubjects(dtos);
    }

    public void setSubjectStudyPeriodPlansToStudentGroupContainer(SubjectStudyPeriodDtoContainer container) {

        StudentGroup sg = studentGroupRepository.getOne(container.getStudentGroup());

        List<SubjectStudyPeriodPlan> plans = subjectStudyPeriodPlanRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));

            List<Long> subjectIds = StreamUtil.toMappedList(s -> s.getSubject(), container.getSubjectStudyPeriodDtos());
            if (!CollectionUtils.isEmpty(subjectIds)) {
                filters.add(root.get("subject").get("id").in(subjectIds));
            }
            /*
             * Below is not working attempt to filter properly by studyForm
             */
            // Subquery<Long> studyFormSubquery = query.subquery(Long.class);
            // Root<SubjectStudyPeriodPlanStudyform> targetRoot =
            // studyFormSubquery.from(SubjectStudyPeriodPlanStudyform.class);
            // studyFormSubquery = studyFormSubquery
            // .select(targetRoot.get("plan").get("id"));
            //
            // Subquery<Long> studyFormSubquery2 = query.subquery(Long.class);
            // Root<SubjectStudyPeriodPlanStudyform> targetRoot2 =
            // studyFormSubquery2.from(SubjectStudyPeriodPlanStudyform.class);
            // studyFormSubquery2 = studyFormSubquery2
            // .select(targetRoot2.get("plan").get("id"))
            // .where(cb.equal(targetRoot2.get("studyForm").get("code"),
            // sg.getStudyForm().getCode()));
            // filters.add(cb.or(cb.not(cb.exists(studyFormSubquery)),
            // root.get("id").in(studyFormSubquery2)));
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
            return CollectionUtils.isEmpty(curriculums) || curriculums.contains(EntityUtil.getId(sg.getCurriculum()));
        }).filter(p -> {
            List<String> studyForms = StreamUtil.toMappedList(sf -> EntityUtil.getCode(sf.getStudyForm()), p.getStudyForms());
            return CollectionUtils.isEmpty(studyForms) || studyForms.contains(EntityUtil.getCode(sg.getStudyForm()));
        }).collect(Collectors.toList());

        container.setSubjectStudyPeriodPlans(StreamUtil.toMappedList(plan -> {
            SubjectStudyPeriodPlanDto dto = new SubjectStudyPeriodPlanDto();
            dto.setId(EntityUtil.getId(plan));
            dto.setSubject(EntityUtil.getId(plan.getSubject()));
            dto.setCapacities(StreamUtil.toMappedSet(SubjectStudyPeriodPlanCapacityDto::of, plan.getCapacities()));
            return dto;
        }, plans));
    }

    public void updateSspCapacities(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = new ArrayList<>();

        for (SubjectStudyPeriodDto dto : container.getSubjectStudyPeriodDtos()) {
            SubjectStudyPeriod ssp = subjectStudyPeriodRepository.getOne(dto.getId());

            AssertionFailedException.throwIf(!EntityUtil.getId(ssp.getSubject().getSchool()).equals(schoolId),
                    "User and subject have different schools!");

            List<SubjectStudyPeriodCapacityDto> newCapacities = dto.getCapacities().stream()
                    .filter(c -> c.getHours() != null).collect(Collectors.toList());

            EntityUtil.bindEntityCollection(ssp.getCapacities(), SubjectStudyPeriodCapacity::getId, newCapacities,
                    SubjectStudyPeriodCapacityDto::getId, dto3 -> {
                        SubjectStudyPeriodCapacity newCapacity = EntityUtil.bindToEntity(dto3,
                                new SubjectStudyPeriodCapacity(), classifierRepository);
                        newCapacity.setSubjectStudyPeriod(ssp);
                        return newCapacity;
                    }, (dto2, c) -> {
                        c.setHours(dto2.getHours());
                    });
            ssps.add(ssp);
        }
        subjectStudyPeriodRepository.save(ssps);
    }

    public List<StudentGroupSearchDto> getStudentGroupsList(Long schoolId, Long studyPeriodId) {

        final String SELECT = "sg.id, sg.code, sg.course, c.id as curricId";
        final String FROM = "from student_group sg join curriculum c on c.id = sg.curriculum_id";

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM);

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("c.is_higher = true");
        qb.filter("c.status_code = 'OPPEKAVA_STAATUS_K'");
        qb.filter("(sg.valid_from is null or sg.valid_from <= current_date)");
        qb.filter("(sg.valid_thru is null or sg.valid_thru >= current_date)");

        qb.optionalCriteria("not exists " 
                        + "(select * from subject_study_period_student_group ssp_sg "
                        + "join subject_study_period ssp on ssp.id = ssp_sg.subject_study_period_id "
                        + "where ssp.study_period_id = :studyPeriodId " + "and ssp_sg.student_group_id = sg.id )",
                          "studyPeriodId", studyPeriodId);

        List<?> data = qb.select(SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setCourse(resultAsInteger(r, 2));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 3), null, null));
            return dto;
        }, data);
    }

    // Curricula for StudentGroups

    public List<CurriculumSearchDto> getCurricula(Long schoolId) {
        List<Curriculum> curriculums = curriculumRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            // TODO use enum constant
            filters.add(cb.equal(root.get("status").get("code"), "OPPEKAVA_STAATUS_K"));
            filters.add(cb.equal(root.get("higher"), Boolean.TRUE));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        return StreamUtil.toMappedList(c -> {
            CurriculumSearchDto dto = new CurriculumSearchDto();
            dto.setId(c.getId());
            dto.setNameEt(c.getNameEt());
            dto.setNameEn(c.getNameEn());
            dto.setDepartments(StreamUtil.toMappedList(d -> EntityUtil.getId(d.getSchoolDepartment()), c.getDepartments()));
            return dto;
        }, curriculums);
    }

    // Teachers

    public Page<TeacherSearchDto> searchByTeachers(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        return teacherRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            if (criteria.getTeacher() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getTeacher()));
            }

            filters.add(cb.equal(root.get("isHigher"), Boolean.TRUE));
            filters.add(cb.equal(root.get("isActive"), Boolean.TRUE));

            /*
             * Search should show only those teachers, which have any
             * connections with subject_study_period_teacher table with specific
             * studyPeriod
             */
            Subquery<Long> sspTeacherQuery = query.subquery(Long.class);
            Root<SubjectStudyPeriodTeacher> sspTeacherRoot = sspTeacherQuery.from(SubjectStudyPeriodTeacher.class);
            sspTeacherQuery = sspTeacherQuery.select(sspTeacherRoot.get("teacher").get("id")).where(cb.equal(
                    sspTeacherRoot.get("subjectStudyPeriod").get("studyPeriod").get("id"), criteria.getStudyPeriod()));
            filters.add(root.get("id").in(sspTeacherQuery));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(t -> {
            TeacherSearchDto dto = new TeacherSearchDto();
            dto.setId(EntityUtil.getId(t));
            dto.setName(t.getPerson().getFullname());            
            List<SubjectStudyPeriod> ssps = filterSsps
                    (StreamUtil.toMappedList(sspt -> sspt.getSubjectStudyPeriod(), t.getSubjectStudyPeriods()), 
                            criteria.getStudyPeriod());
            dto.setHours(getHours(ssps));
            return dto;
        });
    }

    public void setSubjectStudyPeriodsToTeachersContainer(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));
            filters.add(cb.equal(root.get("studyPeriod").get("studyYear").get("school").get("id"), schoolId));

            Subquery<Long> teacherSubquery = query.subquery(Long.class);
            Root<SubjectStudyPeriodTeacher> targetRoot = teacherSubquery.from(SubjectStudyPeriodTeacher.class);
            teacherSubquery = teacherSubquery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                    .where(cb.equal(targetRoot.get("teacher").get("id"), container.getTeacher()));
            filters.add(root.get("id").in(teacherSubquery));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            dto.setStudentGroupObjects(
                    StreamUtil.toMappedList(s -> AutocompleteResult.of(s.getStudentGroup()), ssp.getStudentGroups()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            return dto;
        }, ssps);
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodDtos);
    }

    public void setSubjectStudyPeriodPlansToTeachersContainer(SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriodPlan> plans = subjectStudyPeriodPlanRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));

            List<Long> subjectIds = StreamUtil.toMappedList(s -> s.getSubject(), container.getSubjectStudyPeriodDtos());
            if (!CollectionUtils.isEmpty(subjectIds)) {
                filters.add(root.get("subject").get("id").in(subjectIds));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        container.setSubjectStudyPeriodPlans(StreamUtil.toMappedList(plan -> {
            SubjectStudyPeriodPlanDto dto = new SubjectStudyPeriodPlanDto();
            dto.setId(EntityUtil.getId(plan));
            dto.setSubject(EntityUtil.getId(plan.getSubject()));
            dto.setCapacities(StreamUtil.toMappedSet(SubjectStudyPeriodPlanCapacityDto::of, plan.getCapacities()));
            return dto;
        }, plans));
    }

    public List<AutocompleteResult> getTeachersList(Long schoolId, Long studyPeriodId) {
        final String SELECT = "t.id, p.firstname, p.lastname";
        final String FROM = "from teacher t join person p on p.id = t.person_id";

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM);

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("t.is_higher = true");
        qb.filter("t.is_active = true");

        qb.requiredCriteria("not exists " 
                        + "(select * from subject_study_period_teacher sspt "
                        + "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                        + "where ssp.study_period_id = :studyPeriodId and sspt.teacher_id = t.id)",
                          "studyPeriodId", studyPeriodId);

        List<?> data = qb.select(SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    // subjects

    public Page<SubjectStudyPeriodSearchDto> searchBySubjects(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        return subjectRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            filters.add(cb.equal(root.get("status").get("code"), SubjectStatus.AINESTAATUS_K.name()));
            /*
             * Search should show only those subjects, which have any
             * connections with subject_study_period table with specific
             * studyPeriod
             */
            Subquery<Long> sspSubjectQuery = query.subquery(Long.class);
            Root<SubjectStudyPeriod> sspSubjectRoot = sspSubjectQuery.from(SubjectStudyPeriod.class);
            sspSubjectQuery = sspSubjectQuery.select(sspSubjectRoot.get("subject").get("id"))
                    .where(cb.equal(sspSubjectRoot.get("studyPeriod").get("id"), criteria.getStudyPeriod()));
            filters.add(root.get("id").in(sspSubjectQuery));

            if (criteria.getSubject() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getSubject()));
            }
            if (criteria.getTeacher() != null) {
                Subquery<Long> sspTeachersQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriodTeacher> sspTeachertRoot = sspTeachersQuery
                        .from(SubjectStudyPeriodTeacher.class);
                sspTeachersQuery = sspTeachersQuery
                        .select(sspTeachertRoot.get("subjectStudyPeriod").get("subject").get("id"))
                        .where(cb.equal(sspTeachertRoot.get("teacher").get("id"), criteria.getTeacher()));
                filters.add(root.get("id").in(sspTeachersQuery));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(s -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setSubject(AutocompleteResult.of(s));
            Set<String> teachers = new HashSet<>();
            
            List<SubjectStudyPeriod> ssps = filterSsps(s.getSubjectStudyPeriods(), criteria.getStudyPeriod());
            
            for (SubjectStudyPeriod ssp : ssps) {
                teachers.addAll(
                        StreamUtil.toMappedList(t -> t.getTeacher().getPerson().getFullname(), ssp.getTeachers()));
            }
            dto.setTeachers(teachers);
            dto.setHours(getHours(ssps));
            return dto;
        });
    }
    
    private static List<SubjectStudyPeriod> filterSsps(Collection<SubjectStudyPeriod> ssps, Long studyPeriod) {
        return ssps.stream().filter(ssp -> EntityUtil.getId(ssp.getStudyPeriod()).equals(studyPeriod)).collect(Collectors.toList());
    }
    
    private static Long getHours(List<SubjectStudyPeriod> ssps) {
        long sum = 0;
        for (SubjectStudyPeriod ssp : ssps) {
            sum += ssp.getCapacities().stream().mapToLong(SubjectStudyPeriodCapacity::getHours).sum();
        }
        return Long.valueOf(sum);
    }

    public void setSubjectStudyPeriodsToSubjectsContainer(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));
            filters.add(cb.equal(root.get("studyPeriod").get("studyYear").get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("subject").get("id"), container.getSubject()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            dto.setStudentGroupObjects(
                    StreamUtil.toMappedList(s -> AutocompleteResult.of(s.getStudentGroup()), ssp.getStudentGroups()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            dto.setTeachers(StreamUtil.toMappedList(SubjectStudyPeriodTeacherDto::of, ssp.getTeachers()));
            return dto;
        }, ssps);
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodDtos);
    }

    public List<AutocompleteResult> getSubjectsList(Long schoolId, Long studyPeriodId) {
        final String SELECT = "s.id, s.code, s.name_et, s.name_en, s.credits";
        final String FROM = "from subject s";

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code = :status", "status", SubjectStatus.AINESTAATUS_K.name());

        qb.optionalCriteria("not exists " 
                        + "(select * from subject_study_period ssp "
                        + " where ssp.study_period_id = :studyPeriodId and ssp.subject_id = s.id)",
                           "studyPeriodId", studyPeriodId);

        List<?> data = qb.select(SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            BigDecimal credits = resultAsDecimal(r, 4);
            String nameEt = SubjectUtil.subjectName(code, resultAsString(r, 2), credits);
            String nameEn = SubjectUtil.subjectName(code, resultAsString(r, 3), credits);
            return new AutocompleteResult(resultAsLong(r, 0), nameEt, nameEn);
        }, data);
    }
}
