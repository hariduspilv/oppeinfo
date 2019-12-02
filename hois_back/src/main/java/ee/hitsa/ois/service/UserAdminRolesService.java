package ee.hitsa.ois.service;

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

import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.domain.UserSchoolRoleRights;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.AssertionFailedException;
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
public class UserAdminRolesService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierService classifierService;

    public Page<AutocompleteResult> searchRoles(HoisUserDetails user, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from user_school_role usr").sort(pageable);
        qb.requiredCriteria("usr.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("usr.teacher_occupation_id is null"); // Only admin roles
        return JpaQueryUtil.pagingResult(qb, "usr.id, usr.name_et, usr.name_en", em, pageable).map(r -> {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2));
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
        if (cmd.getRoleId() != null && checkIfHasUsers) {
            checkResult.put("hasUsers", Boolean.valueOf(!em.createNativeQuery("select 1 from user_ u "
                    + "where u.user_school_role_id = ?1 and "
                    + "((u.valid_from is null or u.valid_from <= ?2) and (u.valid_thru is null or u.valid_thru >= ?2))")
                .setParameter(1, cmd.getRoleId())
                .setParameter(2, JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .setMaxResults(1)
                .getResultList().isEmpty()));
        }
        return checkResult;
    }

    public UserSchoolRole createRole(HoisUserDetails user, UserSchoolRoleForm cmd) {
        UserSchoolRole role = new UserSchoolRole();
        role.setSchool(em.getReference(School.class, user.getSchoolId()));
        return updateRole(user, role, cmd);
    }

    public UserSchoolRole updateRole(HoisUserDetails user, UserSchoolRole role, UserSchoolRoleForm cmd) {
        EntityUtil.setUsername(user.getUsername(), em);
        UserSchoolRoleCheckCommand checkCmd = new UserSchoolRoleCheckCommand();
        checkCmd.setRoleId(role.getId());
        checkCmd.setNameEt(cmd.getNameEt());
        checkCmd.setNameEn(cmd.getNameEn());
        checkCmd.setSchoolId(EntityUtil.getId(role.getSchool()));
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
        
        EntityUtil.bindToEntity(cmd, role, "school", "teacherOccupation", "rights", "overwriteRights");
        
        Map<String, Set<String>> oldPerms = role.getRights().stream()
                .collect(Collectors.groupingBy(r -> r.getObject().getCode(),
                        Collectors.mapping(r -> r.getPermission().getCode(), Collectors.toSet())));
        
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

        if (Boolean.TRUE.equals(cmd.getOverwriteRights()) && !role.getUsers().isEmpty()) {
            Map<String, Set<String>> newPerms = role.getRights().stream()
                    .collect(Collectors.groupingBy(r -> r.getObject().getCode(),
                            Collectors.mapping(r -> r.getPermission().getCode(), Collectors.toSet())));
            role.getUsers().stream().filter(u -> DateUtils.isValid(u.getValidFrom(), u.getValidThru())).forEach(u -> {
                UserRolesService.updateRoleForUser(clCache, u, oldPerms, newPerms);
            });
        }
        return EntityUtil.save(role, em);
    }
    
    public void deleteRole(HoisUserDetails user, UserSchoolRole role, boolean deleteRights) {
        EntityUtil.setUsername(user.getUsername(), em);
        final ClassifierCache clCache = new ClassifierCache(classifierService);
        Map<String, Set<String>> rolePermissions = role.getRights().stream()
                .collect(Collectors.groupingBy(r -> r.getObject().getCode(),
                        Collectors.mapping(r -> r.getPermission().getCode(), Collectors.toSet())));
        role.getUsers().forEach(u -> {
            u.setUserSchoolRole(null);
            if (deleteRights && DateUtils.isValid(u.getValidFrom(), u.getValidThru())) {
                UserRolesService.updateRoleForUser(clCache, u, rolePermissions, Collections.emptyMap());
            }
            EntityUtil.save(u, em);
        });
        EntityUtil.deleteEntity(role, em);
    }
}
