package ee.hitsa.ois.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.dto.TeacherOccupationDto;

@Transactional
@Service
public class TeacherOccupationService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierService classifierService;

    public Page<TeacherOccupationDto> search(Long schoolId, TeacherOccupationSearchCommand criteria, Pageable pageable) {
        JpaQueryBuilder<TeacherOccupation> qb = new JpaQueryBuilder<>(TeacherOccupation.class, "t").sort(pageable);
        qb.requiredCriteria("t.school.id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("t.occupationEt", "occupationEt", criteria.getOccupationEt());
        qb.optionalContains("t.occupationEn", "occupationEn", criteria.getOccupationEn());
        if(Boolean.TRUE.equals(criteria.getIsValid())) {
            qb.filter("t.isValid = true");
        }

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(TeacherOccupationDto::of);
    }

    public List<TeacherOccupationDto> listAll(Long schoolId) {
        List<TeacherOccupation> occupations = em.createQuery("select t from TeacherOccupation t where t.school.id = ?1", TeacherOccupation.class)
                .setParameter(1, schoolId).getResultList();
        return StreamUtil.toMappedList(TeacherOccupationDto::of, occupations);
    }

    public TeacherOccupation create(HoisUserDetails user, TeacherOccupationForm form) {
        TeacherOccupation teacherOccupation = new TeacherOccupation();
        teacherOccupation.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(teacherOccupation, form);
    }

    public TeacherOccupation save(TeacherOccupation teacherOccupation, TeacherOccupationForm form) {
        EntityUtil.bindToEntity(form, teacherOccupation);
        EntityUtil.save(teacherOccupation, em);
        if (teacherOccupation.getUserSchoolRole() != null) {
            UserSchoolRole role = teacherOccupation.getUserSchoolRole();
            // Role name is not used, but still required
            role.setNameEt(teacherOccupation.getOccupationEt().length() > 50 ? teacherOccupation.getOccupationEt().substring(0, 50) : teacherOccupation.getOccupationEt());
            role.setNameEn(teacherOccupation.getOccupationEn() != null && teacherOccupation.getOccupationEn().length() > 50
                    ? teacherOccupation.getOccupationEn().substring(0, 50) : teacherOccupation.getOccupationEn());
            EntityUtil.save(role, em);
        }
        return teacherOccupation;
    }

    public void delete(HoisUserDetails user, TeacherOccupation teacherOccupation) {
        EntityUtil.setUsername(user.getUsername(), em);
        if (teacherOccupation.getUserSchoolRole() != null) {
            final ClassifierCache clCache = new ClassifierCache(classifierService);
            Map<String, Set<String>> rolePermissions = teacherOccupation.getUserSchoolRole().getRights().stream()
                    .collect(Collectors.groupingBy(r -> r.getObject().getCode(),
                            Collectors.mapping(r -> r.getPermission().getCode(), Collectors.toSet())));
            teacherOccupation.getTeachers().stream().map(Teacher::getUser).filter(Objects::nonNull).filter(u -> DateUtils.isValid(u.getValidFrom(), u.getValidThru())).forEach(u -> {
                UserRolesService.updateRoleForUser(clCache, u, rolePermissions, Collections.emptyMap());
            });
        }
        EntityUtil.deleteEntity(teacherOccupation, em);
    }
}
