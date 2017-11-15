package ee.hitsa.ois.service;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.EntityUtil;

@Transactional
@Service
public class EmailGeneratorService {

    private static final Pattern INVALID_EMAIL_NAME_SYMBOLS = Pattern.compile("@");

    @Autowired
    private EntityManager em;

    /**
     * Generate e-mail for given name.
     *
     * @param school
     * @param firstname
     * @param lastname
     * @return
     * @throws IllegalArgumentException if school does not have e-mail generation enabled or lastname is null
     */
    public String generateEmail(School school, String firstname, String lastname) {
        // if there is no generation enabled for given school, return failure
        String domain = school.getEmailDomain();
        if(!Boolean.TRUE.equals(school.getGenerateUserEmail()) || lastname == null || domain == null) {
            throw new IllegalArgumentException();
        }

        if(!domain.startsWith("@")) {
            domain = "@" + domain;
        }
        String prefix = normalize((firstname != null ? (firstname + ".") : "") + lastname);
        int pos = 0;
        String email = prefix + domain;
        while(emailExists(email)) {
            email = prefix + "." + String.valueOf(++pos) + domain;
        }
        return email;
    }

    /**
     * Try to look up person's school email
     *
     * @param school
     * @param person
     * @return null if no email was found
     */
    public String lookupSchoolEmail(School school, Person person) {
        Query q = em.createNativeQuery("select email from (select email, changed from teacher t where t.person_id = :personId and t.school_id = :schoolId " +
                                       "union all " +
                                       "select email, changed from student s where s.person_id = :personId and s.school_id = :schoolId) e order by changed desc");
        q.setParameter("personId", EntityUtil.getId(person));
        q.setParameter("schoolId", EntityUtil.getId(school));
        List<?> data = q.setMaxResults(1).getResultList();
        return data.isEmpty() ? null : (String)data.get(0);
    }

    /**
     * is there given email already in system?
     *
     * @param email
     * @return
     */
    private boolean emailExists(String email) {
        // uniqueness check in students, teachers (both have email field) and for person.email of administrative employee of given school
        Query q = em.createNativeQuery("select 1 from teacher t where t.email = :email " +
                                       "union all " +
                                       "select 1 from person p where p.email = :email " +
                                       "union all " +
                                       "select 1 from student s where s.email = :email");
        q.setParameter("email", email);
        return !q.setMaxResults(1).getResultList().isEmpty();
    }

    /**
     * normalize text for name portion of email.
     *
     * @param text
     * @return
     */
    private static String normalize(String text) {
        String name = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        name = name.trim().replaceAll("\\s+", ".");
        return INVALID_EMAIL_NAME_SYMBOLS.matcher(name).replaceAll("").toLowerCase();
    }
}
