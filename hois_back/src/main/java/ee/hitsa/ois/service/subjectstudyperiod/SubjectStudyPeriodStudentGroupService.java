package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.repository.SubjectStudyPeriodPlanRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_group sg join curriculum c on c.id = sg.curriculum_id");

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("c.is_higher = true");
        qb.requiredCriteria("c.status_code = :curriculumStatus", "curriculumStatus", CurriculumStatus.OPPEKAVA_STAATUS_K);
        qb.filter("(sg.valid_from is null or sg.valid_from <= current_date)");
        qb.filter("(sg.valid_thru is null or sg.valid_thru >= current_date)");

        qb.optionalCriteria("not exists " 
                        + "(select * from subject_study_period_student_group ssp_sg "
                        + "join subject_study_period ssp on ssp.id = ssp_sg.subject_study_period_id "
                        + "where ssp.study_period_id = :studyPeriodId " + "and ssp_sg.student_group_id = sg.id )",
                          "studyPeriodId", studyPeriodId);

        List<?> data = qb.select("sg.id, sg.code, sg.course, c.id as curricId", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setCourse(resultAsInteger(r, 2));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 3), null, null));
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
}
