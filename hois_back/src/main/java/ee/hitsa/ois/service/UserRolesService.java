package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.UserSchoolRoleDto;

@Transactional
@Service
public class UserRolesService {

    @Autowired
    protected EntityManager em;
    @Autowired
    protected ClassifierService classifierService;
    
    static class SearchResult extends AutocompleteResult {
        
        private Boolean created;

        public SearchResult(Long id, String nameEt, String nameEn, Boolean isCreated) {
            super(id, nameEt, nameEn);
            this.created = isCreated;
        }

        public Boolean getCreated() {
            return created;
        }

        public void setCreated(Boolean created) {
            this.created = created;
        }
    }

    static User updateRoleForUser(ClassifierCache cache, User user, Map<String, Set<String>> oldPerms, Map<String, Set<String>> newPerms) {
        Map<String, Set<String>> toRemove = new HashMap<>();
        Map<String, Set<String>> newCopy = copy(newPerms);
        Map<String, Set<String>> mappedUserRights = user.getUserRights().stream()
                .collect(Collectors.groupingBy(r -> r.getObject().getCode(),
                        Collectors.mapping(r -> r.getPermission().getCode(), Collectors.toSet())));
        Iterator<Entry<String, Set<String>>> it = mappedUserRights.entrySet().iterator();
        
        while (it.hasNext()) {
            Entry<String, Set<String>> entry = it.next();
            entry.getValue().forEach(permission -> {
                if (newCopy.containsKey(entry.getKey()) && newCopy.get(entry.getKey()).contains(permission)) {
                    newCopy.get(entry.getKey()).remove(permission);
                    if (newCopy.get(entry.getKey()).isEmpty()) {
                        newCopy.remove(entry.getKey());
                    }
                } else if (oldPerms.containsKey(entry.getKey()) && oldPerms.get(entry.getKey()).contains(permission)) {
                    if (!toRemove.containsKey(entry.getKey())) {
                        toRemove.put(entry.getKey(), new HashSet<>());
                    }
                    toRemove.get(entry.getKey()).add(permission);
                }
            });
        }
        
        if (!newCopy.isEmpty()) {
            Iterator<Entry<String, Set<String>>> newPermsIt = newCopy.entrySet().iterator();
            while (newPermsIt.hasNext()) {
                Entry<String, Set<String>> entry = newPermsIt.next();
                entry.getValue().forEach(permission -> {
                    UserRights userRights = new UserRights();
                    userRights.setUser(user);
                    userRights.setObject(cache.getByCode(entry.getKey(), MainClassCode.TEEMAOIGUS));
                    userRights.setPermission(cache.getByCode(permission, MainClassCode.OIGUS));
                    user.getUserRights().add(userRights);
                });
            }
        }
        
        user.getUserRights().removeIf(ur -> toRemove.containsKey(ur.getObject().getCode()) && toRemove.get(ur.getObject().getCode()).contains(ur.getPermission().getCode()));
        return user;
    }

    static <T> Map<T, Set<T>> copy(Map<T, Set<T>> original) {
        Map<T, Set<T>> copy = new HashMap<>();
        for (Map.Entry<T, Set<T>> entry : original.entrySet()) {
            copy.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
        return copy;
    }

    /**
     * Used for search form (all admin roles and teacher occupations)
     * 
     * In this case it returns all roles and occupations without role.
     * 
     * @param user
     * @param onlyAdmin
     * @return
     */
    public List<AutocompleteResult> userSchoolRoles(HoisUserDetails user, boolean onlyAdmin) {
        List<?> results = em.createNativeQuery("select id, name_et, name_en, created " + 
                "from (select usr.id, coalesce(tocc.occupation_et, usr.name_et) as name_et, coalesce(tocc.occupation_en, usr.name_en) as name_en, true as created " +
                "from user_school_role usr left join teacher_occupation tocc on tocc.id = usr.teacher_occupation_id where usr.school_id = ?1 " +
                (onlyAdmin ? "and tocc.id is null " : "") + 
                "union select tocc.id, tocc.occupation_et as name_et, tocc.occupation_en as name_en, false as created " + 
                "from teacher_occupation tocc left join user_school_role usr on usr.teacher_occupation_id = tocc.id " +
                "where tocc.school_id = ?1 and usr.id is null ) wrapped " +
                (onlyAdmin ? "where created " : ""))
                .setParameter(1, user.getSchoolId())
                .getResultList();
        return StreamUtil.toMappedList(r -> new SearchResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsBoolean(r, 3)), results);
    }

    public Map<Long, UserSchoolRoleDto> userSchoolRoleRights(HoisUserDetails user) {
        List<UserSchoolRole> results = em
                .createQuery("select usr from UserSchoolRole usr where usr.school.id = ?1", UserSchoolRole.class)
                .setParameter(1, user.getSchoolId())
                .getResultList();
        return results.stream().collect(Collectors.toMap(r -> r.getId(), UserSchoolRoleDto::of, (o, n) -> o));
    }

    public Page<AutocompleteResult> usersByRole(UserSchoolRole role, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from user_school_role usr "
                + "join user_ u on u.user_school_role_id = usr.id "
                + "join person p on p.id = u.person_id ").sort(pageable);
        qb.requiredCriteria("usr.id = :id", "id", role.getId());
        qb.requiredCriteria("((u.valid_from is null or u.valid_from <= :now) and (u.valid_thru is null or u.valid_thru >= :now))", "now", LocalDate.now());
        return JpaQueryUtil.pagingResult(qb, "u.id, p.firstname || ' ' || p.lastname", em, pageable)
                .map(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1)));
    }
}
