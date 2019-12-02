package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.domain.UserSchoolRoleRights;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.UserRolesService.SearchResult;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler;
import ee.hitsa.ois.web.commandobject.UserSchoolRoleCheckCommand;
import ee.hitsa.ois.web.commandobject.UserSchoolRoleForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.UserSchoolRoleDto;

@Transactional
@Service
public class UserTeacherRolesService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierService classifierService;

    public Page<AutocompleteResult> searchRoles(HoisUserDetails user, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher_occupation tocc "
                + "left join user_school_role usr on usr.teacher_occupation_id = tocc.id").sort(pageable);
        qb.requiredCriteria("coalesce(usr.school_id, tocc.school_id) = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("(tocc.is_valid or exists(select 1 from teacher t where t.teacher_occupation_id = tocc.id))");
        return JpaQueryUtil.pagingResult(qb, "coalesce(usr.id, tocc.id) as id, "
                + "tocc.occupation_et as name_et, "
                + "tocc.occupation_en as name_en, "
                + "usr.id is not null as created", em, pageable).map(r -> {
            return new SearchResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsBoolean(r, 3));
        });
    }

    public UserSchoolRoleDto getRole(UserSchoolRole role) {
        return UserSchoolRoleDto.of(role);
    }
    
    public Map<String, Boolean> checkRole(UserSchoolRoleCheckCommand cmd, boolean checkIfHasUsers) {
        Map<String, Boolean> checkResult = new HashMap<>();
        Optional<UserSchoolRole> optUsr = em.createQuery("select usr from UserSchoolRole usr "
                + "where (usr.nameEt = ?1 or (?2 is not null and usr.nameEn = ?2)) and usr.school.id = ?3 and (?4 is null or usr.id != ?4)", UserSchoolRole.class)
                .setParameter(1, cmd.getNameEt())
                .setParameter(2, cmd.getNameEn())
                .setParameter(3, cmd.getSchoolId())
                .setParameter(4, cmd.getRoleId())
                .setMaxResults(1).getResultList().stream().findAny();
        if (optUsr.isPresent()) {
            if (Objects.equals(optUsr.get().getNameEt(), cmd.getNameEt())) {
                checkResult.put("existsSameNameEt", Boolean.TRUE);
            }
            
            if (cmd.getNameEn() != null && Objects.equals(optUsr.get().getNameEn(), cmd.getNameEn())) {
                checkResult.put("existsSameNameEn", Boolean.TRUE);
            }
        }
        checkResult.put("existsSameOccupation", Boolean.valueOf(!em.createQuery("select usr.teacherOccupation from UserSchoolRole usr "
                + "where usr.teacherOccupation.id = ?1 and usr.school.id = ?2 and (?3 is null or usr.id != ?3)", TeacherOccupation.class)
                .setParameter(1, cmd.getTeacherOccupationId())
                .setParameter(2, cmd.getSchoolId())
                .setParameter(3, cmd.getRoleId())
                .setMaxResults(1).getResultList().isEmpty()));
        if (cmd.getTeacherOccupationId() != null && checkIfHasUsers) {
            checkResult.put("hasUsers", Boolean.valueOf(!em.createNativeQuery("select 1 from teacher_occupation tocc "
                    + "join teacher t on t.teacher_occupation_id = tocc.id "
                    + "join user_ u on t.id = u.teacher_id "
                    + "where tocc.id = ?1 and "
                    + "((u.valid_from is null or u.valid_from <= ?2) and (u.valid_thru is null or u.valid_thru >= ?2))")
                .setParameter(1, cmd.getTeacherOccupationId())
                .setParameter(2, JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .setMaxResults(1)
                .getResultList().isEmpty()));
        }
        return checkResult;
    }

    /**
     * School, Teacher occupation, names are set during creation.
     * 
     * @param user
     * @param cmd
     * @return
     */
    public UserSchoolRole createRole(HoisUserDetails user, UserSchoolRoleForm cmd) {
        UserSchoolRole role = new UserSchoolRole();
        role.setSchool(em.getReference(School.class, user.getSchoolId()));
        AssertionFailedException.throwIf(cmd.getTeacherOccupation() == null, "No teacher occupation is given");
        TeacherOccupation occupation = em.getReference(TeacherOccupation.class, cmd.getTeacherOccupation().getId());
        role.setTeacherOccupation(occupation);
        AssertionFailedException.throwIf(!role.getSchool().equals(occupation.getSchool()), "School should be the same for role and occupation");
        role.setNameEt(occupation.getOccupationEt().length() > 50 ? occupation.getOccupationEt().substring(0, 50) : occupation.getOccupationEt());
        role.setNameEn(occupation.getOccupationEn() != null && occupation.getOccupationEn().length() > 50
                ? occupation.getOccupationEn().substring(0, 50) : occupation.getOccupationEn());
        return updateRole(user, role, cmd);
    }

    public UserSchoolRole updateRole(HoisUserDetails user, UserSchoolRole role, UserSchoolRoleForm cmd) {
        EntityUtil.setUsername(user.getUsername(), em);
        UserSchoolRoleCheckCommand checkCmd = new UserSchoolRoleCheckCommand();
        checkCmd.setRoleId(role.getId());
        checkCmd.setNameEt(role.getNameEt());
        checkCmd.setNameEn(role.getNameEn());
        checkCmd.setSchoolId(EntityUtil.getId(role.getSchool()));
        checkCmd.setTeacherOccupationId(EntityUtil.getId(role.getTeacherOccupation()));
        Map<String, Boolean> check = checkRole(checkCmd, false);
        List<ControllerErrorHandler.ErrorInfo.Error> formErrors = new ArrayList<>();
        if (Boolean.TRUE.equals(check.get("existsSameNameEt"))) {
            formErrors.add(new ControllerErrorHandler.ErrorInfo.ErrorForField("sameNameExists", "nameEt"));
        }
        if (Boolean.TRUE.equals(check.get("existsSameNameEn"))) {
            formErrors.add(new ControllerErrorHandler.ErrorInfo.ErrorForField("sameNameExists", "nameEn"));
        }
        if (!formErrors.isEmpty()) {
            throw new ValidationFailedException(formErrors);
        }
        ValidationFailedException.throwIf(Boolean.TRUE.equals(check.get("existsSameOccupation")), "userRole.occupationExists");
        
        EntityUtil.bindToEntity(cmd, role, "school", "teacherOccupation", "rights", "overwriteRights", "nameEt", "nameEn");
        
        // load allowed codes
        List<?> cl = em.createNativeQuery("select c.code, c.main_class_code from classifier c where (c.main_class_code = ?1 and c.code in (select object_code from user_role_default where role_code = ?2)) or c.main_class_code = ?3")
                .setParameter(1, MainClassCode.TEEMAOIGUS.name())
                .setParameter(2, Role.ROLL_A.name())
                .setParameter(3, MainClassCode.OIGUS.name())
                .getResultList();
        Set<String> objects = StreamUtil.toMappedSet(r -> resultAsString(r, 0), cl.stream().filter(r -> MainClassCode.TEEMAOIGUS.name().equals(resultAsString(r, 1))));
        Set<String> permissions = StreamUtil.toMappedSet(r -> resultAsString(r, 0), cl.stream().filter(r -> MainClassCode.OIGUS.name().equals(resultAsString(r, 1))));

        // we are using List with two elements (object, permission) as tuple
        List<List<String>> newRights = new ArrayList<>();
        for(Map.Entry<String, List<String>> it : StreamUtil.nullSafeMap(cmd.getRights()).entrySet()) {
            if(!objects.contains(it.getKey())) {
                throw new AssertionFailedException("Unknown object code: " + it.getKey());
            }
            for(String p : StreamUtil.nullSafeList(it.getValue())) {
                if(!permissions.contains(p)) {
                    throw new AssertionFailedException("Unknown permission code: " + p);
                }
                newRights.add(Arrays.asList(it.getKey(), p));
            }
        }
        
        ClassifierCache clCache = new ClassifierCache(classifierService);
        
        EntityUtil.bindEntityCollection(role.getRights(), r -> Arrays.asList(EntityUtil.getCode(r.getObject()), EntityUtil.getCode(r.getPermission())), newRights, id -> {
            UserSchoolRoleRights ur = new UserSchoolRoleRights();
            ur.setUserSchoolRole(role);
            ur.setObject(clCache.getByCode(id.get(0), MainClassCode.TEEMAOIGUS));
            ur.setPermission(clCache.getByCode(id.get(1), MainClassCode.OIGUS));
            return ur;
        });

        if (Boolean.TRUE.equals(cmd.getOverwriteRights())) {
            role.getTeacherOccupation().getTeachers().stream().map(Teacher::getUser).filter(Objects::nonNull).filter(u -> DateUtils.isValid(u.getValidFrom(), u.getValidThru())).forEach(u -> {
                EntityUtil.bindEntityCollection(u.getUserRights(), r -> Arrays.asList(EntityUtil.getCode(r.getObject()), EntityUtil.getCode(r.getPermission())), newRights, id -> {
                    UserRights ur = new UserRights();
                    ur.setUser(u);
                    ur.setObject(clCache.getByCode(id.get(0), MainClassCode.TEEMAOIGUS));
                    ur.setPermission(clCache.getByCode(id.get(1), MainClassCode.OIGUS));
                    return ur;
                });
            });
        }
        return EntityUtil.save(role, em);
    }

    public void deleteRole(HoisUserDetails user, UserSchoolRole role, boolean deleteRights) {
        EntityUtil.setUsername(user.getUsername(), em);
        if (deleteRights) {
            final ClassifierCache clCache = new ClassifierCache(classifierService);
            // User by default without user_school_role_id which means that user has a connection via teacher occupation
            Map<String, Set<String>> rolePermissions = role.getRights().stream()
                    .collect(Collectors.groupingBy(r -> r.getObject().getCode(),
                            Collectors.mapping(r -> r.getPermission().getCode(), Collectors.toSet())));
            role.getTeacherOccupation().getTeachers().stream().map(Teacher::getUser).filter(Objects::nonNull).filter(u -> DateUtils.isValid(u.getValidFrom(), u.getValidThru())).forEach(u -> {
                UserRolesService.updateRoleForUser(clCache, u, rolePermissions, Collections.emptyMap());
                EntityUtil.save(u, em);
            });
        }
        EntityUtil.deleteEntity(role, em);
    }

    public Page<AutocompleteResult> usersByOccupation(TeacherOccupation occupation, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher_occupation tocc "
                + "join teacher t on t.teacher_occupation_id = tocc.id "
                + "join user_ u on u.teacher_id = t.id "
                + "join person p on p.id = u.person_id ").sort(pageable);
        qb.requiredCriteria("tocc.id = :id", "id", occupation.getId());
        qb.requiredCriteria("((u.valid_from is null or u.valid_from <= :now) and (u.valid_thru is null or u.valid_thru >= :now))", "now", LocalDate.now());
        return JpaQueryUtil.pagingResult(qb, "u.id, p.firstname || ' ' || p.lastname", em, pageable)
                .map(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1)));
    }
}
