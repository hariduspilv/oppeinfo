package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.StudentGroup;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.StudentGroupSearchCommand;
import ee.hitsa.ois.web.dto.StudentGroupSearchDto;

@Transactional
@Service
public class StudentGroupService {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudentGroupRepository studentGroupRepository;

    @SuppressWarnings("unchecked")
    public Page<StudentGroupSearchDto> search(Long schoolId, StudentGroupSearchCommand criteria, Pageable pageable) {
        return JpaQueryUtil.query(StudentGroupSearchDto.class, StudentGroup.class, (root, query, cb) -> {
            // TODO optimize curriculum fetch. Now it's N + 1 queries
            // TODO only students with status õpib, akadeemilisel või välisõppes
            ((CriteriaQuery<StudentGroupSearchDto>)query).select(cb.construct(StudentGroupSearchDto.class, root.get("id"), root.get("code"), root.get("curriculum"), root.get("studyForm").get("code"), root.get("course"), cb.count(root.join("students").get("id"))));
            query.groupBy(root.get("id"), root.get("code"), root.get("curriculum"), root.get("studyForm"), root.get("course"));

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            propertyContains(() -> root.get("code"), cb, criteria.getCode(), filters::add);
            if(!CollectionUtils.isEmpty(criteria.getCurriculum())) {
                filters.add(root.get("curriculum").get("id").in(criteria.getCurriculum()));
            }
            if(!CollectionUtils.isEmpty(criteria.getStudyForm())) {
                filters.add(root.get("studyForm").get("code").in(criteria.getStudyForm()));
            }
            if(criteria.getTeacher() != null) {
                filters.add(root.get("teacher").get("id").in(criteria.getTeacher()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em);
    }

    public StudentGroup save(StudentGroup studentGroup, StudentGroupForm form) {
        EntityUtil.bindToEntity(form, studentGroup);
        studentGroup = studentGroupRepository.save(studentGroup);
        // TODO update student list in group
        return studentGroup;
    }

    public void delete(StudentGroup studentGroup) {
        studentGroupRepository.delete(studentGroup);
    }
}
