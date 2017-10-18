package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.repository.SubjectStudyPeriodPlanRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;

@Transactional
@Service
public class SubjectStudyPeriodTeacherService {

    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private SubjectStudyPeriodPlanRepository subjectStudyPeriodPlanRepository;

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
            if (!subjectIds.isEmpty()) {
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on p.id = t.person_id");

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("t.is_higher = true");
        qb.filter("t.is_active = true");

        qb.requiredCriteria("not exists " 
                        + "(select * from subject_study_period_teacher sspt "
                        + "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                        + "where ssp.study_period_id = :studyPeriodId and sspt.teacher_id = t.id)",
                          "studyPeriodId", studyPeriodId);

        List<?> data = qb.select("t.id, p.firstname, p.lastname", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

}
