package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.UsersSeachCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.UsersSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

@Transactional
@Service
public class PersonService {

    @Autowired
    private EntityManager em;

    @Autowired
    private SchoolRepository schoolRepository;

    private static final String PERSON_FROM = "from person p " +
            "inner join user_ u on p.id=u.person_id " +
            "inner join (select array_agg(uu.role_code) as roll, uu.person_id, uu.school_id, s.ehis_school_code ehiscode " +
            "from user_ uu left outer join school s on uu.school_id = s.id group by uu.person_id, uu.school_id, s.ehis_school_code) roles " +
            "on u.person_id=roles.person_id and u.school_id=roles.school_id ";

    private static final String PERSON_SELECT = "distinct p.idcode, p.firstname, p.lastname, u.school_id, p.id,array_to_string(roles.roll, ', ')";
    //private static final String PERSON_SELECT = "distinct p.idcode, p.firstname, p.lastname, u.school_id, roles.roll roll";

    public Page<UsersSearchDto> search(UsersSeachCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(PERSON_FROM, pageable);

        if(StringUtils.hasText(criteria.getName()))  {
            qb.requiredCriteria("(upper(p.firstname) like :name or upper(p.lastname) like :name "
                    + "or upper(p.firstname || ' ' || p.lastname) like :name)", "name", "%"+criteria.getName().toUpperCase()+"%");
        }

        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());

        qb.optionalCriteria("roles.ehiscode = :ehiscode", "ehiscode", criteria.getSchool());

        qb.optionalCriteria(":roll = ANY(roles.roll)", "roll", criteria.getRole());


        Page<Object[]> result =  JpaQueryUtil.pagingResult(qb.select(PERSON_SELECT, em), pageable, () -> qb.count(em));
        Set<Long> schoolIds = result.getContent().stream().filter(s -> s[3] != null).map(s -> resultAsLong(s,3)).collect(Collectors.toSet());

        Map<Long, AutocompleteResult> schools =  schoolRepository.findAll(schoolIds).stream().collect(Collectors.toMap(School::getId, AutocompleteResult::of));

        return result.map(r -> {
            UsersSearchDto dto = new UsersSearchDto();
            dto.setIdcode(resultAsString(r, 0));
            dto.setName(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setSchool(schools.get(resultAsLong(r, 3)));
            dto.setId(resultAsLong(r, 4));
            // todo implements UserType(array)
            dto.setRole(Arrays.asList(resultAsString(r, 5).split(", ")));
            return dto;
        });
    }
}
